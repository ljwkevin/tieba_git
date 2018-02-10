package com.henu.util;

public class PageUtil{

    public static int getPageMax(int count,int OnePageMax){
        if(count%OnePageMax==0){
            return (count/OnePageMax);
        }
        return (count/OnePageMax)+1;
    }
}