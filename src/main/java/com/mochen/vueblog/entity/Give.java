package com.mochen.vueblog.entity;

import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("m_give")
public class Give implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @NotNull(message = "捐献物品不得为空")
    private String giveName;

    @NotNull(message = "图片不得为空")
    private String givePicture;

    private Long giveUser;

    @NotNull(message = "个数不得为空")
    private Long giveNum;

    @NotNull(message = "介绍不得为空")
    private String giveContext;


}
