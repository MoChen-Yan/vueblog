package com.mochen.vueblog.controller;


import com.mochen.vueblog.common.lang.Result;
import com.mochen.vueblog.entity.User;
import com.mochen.vueblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/{id}")
    public Object index(@PathVariable("id") Long id){
        User user = userService.getById(id);
        return Result.succ(user);
    }

}
