package com.mochen.vueblog.common.dto;

import lombok.Data;
import org.springframework.validation.annotation.Validated;
import sun.security.util.ManifestEntryVerifier;

import javax.validation.constraints.NotBlank;

@Data
@Validated
public class RegisterDto {

    @NotBlank(message = "用户名不得为空")
    private String username;

    @NotBlank(message = "密码不得为空")
    private String password;

    @NotBlank(message = "邮箱不得为空")
    private String email;

}
