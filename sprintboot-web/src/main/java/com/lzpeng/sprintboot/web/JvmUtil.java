package com.lzpeng.sprintboot.web;

public class JvmUtil {

    public static final String getCurrentMethod(){
        return Thread.currentThread().getStackTrace()[2].getMethodName();
    }

    public static final String getCurrentClass(){
        return Thread.currentThread().getStackTrace()[2].getClassName();
    }

}
