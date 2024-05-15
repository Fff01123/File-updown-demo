package com.phoebe.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: springboot
 * @description: 接收前端传来的变量（name和phone）
 * @author: F_phoebe
 * @create: 2024-04-28 21:26
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Params {
    private String name;
    private String phone ;
    private String author ;
    private Integer currentPage;
    private Integer pageSize;
}
