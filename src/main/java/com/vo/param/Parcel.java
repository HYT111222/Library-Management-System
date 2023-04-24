package com.vo.param;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * FileName: EnterParam
 * Date: 2023/04/11
 * Description: 该类用作出 入库请求传递参数 的类
 */

@Getter
@Setter
//@AllArgsConstructor
public class Parcel {

    private String id;

    private String place;

//    private String result;

    public Parcel(String id, String place){
        this.id = id;
        this.place = place;
    }

}
