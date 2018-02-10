package com.henu.util;

public class BloomFilter {
    private static final int DEFAULT_SIZE = 28;//布隆过滤器的比特长度
    private static final int[] seeds = {3,5,7, 11, 13, 31, 37, 61};
    private static SimpleHash[] func = new SimpleHash[seeds.length];
    public static SimpleHash[] newFunc(int size){
        if(func[0]!=null){
            return func;
        }
        if(size>8){
            size = 2<<size;
        }else{
            size = 2<<DEFAULT_SIZE;
        }
        for (int i = 0; i < seeds.length; i++) {
            func[i] = new SimpleHash(size, seeds[i]);
        }
        return func;
    }

}
class SimpleHash {

    private int cap;
    private int seed;

    public  SimpleHash(int cap, int seed) {
        this.cap = cap;
        this.seed = seed;
    }

    public int hash(String value) {//字符串哈希，选取好的哈希函数很重要
        int result = 0;
        int len = value.length();
        for (int i = 0; i < len; i++) {
            result = seed * result + value.charAt(i);
        }
        return (cap - 1) & result;
    }
}
