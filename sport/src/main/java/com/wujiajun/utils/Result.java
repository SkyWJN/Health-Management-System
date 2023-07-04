package com.wujiajun.utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author wujiajun
 * @date 2023/3/10/ 20:34
 */
@ApiModel(value = "响应参数")
public class Result implements Serializable {
    //响应给前端是否成功的标识
    @ApiModelProperty(value = "是否成功标识", dataType = "boolean")
    private boolean flag;
    //响应信息
    @ApiModelProperty(value = "响应信息", dataType = "String")
    private String message;
    //响应数据
    @ApiModelProperty(value = "响应数据", dataType = "Object")
    private Object data;

    /**
     * 响应成功的结果
     *
     * @param message
     * @param data
     * @return
     */
    public static Result success(String message, Object data) {
        return new Result(true, message, data);
    }

    public static Result success(String message) {
        return new Result(true, message);
    }

    /**
     * 响应失败的结果
     *
     * @param message
     * @return
     */
    public static Result fail(String message) {
        return new Result(false, message);
    }

    public Result() {
    }

    public Result(boolean flag, String message) {
        this.flag = flag;
        this.message = message;
    }

    public Result(boolean flag, String message, Object data) {
        this.flag = flag;
        this.message = message;
        this.data = data;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "flag=" + flag +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
