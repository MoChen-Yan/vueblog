package com.mochen.vueblog.service.impl;

import com.mochen.vueblog.entity.Order;
import com.mochen.vueblog.mapper.OrderMapper;
import com.mochen.vueblog.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author MoChen出品，必是精品
 * @since 2021-02-04
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

}
