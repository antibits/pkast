package com.pkast.modules;

import java.util.Arrays;
import java.util.List;

public class Resp<T> {
    private int retCode = RespRetCode.RET_SUCCESS.value();

    private int totalCount;

    private int totalPage;

    private int pageSize;

    private int currPage;

    private List<T> data;

    private Resp(){}

    public static <T> Resp<T> makeResp(RespRetCode retCode){
        Resp<T> result = new Resp<>();
        result.retCode = retCode.value();
        return result;
    }

    public static <T> Resp<T> makeResp(T data){
        Resp<T> result = new Resp<>();
        result.data = Arrays.asList(data);
        result.totalCount = 1;
        result.totalPage = 1;
        result.pageSize = 1;
        result.currPage = 1;

        return result;
    }

    public static <T> Resp<T> makeResp(int totalCount, int totalPage, int pageSize, int currPage, List<T> data){
        Resp<T> result = new Resp<>();
        result.totalCount = totalCount;
        result.totalPage = totalPage;
        result.pageSize = pageSize;
        result.currPage = currPage;
        result.data = data;

        return result;
    }

    public int getRetCode() {
        return retCode;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getCurrPage() {
        return currPage;
    }

    public List<T> getData() {
        return data;
    }
}
