package com.example.rapha.swipeprototype2.http;

public class HttpRequest {

    IHttpRequester httpRequester;
    int informationCode;

    public HttpRequest(IHttpRequester httpRequester, int informationCode){
        this.httpRequester = httpRequester;
        this.informationCode = informationCode;
    }
}
