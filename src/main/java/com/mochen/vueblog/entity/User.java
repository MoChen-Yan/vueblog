package com.mochen.vueblog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("m_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @NotNull(message = "用户名不得为空")
    private String username;

    //头像
    private String avatar;

    @NotNull(message = "电子邮箱不得为空")
    private String email;

    @NotNull(message = "密码不得为空")
    private String password;

    private Integer status;

    private LocalDateTime created;

    private LocalDateTime lastLogin;


}
