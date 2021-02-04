package com.mochen.vueblog.common.dto;

import cn.hutool.core.lang.Range;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;

@Data
@Validated
public class OrderDto implements Serializable {



    private long orderGoods;

    private String orderAddress;

    private double orderPrice;

}
