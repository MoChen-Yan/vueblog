package com.mochen.vueblog.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

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

    private String goodsName;

    private String goodsContent;

    private Long goodsNum;

    private Double goodsPrice;

    private String goodsPicture;

    private Integer goodsStatus;

    private Long goodsUser;

    private Long goodsCate;

    private LocalDateTime goodsCreated;


}
