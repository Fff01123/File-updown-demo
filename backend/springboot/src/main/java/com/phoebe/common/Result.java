package com.phoebe.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: springboot
 * @description: 返回接口
 * @author: F_phoebe
 * @create: 2024-04-28 14:32
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    private static final String SUCCESS = "200";
    private static final String ERROR = "500";

    private String code;
    private String msg;
    private Object data;

    public static Result success(){
        Result result = new Result();
        result.setCode(SUCCESS);
        return result;
    }

    public static Result success(Object data){
        Result result = new Result();
        result.setCode(SUCCESS);
        result.setData(data);
        return result;
    }

    public static Result error(String msg){
        Result result = new Result();
        result.setCode(ERROR);
        result.setMsg(msg);
        return result;
    }

}
