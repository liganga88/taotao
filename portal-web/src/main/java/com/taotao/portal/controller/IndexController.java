package com.taotao.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 2017-8-21.
 */
@Controller
public class IndexController {
    @RequestMapping("/index")
    public String showIndex(){
        return "index";
    }
}
