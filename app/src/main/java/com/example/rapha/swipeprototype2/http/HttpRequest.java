package com.example.rapha.swipeprototype2.http;

public class HttpRequest {

    IHttpRequester httpRequester;
    HttpRequestInfo requestInfo;

    public HttpRequest(IHttpRequester httpRequester, HttpRequestInfo requestInfo){
        this.httpRequester = httpRequester;
        this.requestInfo = requestInfo;
    }
}
