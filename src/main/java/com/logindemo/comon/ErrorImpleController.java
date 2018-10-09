package com.logindemo.comon;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorImpleController implements ErrorController{
	
	@RequestMapping("/error")
	public String error(Model model){
		return "404";
		
	}

	@Override
	public String getErrorPath() {
		// TODO Auto-generated method stub
		return "error2";
	}
	
}
