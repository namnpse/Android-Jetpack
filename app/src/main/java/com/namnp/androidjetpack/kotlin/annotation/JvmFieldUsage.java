package com.namnp.androidjetpack.kotlin.annotation;

import java.util.Date;

public class JvmFieldUsage {
    public static void main(String[] args) {

        Meeting meeting = new Meeting("Meeting", new Date());
//        String name = meeting.name; // compilation error
//        It will not compile. From Java, we will have to use the getter method as below:

//        String name = meeting.getName(); // without using @JvmField
        String name = meeting.name; // without using @JvmField
    }
}
