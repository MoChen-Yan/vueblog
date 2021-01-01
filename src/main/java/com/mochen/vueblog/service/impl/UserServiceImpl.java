package com.mochen.vueblog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mochen.vueblog.entity.User;
import com.mochen.vueblog.mapper.UserMapper;
import com.mochen.vueblog.service.UserService;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
