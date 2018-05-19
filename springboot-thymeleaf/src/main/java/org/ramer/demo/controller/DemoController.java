package org.ramer.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.thoughtworks.xstream.XStream;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.ramer.demo.domain.CommonResponse;
import org.ramer.demo.domain.Location;
import org.ramer.demo.util.HttpUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller
@RequestMapping("/demo")
@Slf4j
public class DemoController{
    @Resource
    private OkHttpClient okHttpClient;
    @Value("${org.ramer.demo.index_url}")
    private String INDEX_URL;

    @GetMapping("/transferJson")
    @ResponseBody
    public ResponseEntity<Location> transferJson() {
        //    public String transferJson() {
        Location location = new Location();
        location.setCountry("中国");
        location.setArea("西南");
        location.setRegion("四川省");
        location.setCity("成都市");
        return new ResponseEntity<>(location, HttpStatus.OK);
        //        return JSONObject.toJSONString(location);
    }

    @GetMapping("/transferXml")
    @ResponseBody
    public String returnXml() {
        Location location = new Location();
        location.setCountry("中国");
        location.setArea("西南");
        location.setRegion("四川省");
        location.setCity("成都市");
        XStream xStream = new XStream();
        xStream.processAnnotations(Location.class);
        String locationXml = xStream.toXML(location);
        log.debug(" transferXml : \n{}", locationXml);
        return locationXml;
    }

    @GetMapping("/parseXml")
    @ResponseBody
    public CommonResponse parseXml() {
        Location location = HttpUtil.get(String.join("/", "http:/", INDEX_URL, "demo/transferXml"), Location.class,
                HttpUtil.MediaType.XML, okHttpClient);
        log.debug(" parseXml : {}", JSONObject.toJSONString(location));
        return new CommonResponse(true, JSONObject.toJSONString(location));
    }

}
