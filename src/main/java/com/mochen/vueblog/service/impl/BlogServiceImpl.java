package com.mochen.vueblog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mochen.vueblog.entity.Blog;
import com.mochen.vueblog.mapper.BlogMapper;
import com.mochen.vueblog.service.BlogService;
import org.springframework.stereotype.Service;


@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {

}
