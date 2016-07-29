package com.androiddev.frame.networkengine;

/**
 * Created by Administrator on 2016/7/29.
 */
public interface RequestCallBack {

    public void onStart();

    public void onLoading(long total, long current, boolean isUploading);

    public void onSuccess(Object entity);

    public void onFailure(Exception error, String msg);
}
