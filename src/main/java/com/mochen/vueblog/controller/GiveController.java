package com.mochen.vueblog.controller;


import com.mochen.vueblog.service.GiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author MoChen出品，必是精品
 * @since 2021-01-15
 */
@RestController
@RequestMapping("/give")
public class GiveController {

    @Autowired
    GiveService giveService;




}
