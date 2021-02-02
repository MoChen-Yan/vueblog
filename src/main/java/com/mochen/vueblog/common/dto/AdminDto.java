package com.mochen.vueblog.common.dto;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Validated
public class AdminDto {

    @NotBlank(message = "管理员不得为空")
    private String adminName;

    @NotBlank(message = "密码不得为空")
    private String adminPassword;
}
