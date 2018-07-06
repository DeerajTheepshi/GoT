package com.example.android.got.RetrofitClasses;

public class EntireBody {
    public String Message;
    public results data;

    public String getMessage() {
        return Message;
    }

    public results getData() {
        return data;
    }

    public EntireBody(String message, results data) {

        Message = message;
        this.data = data;
    }
}
