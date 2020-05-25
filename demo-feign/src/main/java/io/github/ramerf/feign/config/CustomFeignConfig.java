package io.github.ramerf.feign.config;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.optionals.OptionalDecoder;
import io.github.ramerf.wind.core.entity.enums.InterEnum;
import io.github.ramerf.wind.core.serializer.JacksonEnumSerializer;
import io.github.ramerf.wind.core.util.EnumUtils;
import java.io.IOException;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import static io.github.ramerf.wind.core.util.BeanUtils.scanClasses;

@SuppressWarnings("rawtypes")
@Slf4j
@Configuration
public class CustomFeignConfig {
  @Resource private ObjectFactory<HttpMessageConverters> messageConverters;

  @Resource private ObjectMapper objectMapper;

  @Bean
  @ConditionalOnMissingBean
  public Decoder feignDecoder() {
    messageConverters.getObject().getConverters().stream()
        .filter(MappingJackson2HttpMessageConverter.class::isInstance)
        .forEach(
            converter ->
                ((MappingJackson2HttpMessageConverter) converter)
                    .setObjectMapper(customObjectMapper()));
    return new OptionalDecoder(
        new ResponseEntityDecoder(new SpringDecoder(this.messageConverters)));
  }

  @Bean
  @ConditionalOnMissingBean
  public Encoder feignEncoder() {
    return new SpringEncoder(this.messageConverters);
  }

  public ObjectMapper customObjectMapper() {
    ObjectMapper objectMapper = new ObjectMapper();
    BeanUtils.copyProperties(this.objectMapper, objectMapper);
    // 驼峰自动转下划线
    objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
    // 忽略在bean中不存在的属性
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    SimpleModule module = new SimpleModule();
    module.addSerializer(InterEnum.class, new JacksonEnumSerializer());
    objectMapper.registerModule(module);
    try {
      scanClasses("io.github.ramerf", InterEnum.class)
          .forEach(o -> module.addDeserializer(o, new DeserEnum(o)));
    } catch (IOException e) {
      log.warn(e.getMessage());
      log.error(e.getMessage(), e);
    }
    objectMapper.registerModule(module);
    return objectMapper;
  }

  @Slf4j
  public static class DeserEnum<T extends InterEnum> extends JsonDeserializer<T> {
    private Class<T> clazz;

    public DeserEnum(Class<T> clazz) {
      this.clazz = clazz;
    }

    @Override
    public T deserialize(JsonParser p, DeserializationContext ctxt)
        throws IOException, JsonProcessingException {
      int value = 0;
      JsonToken t = p.currentToken();
      if (t == JsonToken.START_OBJECT) {
        t = p.nextToken();
      } else if (t != JsonToken.FIELD_NAME) {
        throw new Error();
      }
      while (t == JsonToken.FIELD_NAME) {
        final String fieldName = p.currentName();
        t = p.nextToken();
        if ("value".equals(fieldName)) {
          value = p.getIntValue();
        }
        t = p.nextToken();
      }
      return EnumUtils.of(clazz, value);
    }
  }
}
