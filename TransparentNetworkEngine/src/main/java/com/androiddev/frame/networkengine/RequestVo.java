package com.androiddev.frame.networkengine;

import android.content.Context;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2016/7/29.
 */
public class RequestVo {

    /**
     * 请求地址
     */
    public String requestUrl;
    /**
     * 上下文
     */
    public Context context;
    /**
     * 请求参数
     */
    public HashMap<String, String> requestDataMap;
    /**
     * Json请求参数
     */
    public JSONObject requestJson;
    /**
     * 解析实体类型
     */
    public Class type;
    /***
     * 下载文件地址
     */
    public String fileUrl;
    /***
     * 上传文件列表
     */
    public ArrayList<File> fileList = new ArrayList<File>();
    /***
     * 请求取消标志
     */
    public String cancleTag;
    /***
     * 事件标识
     */
    public int eventTag;

}
