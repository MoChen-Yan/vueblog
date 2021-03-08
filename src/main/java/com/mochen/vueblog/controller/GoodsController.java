package com.mochen.vueblog.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mochen.vueblog.common.lang.Result;
import com.mochen.vueblog.entity.Goods;
import com.mochen.vueblog.mapper.GoodsMapper;
import com.mochen.vueblog.service.GoodsService;
import com.mochen.vueblog.util.ShiroUtil;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;

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
        IPage pageData = goodsService.page(page, new QueryWrapper<Goods>().orderByDesc("id"));

        return Result.succ(pageData);
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable(name = "id") long id){
        Goods goods = goodsService.getById(id);
        Assert.notNull(goods,"该商品不存在");
        return Result.succ(goods);


    }

    @GetMapping("/likename/{goodsName}")
    public Result likeName(@PathVariable(name = "goodsName") String goodsName,@RequestParam(defaultValue = "1") Integer currentPage){
        Page page = new Page(currentPage,10);
        IPage pageData = goodsService.page(page,new QueryWrapper<Goods>().like("goods_name",goodsName));
//        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
//        queryWrapper.like(goodsName!=null,"goods_name",goodsName);
//        List<Goods> list = goodsMapper.selectList(new QueryWrapper<Goods>().like("goods_name",goodsName));
        return Result.succ(pageData);

    }

    @GetMapping("/listByCate/{cateId}")
    public Result listByCate(@PathVariable(name = "cateId") long cateId,@RequestParam(defaultValue = "1") Integer currentPage){
        Page page = new Page(currentPage,10);
        IPage pageData = goodsService.page(page,new QueryWrapper<Goods>().eq("goods_cate",cateId));

        return Result.succ(pageData);

    }



    @RequiresAuthentication
    @GetMapping("/edit")
    public Result edit(@Validated @RequestBody Goods goods){

        Goods temp = null;
        if(goods.getId() != null){
            temp = goodsService.getById(goods.getId());


        }else{

            temp = new Goods();
            temp.setGoodsUser(ShiroUtil.getProfile().getId());
            temp.setGoodsCreated(LocalDateTime.now(Clock.system(ZoneId.of("Asia/Shanghai"))));
            temp.setGoodsStatus(0);


        }

        BeanUtil.copyProperties(goods,temp,"id","goodsCreated",
                "goodsUser","goodsStatus");
        goodsService.saveOrUpdate(temp);

        return Result.succ(null);

    }

    @GetMapping("/del/{id}")
    public Result del(@PathVariable(name = "id")  long id){
        Goods goods = goodsService.getById(id);
        try{
            if(goods != null){
                Assert.isTrue(goods.getId().longValue() == ShiroUtil.getProfile().getId().longValue(),"不是您自己的商品");
                goodsMapper.deleteById(id);

            }

        }catch (Exception e){
            e.getMessage();
        }

        return Result.succ(null);
    }

    @GetMapping("/update")
    public Result update(@RequestBody Goods goods) {

        UpdateWrapper<Goods> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",goods.getId())
                .set("goods_name",goods.getGoodsName())
                .set("goods_content",goods.getGoodsContent())
                .set("goods_num",goods.getGoodsNum())
                .set("goods_price",goods.getGoodsPrice())
                .set("goods_picture",goods.getGoodsPicture())
                .set("goods_status",goods.getGoodsStatus())
                .set("goods_cate",goods.getGoodsCate());
        Boolean TF = goodsService.update(updateWrapper);
        return Result.succ(TF);


        /*Goods temp = null;
        if(goods.getId() != null){
            temp = goodsService.getById(goods.getId());

            //编辑
            System.out.println(ShiroUtil.getProfile().getId());


        }else{

            temp = new Goods();
            temp.setGoodsUser(ShiroUtil.getProfile().getId());
            temp.setGoodsCreated(LocalDateTime.now());


        }

        BeanUtil.copyProperties(goods,temp,"id","goods_created",
                "goods_user");
        goodsService.saveOrUpdate(temp);

        return Result.succ(null);*/

    }



}
