package com.yj.utils;

public class PageUtils {
    public static int calcOffset(int pageNum,int pageSize){
        return (pageNum - 1)*pageSize;
    }
}
