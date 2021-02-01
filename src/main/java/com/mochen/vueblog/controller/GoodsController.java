package com.mochen.vueblog.controller;


import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.mochen.vueblog.common.lang.Result;
import com.mochen.vueblog.entity.Blog;
import com.mochen.vueblog.entity.Goods;
import com.mochen.vueblog.mapper.GoodsMapper;
import com.mochen.vueblog.service.GoodsService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.util.StringUtils;
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
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    GoodsService goodsService;

    @Autowired
    GoodsMapper goodsMapper;

    @GetMapping("/list")
    public Result list(@RequestParam(defaultValue = "1") Integer currentPage){
        Page page = new Page(currentPage,10);
        IPage pageData = goodsService.page(page, new QueryWrapper<Goods>().orderByDesc("created"));

        return Result.succ(pageData);
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable(name = "id") long id){
        Goods goods = goodsService.getById(id);
        Assert.notNull(goods,"该商品不存在");
        return Result.succ(goods);


    }

    @GetMapping("/getByname")
    public Result getByName(@PathVariable(name = "goodsName") String goodsName,@RequestParam(defaultValue = "1") Integer currentPage){
        Page page = new Page(currentPage,10);
        IPage pageData = goodsService.page(page,new QueryWrapper<Goods>().like("goods_name",goodsName));
//        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
//        queryWrapper.like(goodsName!=null,"goods_name",goodsName);
//        List<Goods> list = goodsMapper.selectList(new QueryWrapper<Goods>().like("goods_name",goodsName));
        return Result.succ(pageData);

    }





}
