package com.raphael.rapha.myNews.http;

import android.content.Context;
import android.support.v4.app.Fragment;

public class HttpRequestInfo {

    public static final int TOO_MANY_REQUESTS = 429;

    private int informationCode;
    private int httpResponseCode;
    private boolean errorOccurred;
    private Object requestResponse;
    private Context context;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Object getDataOfRequester() {
        return dataOfRequester;
    }

    public void setDataOfRequester(Object dataOfRequester) {
        this.dataOfRequester = dataOfRequester;
    }

    private Object dataOfRequester;
    private static final int DEFAULT_CODE = -1;

    public HttpRequestInfo(){
        this.informationCode = DEFAULT_CODE;
        this.errorOccurred = false;
    }

    public int getInformationCode() {
        return informationCode;
    }

    public void setInformationCode(int informationCode) {
        this.informationCode = informationCode;
    }

    public boolean isErrorOccurred() {
        return errorOccurred;
    }

    public void setErrorOccurred(boolean errorOccurred) {
        this.errorOccurred = errorOccurred;
    }

    public Object getRequestResponse() {
        return requestResponse;
    }

    public void setRequestResponse(Object requestResponse) {
        this.requestResponse = requestResponse;
    }

    public int getHttpResponseCode() {
        return httpResponseCode;
    }

    public void setHttpResponseCode(int httpResponseCode) {
        this.httpResponseCode = httpResponseCode;
    }
}
