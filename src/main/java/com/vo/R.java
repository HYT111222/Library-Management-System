package com.vo;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * FileName: R
 * Date: 2023/04/12
 * Description: 该类用于传输数据，为Response文件，返回值时使用
 */
@Data
public class R {
    //是否成功
    private Boolean success;
    //返回消息
    private String message;
    //错误码
    private String code;
    //返回数据
    public Map<String, Object> data = new HashMap<>();

    // 成功
    public R ok() {
        this.setSuccess(true);
        this.setMessage("成功");
        return this;
    }

    //设置错误码
    public R setCode(String code){
        this.code = code;
        return this;
    }

    // 失败方法
    public  R error(String mag) {
        this.setSuccess(false);
        this.setMessage(mag);
        return this;
    }
    public  R error(String code,String mag) {
        this.setSuccess(false);
        this.setMessage(mag);
        this.setCode(code);
        return this;
    }

    public  R error() {
        this.setSuccess(false);
        this.setMessage("失败");
        return this;
    }

    // 数据
    public R data(String key, Object value){
        this.data.put(key, value);
        return this;
    }
    //数据
    public R data(Map<String, Object> map){
        this.setData(map);
        return this;
    }
}
