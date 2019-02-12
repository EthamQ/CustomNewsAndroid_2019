package com.example.rapha.swipeprototype2.http;

public class HttpRequestInfo {

    private int informationCode;
    private boolean errorOccurred;
    private Object data;
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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
