/*
package com.hanma.common;

import org.apache.poi.ss.formula.functions.T;

public class ResultData {
    private String message;
    private int retCode;
    private T data;

    private ResultData(T data) {
        this.retCode = 200;
        this.message = "成功";
        this.data = data;
    }
    private ResultData(CodeMsg cm) {
        if(cm == null){
            return;
        }
        this.retCode = cm.getRetCode();
        this.message = cm.getMessage();
    }

    public static <T> ResultData<T> fail(String cm,String msg){
        cm.setMessage(cm.getMessage()+"--"+msg);
        return new ResultData<T>(cm);
    }
}
*/
