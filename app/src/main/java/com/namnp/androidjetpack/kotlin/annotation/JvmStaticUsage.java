package com.namnp.androidjetpack.kotlin.annotation;

public class JvmStaticUsage {

    public static void main(String[] args) {
//    AppUtils.install(); // compilation error (1)
//    -> fix:
//        Case 1: AppUtils is not annotated with @JvmStatic
        AppUtils.INSTANCE.install(); // works
//        Case 2: AppUtils is annotated with @JvmStatic
        AppUtils.install(); // (1) works
    }
}
