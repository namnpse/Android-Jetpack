package com.namnp.androidjetpack.design_pattern

// in Kotlin

object Singleton {

    fun doSomething() {

    }

}


// Tools -> Kotlin -> Show ByteCode -> Decompile
// -> to see code above from Singleton in Kotlin -> Singleton in Java
// in Java

//public final class Singleton {
//    @NotNull
//    public static final Singleton INSTANCE;
//
//    public final void doSomething() {
//    }
//
//    private Singleton() {
//    }
//
//    static {
//        Singleton var0 = new Singleton();
//        INSTANCE = var0;
//    }
//}