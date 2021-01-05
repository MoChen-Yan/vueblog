package com.mochen.vueblog.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mochen.vueblog.common.lang.Result;
import com.mochen.vueblog.entity.Blog;
import com.mochen.vueblog.service.BlogService;
import com.mochen.vueblog.util.ShiroUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.HttpURLConnection;
import java.time.LocalDateTime;


@RestController
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    BlogService blogService;


    @GetMapping("/list")
    public Result list(@RequestParam(defaultValue = "1") Integer currentPage){

        Page page = new Page(currentPage,10);
        IPage pageDate = blogService.page(page,new QueryWrapper<Blog>().orderByDesc("created"));

        return Result.succ(pageDate);
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable(name = "id") long id){

        Blog blog = blogService.getById(id);
        Assert.notNull(blog,"该博客不存在");
        return Result.succ(blog);
    }

    @RequiresAuthentication
    @PostMapping("/edit")
    public Result edit(@Validated @RequestBody Blog blog){

        Blog temp = null;
        if(blog.getId() != null) {
            temp = blogService.getById(blog.getId());
            // 只能编辑自己的文章
            System.out.println(ShiroUtil.getProfile().getId());
            Assert.isTrue(temp.getUserId().longValue() == ShiroUtil.getProfile().getId().longValue(), "没有权限编辑");

        } else {

            temp = new Blog();
            temp.setUserId(ShiroUtil.getProfile().getId());
            temp.setCreated(LocalDateTime.now());
            temp.setStatus(0);
        }

        BeanUtil.copyProperties(blog, temp, "id", "userId", "created", "status");
        blogService.saveOrUpdate(temp);

        return Result.succ(null);



    }



}
