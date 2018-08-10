package com.logindemo.controller;

import javax.servlet.http.HttpSession;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.logindemo.adapter.WebSecurityConfig;

@Controller
public class LoginController implements ErrorController{
	
	@RequestMapping("/")
	public String greeting(Model model){
		return "index";
		
	}
	
	@RequestMapping("/error")
	public String error(Model model){
		return "404";
		
	}
	
	@RequestMapping("/login")
	    public String login(){
	        return "login";
	    }
	
	@RequestMapping("/loginVerify")
    public String loginVerify(String username,String password,HttpSession session){
        if ("wenjie".equals(username)) {
            session.setAttribute(WebSecurityConfig.SESSION_KEY, username);
            return "index";
        } else {
            return "redirect:/login";
        }
    }

	@Override
	public String getErrorPath() {
		return "/error";
	}

}
