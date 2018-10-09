package com.logindemo.comon;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/decorators")
public class DecoratorController {
	
	@RequestMapping(value = "/index")
    public String index(ModelMap modelMap) {
		return "decorators/layout";
    }
}
