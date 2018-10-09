package com.logindemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class TestController {
	@RequestMapping(value = "/test")
    public String index(ModelMap modelMap) {
		return "blank";
    }
}
