package com.mochen.vueblog.controller;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mochen.vueblog.common.dto.RegisterDto;
import com.mochen.vueblog.common.lang.Result;
import com.mochen.vueblog.entity.User;
import com.mochen.vueblog.mapper.UserMapper;
import com.mochen.vueblog.service.UserService;
import com.mochen.vueblog.util.ShiroUtil;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.PublicKey;
import java.sql.ResultSet;
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


    @GetMapping("/info")
    public Object index() {
        if(ShiroUtil.getProfile().getId() != null){
            long userId = ShiroUtil.getProfile().getId().longValue();
            User user = userService.getById(userId);
            return Result.succ(user);
        }else{
            return Result.fail("未登录");
        }


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
            temp.setStatus(user.getStatus());
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

    @PostMapping("/creat")
    public Result creat(@RequestBody @Validated RegisterDto registerDto){

        String reUserName = registerDto.getUsername();
        User temp = null;
        if(reUserName != null){
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq(true,"username", reUserName);
            try{
                temp = userService.getOne(queryWrapper);
            }catch (Exception e){
            }finally {
                temp = userService.getOne(queryWrapper);
            }
            if(temp != null){
                return Result.fail("用户已被注册,请重新注册");
            }else{
                User user = new User();
                user.setUsername(registerDto.getUsername());
                user.setPassword(registerDto.getPassword());
                user.setEmail(registerDto.getEmail());
                user.setAvatar(null);
                user.setStatus(1);
                user.setCreated(LocalDateTime.now(Clock.system(ZoneId.of("Asia/Shanghai"))));
                user.setLastLogin(null);
                userService.save(user);
                return Result.succ(user);
            }
        }else {
            return Result.fail("注册失败，请重新填写信息");
        }

        /*if(registerDto != null){
            User user = new User();
            user.setUsername(registerDto.getUsername());
            user.setPassword(registerDto.getPassword());
            user.setEmail(registerDto.getEmail());
            user.setAvatar(null);
            user.setStatus(1);
            user.setCreated(LocalDateTime.now(Clock.system(ZoneId.of("Asia/Shanghai"))));
            user.setLastLogin(null);
            userService.save(user);
            return Result.succ(user);
        }else {
            return Result.fail("注册失败");
        }*/

    }




}
