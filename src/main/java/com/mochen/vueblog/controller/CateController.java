package com.mochen.vueblog.controller;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mochen.vueblog.common.lang.Result;
import com.mochen.vueblog.entity.Cate;
import com.mochen.vueblog.service.CateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author MoChen出品，必是精品
 * @since 2021-01-15
 */
@RestController
@RequestMapping("/cate")
public class CateController {

    @Autowired
    CateService cateService;

    @GetMapping("/list")
    public Result list(@RequestParam(defaultValue = "1") Integer currentPage){
        Page page = new Page(currentPage,5);
        IPage pageData = cateService.page(page,new QueryWrapper<Cate>().orderByDesc("id"));

        return Result.succ(pageData);
    }

    @GetMapping("edit")
    public Result edit(@Validated @RequestBody Cate cate){

        Cate cate1 = null;
        if(cate.getId() != null){
            cate1 = cateService.getById(cate.getId());

        }else{
            cate1 = new Cate();
            cate1.setCateName(cate.getCateName());


        }

        BeanUtil.copyProperties(cate,cate1,"id");
        cateService.saveOrUpdate(cate1);

        return Result.succ(cate1);
    }




}
