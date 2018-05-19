package org.ramer.demo.util;

import com.alibaba.fastjson.JSON;
import com.thoughtworks.xstream.XStream;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.ramer.demo.domain.Location;
import org.ramer.demo.domain.json.IpLocation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Configuration
@Slf4j
public class HttpUtil{

    public static Location getLocationByIP(HttpServletRequest req, OkHttpClient okHttpClient) {
        IpLocation ipLocation = getJsonString(
                String.join("=", "http://ip.taobao.com/service/getIpInfo2.php?ip", getRealIp(req)), IpLocation.class,
                okHttpClient);
        log.debug("ip info: {}", ipLocation);
        return ipLocation != null && ipLocation.getCode() == 0 ? ipLocation.getData() : null;
    }

    public static String getRealIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }
        return ip;
    }

    public static <T> T get(String url, Class<T> clazz, MediaType mediaType, OkHttpClient okHttpClient) {
        switch (mediaType) {
        case JSON:
            return getJsonString(url, clazz, okHttpClient);
        case XML:
            return (T) getXmlString(url, clazz, okHttpClient);
        default:
            return null;
        }
    }

    private static <T> T getJsonString(String url, Class<T> clazz, OkHttpClient okHttpClient) {
        RequestBody requestBody = new FormBody.Builder()
                //                .add("key","value")
                //                .add("key","value")
                .build();
        Request request = new Request.Builder().post(requestBody).url(url).build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            String string = response.body().string();
            log.debug("response: \n{}", string);
            return JSON.parseObject(string, clazz);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    private static <T> T getXmlString(String url, Class<T> clazz, OkHttpClient okHttpClient) {
        Request request = new Request.Builder().url(url).build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            String string = response.body().string();
            log.debug("response: \n{}", string);
            XStream xStream = new XStream();
            xStream.processAnnotations(clazz);
            return (T) xStream.fromXML(string);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    public enum MediaType {
        JSON, XML
    }

    @Bean
    public OkHttpClient okHttpClient() {
        return new OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS)
                .build();
    }

}
