package com.altcanvas.asynctemplate;

public class AppException extends Exception
{
    public String msg = null;

    public AppException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public String toString() {
        return msg;
    }
}

