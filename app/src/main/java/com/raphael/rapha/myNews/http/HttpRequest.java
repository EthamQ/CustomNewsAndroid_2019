package com.raphael.rapha.myNews.http;

public class HttpRequest {

    IHttpRequester httpRequester;
    public HttpRequestInfo requestInfo;

    public HttpRequest(IHttpRequester httpRequester, HttpRequestInfo requestInfo){
        this.httpRequester = httpRequester;
        this.requestInfo = requestInfo;
    }
}
