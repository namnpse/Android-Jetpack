package com.namnp.androidjetpack.kotlin.annotation;

import java.util.Date;

public class JvmOverloadUsage {
    public static void main(String[] args) {
        Session sessionOne = new Session("Session One", new Date());
//        Session sessionTwo = new Session("Session Two without @JvmOverload"); // compilation error (expected 2 but found 1)
        // Session(java.lang.String, java.util.Date)' in 'com.example.Session' cannot be applied to '(java.lang.String)
//        -> Java does not support default parameters, only support overloading
        Session sessionTwo = new Session("Session Two witt @JvmOverload"); // works
    }
}
