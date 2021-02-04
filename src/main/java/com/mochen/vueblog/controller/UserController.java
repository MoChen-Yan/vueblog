package com.mochen.vueblog.controller;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mochen.vueblog.common.lang.Result;
import com.mochen.vueblog.entity.User;
import com.mochen.vueblog.mapper.UserMapper;
import com.mochen.vueblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.PublicKey;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;


    @GetMapping("/{id}")
    public Object index(@PathVariable("id") Long id) {
        User user = userService.getById(id);
        return Result.succ(user);
    }

    @GetMapping("/list")
    public Result list(@RequestParam(defaultValue = "1") Integer currentPage){
        Page page = new Page(currentPage,5);
        IPage pageData = userService.page(page,new QueryWrapper<User>().orderByDesc("id"));
        return Result.succ(pageData);
    }

    @GetMapping("/likeName/{username}")
    public  Result likeName(@PathVariable(name = "username") String userName,@RequestParam(defaultValue = "1") Integer ccurrentPage){

        Page page = new Page(ccurrentPage,5);
        IPage pageData = userService.page(page , new QueryWrapper<User>().like("username",userName));

        return Result.succ(pageData);
    }


    @GetMapping("/edit")
    public Result edit(@Validated @RequestBody User user) {
        //LocalDateTime localDateTime = LocalDateTime.now(Clock.system(ZoneId.of("Asia/Shanghai")));
        //LocalDateTime localDateTime1 = localDateTime.of(2018, 1, 1, 2, 1);
        User temp = null;
        if(user.getId() != null){
            temp = userService.getById(user.getId());

        }else{
            temp = new User();
            temp.setAvatar(user.getAvatar());
            temp.setEmail(user.getEmail());
            temp.setPassword(user.getPassword());
            temp.setCreated(LocalDateTime.now(Clock.system(ZoneId.of("Asia/Shanghai"))));
        }

        BeanUtil.copyProperties(user,temp,"id","avatar","email","password","created");
        userService.saveOrUpdate(temp);
        return Result.succ(null);
    }

    @GetMapping("/update")
    public Result update( @RequestBody User user) {
        if(user.getId()!= null){
            UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();
            userUpdateWrapper.eq("id",user.getId())
                    .set("avatar",user.getAvatar())
                    .set("email",user.getEmail())
                    .set("password",user.getPassword())
                    .set("status",user.getStatus());
            boolean TF = userService.update(userUpdateWrapper);
            return Result.succ(TF);
        }else {
            return Result.fail("修改失败");
        }
    }


}
