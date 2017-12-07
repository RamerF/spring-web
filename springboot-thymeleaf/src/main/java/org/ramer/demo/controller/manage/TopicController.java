package org.ramer.demo.controller.manage;

import com.alibaba.fastjson.JSONObject;
import org.ramer.demo.domain.CommonResponse;
import org.ramer.demo.domain.Topic;
import org.ramer.demo.service.QiniuUploadService;
import org.ramer.demo.service.TopicService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller("topicController")
@RequestMapping("/manage")
@PreAuthorize("hasRole('ADMIN')")
public class TopicController{
    @Resource
    QiniuUploadService uploadService;
    @Value("${qiniu.downDomain}")
    private String downDomain;
    @Resource
    private TopicService topicService;

    @PostMapping("/topic")
    @ResponseBody
    public CommonResponse createTopic(Topic topic) {
        if (topicService.saveOrUpdate(topic).getId() > 0) {
            return new CommonResponse(true, "create success");
        }
        return new CommonResponse(false, "create fail");
    }

    @RequestMapping("/upload/token")
    @ResponseBody
    public JSONObject upLoadToken() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("uptoken", uploadService.getUploadToken());
        jsonObject.put("downDomain", downDomain);
        return jsonObject;
    }
}
