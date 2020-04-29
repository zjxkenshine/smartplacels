package com.csdl.smartplacenew.vo;

import lombok.Data;

import java.util.List;

@Data
public class ListVO<T> {

    private long count;

    private List<T> list;
}
