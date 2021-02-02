package com.mochen.vueblog.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 
 * </p>
 *
 * @author MoChen出品，必是精品
 * @since 2021-01-15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("m_goods")
public class  Goods implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @NotNull(message = "商品名不得为空")
    private String goodsName;

    @NotNull(message = "介绍不得为空")
    private String goodsContent;

    @NotNull(message = "个数不得为空")
    private Long goodsNum;

    @NotNull(message = "单价不得为空")
    private Double goodsPrice;

    @NotNull(message = "图片不得为空")
    private String goodsPicture;

    private Integer goodsStatus;

    private Long goodsUser;

    private Long goodsCate;

    private LocalDateTime goodsCreated;


}
