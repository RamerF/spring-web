package io.github.ramerf.feign.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import feign.codec.Decoder;
import feign.codec.Encoder;
import io.github.ramerf.wind.core.entity.enums.InterEnum;
import io.github.ramerf.wind.core.serializer.JacksonEnumSerializer;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@SuppressWarnings("rawtypes")
@Configuration
public class CustomFeignConfig {
  @Bean
  public Decoder feignDecoder() {
    HttpMessageConverter jacksonConverter = new MappingJackson2HttpMessageConverter(customObjectMapper());
    ObjectFactory<HttpMessageConverters> objectFactory = () -> new HttpMessageConverters(jacksonConverter);
    return new ResponseEntityDecoder(new SpringDecoder(objectFactory));
  }

  @Bean
  public Encoder feignEncoder() {
    HttpMessageConverter jacksonConverter = new MappingJackson2HttpMessageConverter(customObjectMapper());
    ObjectFactory<HttpMessageConverters> objectFactory = () -> new HttpMessageConverters(jacksonConverter);
    return new SpringEncoder(objectFactory);
  }

  public ObjectMapper customObjectMapper() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
    // 驼峰自动转下划线
    objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
    // 忽略在bean中不存在的属性
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    SimpleModule module = new SimpleModule();
    module.addSerializer(InterEnum.class, new JacksonEnumSerializer());
    objectMapper.registerModule(module);

    module.addDeserializer(InterEnum.class, new DeserEnum());
    objectMapper.registerModule(module);
    return objectMapper;
  }

  @Slf4j
  public static class DeserEnum extends JsonDeserializer<InterEnum> {

    @Override
    public InterEnum deserialize(JsonParser p,
                                 DeserializationContext ctxt) throws IOException, JsonProcessingException {
      final String text = p.getText();
      log.info("deserialize:[{}]", text);
      return null;
    }
  }
}