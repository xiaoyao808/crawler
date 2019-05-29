package com.cpx.controller;

import com.cpx.interfaces.JsoupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("jsoupApp")
public class JsoupController {

    @Autowired
    private JsoupService jsoupService;
    /**
     * 方法实现说明
     * @author      cpx
     * @return      void
     * @exception
     * @date        2019/5/21 9:04
     */
    @RequestMapping("switchJsoup")
    public void switchJsoup(boolean flag){
        jsoupService.switchJsoup(flag);
    }
}
