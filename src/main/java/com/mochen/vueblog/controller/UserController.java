package com.mochen.vueblog.controller;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.mochen.vueblog.common.lang.Result;
import com.mochen.vueblog.entity.User;
import com.mochen.vueblog.mapper.UserMapper;
import com.mochen.vueblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserMapper userMapper;

    @GetMapping("/{id}")
    public Object index(@PathVariable("id") Long id) {
        User user = userService.getById(id);
        return Result.succ(user);
    }

    @GetMapping("/insert")
    public Object insert() {
        LocalDateTime localDateTime = LocalDateTime.now(Clock.system(ZoneId.of("Asia/Shanghai")));
        LocalDateTime localDateTime1 = localDateTime.of(2018, 1, 1, 2, 1);
        User user = User.builder()
                .username("mochen")
                .avatar("图片")
                .email("442535701@qq.com")
                .password("123456")
                .status(1)
                .created(localDateTime)
                .lastLogin(localDateTime1)
                .build();
        boolean TF = userService.save(user);
        return Result.succ(TF);
        // return true;
    }

    @GetMapping("/update/{id}")
    public Object update(@PathVariable("id") Long id) {
        UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();
        userUpdateWrapper.eq("id", "1")
                .set("username", "mochen1");
        boolean TF = userService.update(userUpdateWrapper);
        return Result.succ(TF);
    }


}
