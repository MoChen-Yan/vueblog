package com.mochen.vueblog.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mochen.vueblog.common.dto.AdminDto;
import com.mochen.vueblog.common.lang.Result;
import com.mochen.vueblog.entity.Admin;
import com.mochen.vueblog.service.AdminService;
import com.mochen.vueblog.util.JwtUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author MoChen出品，必是精品
 * @since 2021-01-14
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    AdminService adminService;

    @GetMapping("/login")
    public Result login(@Validated @RequestBody AdminDto adminDto, HttpServletResponse response){

        Admin admin = adminService.getOne(new QueryWrapper<Admin>().eq("admin_name",adminDto.getAdminName()));
        Assert.notNull(admin,"该管理员不存在");
        if(!admin.getAdminPassword().equals(adminDto.getAdminPassword())){
            return Result.fail("管理员密码错误！！！");
        }

        String jwt = jwtUtils.generateToken(admin.getId());
        response.setHeader("Authorization",jwt);
        response.setHeader("Access-Control-Expose-Headers","Authorization");
        return Result.succ(MapUtil.builder()
        .put("id",admin.getId())
        .put("admin_name",admin.getAdminName())
        .put("avatar",admin.getAvatar())
        );
    }

    //退出
    @GetMapping("/logout")
    @RequiresAuthentication
    public Result logout(){
        SecurityUtils.getSubject().logout();
        return Result.succ(null);
    }

    //添加
    @GetMapping("/edit")
    public Result edit(@Validated @RequestBody Admin admin){

        Admin adminDemo = null;
        if(admin.getId() != null){
            adminDemo = adminService.getById(admin.getId());
        }else {
            adminDemo = new Admin();
            adminDemo.setAdminPassword(admin.getAdminPassword());
            adminDemo.setAvatar(admin.getAvatar());

        }
        BeanUtil.copyProperties(admin,adminDemo,"id","adminPassword","avatar");
        adminService.saveOrUpdate(adminDemo);
        return Result.succ(null);
    }




}
