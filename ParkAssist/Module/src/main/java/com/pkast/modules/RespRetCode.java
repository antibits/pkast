package com.pkast.modules;

public enum RespRetCode {
    RET_SUCCESS(0),// 成功
    RET_FAIL(1), // 失败
    RET_NOTREG(2); // 未注册

    private int val;

    private RespRetCode(int value){
        this.val = value;
    }

    public int value(){
        return val;
    }
}
