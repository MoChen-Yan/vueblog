package com.mochen.vueblog.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("m_give")
public class Give implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String giveName;

    private String givePicture;

    private Long giveUser;

    private Long giveNum;

    private String giveContext;


}
