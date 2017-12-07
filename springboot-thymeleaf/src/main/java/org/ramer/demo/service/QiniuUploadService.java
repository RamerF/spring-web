package org.ramer.demo.service;

import com.alibaba.fastjson.JSONObject;

public interface QiniuUploadService{
    String getUploadToken();

    JSONObject fetchResourceByUrl(String url);

    JSONObject deleteResourceByUrl(String url);

    String getUrlEntityName(String url);
}
