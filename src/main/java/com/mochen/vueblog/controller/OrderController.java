package com.mochen.vueblog.controller;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mochen.vueblog.common.dto.OrderDto;
import com.mochen.vueblog.common.lang.Result;
import com.mochen.vueblog.entity.Goods;
import com.mochen.vueblog.entity.Order;
import com.mochen.vueblog.service.GoodsService;
import com.mochen.vueblog.service.OrderService;
import com.mochen.vueblog.service.UserService;
import com.mochen.vueblog.util.ShiroUtil;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;
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
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    UserService userService;

    @GetMapping("/list")
    public Result list(@RequestParam(defaultValue = "1") Integer currentPage){
        Page page = new Page(currentPage,5);
        IPage pageData = orderService.page(page ,new QueryWrapper<Order>().orderByDesc("order_created"));

        return Result.succ(pageData);
    }

    @GetMapping("/getUserOrder/{orderBuyer}")
    public Result getUserOrder(@RequestParam(defaultValue = "1") Integer currentPage,@PathVariable(name = "orderBuyer") long orderBuyer){
        Page page = new Page(currentPage,5);
        IPage pageData = orderService.page(page ,
                new QueryWrapper<Order>().eq("order_buyer",orderBuyer).orderByDesc("order_created"));
        return Result.succ(pageData);

    }

    @GetMapping("/getById/{id}")
    public Result getById(@RequestParam(defaultValue = "1") Integer currentPage,@PathVariable(name = "id") long id){

        Page page = new Page(currentPage,5);
        IPage pageData = orderService.page(page ,
                new QueryWrapper<Order>().eq("id",id));
        return Result.succ(pageData);
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable(name = "id") Long id){

        Order order = orderService.getById(id);

        return Result.succ(order);
    }


    @GetMapping("/creat")
    public Result creat(@Validated @RequestBody OrderDto orderDto){

        Order order = new Order();
        long goodsID = orderDto.getOrderGoods();
        Goods goods = goodsService.getById(goodsID);
        long buyerId = ShiroUtil.getProfile().getId();
        order.setOrderBuyer(buyerId);
        order.setOrderSeller(goods.getGoodsUser());
        order.setOrderCreated(LocalDateTime.now(Clock.system(ZoneId.of("Asia/Shanghai"))));
        order.setOrderEnded(null);
        order.setOrderStatus(0);

        BeanUtil.copyProperties(orderDto,order,"id","orderBuyer","orderSeller",
                "orderCreated","orderEnded","orderStatus");
        orderService.save(order);

        return Result.succ(null);

    }

    @GetMapping("/update")
    public Result update(@RequestBody Order order){
        orderService.update(order,new QueryWrapper<Order>().eq("id",order.getId()));

        return Result.succ(order);
    }


    @GetMapping("/statusEnd/{id}")
    public Result statusEnd(@PathVariable(name = "id") long id){

        UpdateWrapper<Order> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",id)
                .set("order_status",1)
                .set("order_ended",LocalDateTime.now(Clock.system(ZoneId.of("Asia/Shanghai"))));
        Boolean TF = orderService.update(updateWrapper);

        return  Result.succ(TF);

    }

    @GetMapping("/del/{id}")
    public Result del(@PathVariable(name = "id") long id){

        orderService.removeById(id);

        return Result.succ(null);
    }



}
