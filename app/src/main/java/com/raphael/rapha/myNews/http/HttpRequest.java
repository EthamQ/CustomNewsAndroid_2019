package com.raphael.rapha.myNews.http;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.raphael.rapha.myNews.activities.mainActivity.mainActivityFragments.SwipeFragment;

public class HttpRequest {

    public IHttpRequester httpRequester;
    public HttpRequestInfo requestInfo;

    public HttpRequest(IHttpRequester httpRequester, HttpRequestInfo requestInfo){
        this.httpRequester = httpRequester;
        this.requestInfo = requestInfo;
    }
}
