package com.grid.management.manager.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {
	
	@GetMapping("/login")
    public String login(HttpServletRequest request,Model model) {
    	String encoding = System.getProperty("file.encoding");  
        System.out.println("\n当前编码:" + encoding);  
        model.addAttribute("errmsg", "请输入账号和密码");
        return "/account/login";
    }
	
	@GetMapping("/fail")
    public String fail(HttpServletRequest request,Model model) {
        model.addAttribute("errmsg", "没权限啥的");
        return "fail";
    }
	@GetMapping("/error")
	public String error(HttpServletRequest request,Model model) {
		model.addAttribute("errmsg", "错误了");
		return "error";
	}
	
	@GetMapping("/index")
	public String index(HttpServletRequest request,Model model) {
		return "index";
	}
	@GetMapping("/kickout")
	public String kickout(HttpServletRequest request,Model model) {
		model.addAttribute("errmsg", "你被提下线了,知道吗?");
		return "success";
	}
	
	@ResponseBody
	@PostMapping("/tologin")
    public ResponseEntity<Object> tologin(String name,String password,Model model) {
		Map<String,Object> map = new HashMap<String, Object>();
		Subject subject =SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(name,password);
		try {
			subject.login(token);
			//登录成功
			map.put("code", 200);
			map.put("errmsg", "登录成功!");
		} catch (UnknownAccountException e) {
			// TODO Auto-generated catch block
			//登录失败
			map.put("code", 400);
			map.put("errmsg", "你被禁止登录了,不知道吗?");
		}catch(ExcessiveAttemptsException e1) {
			map.put("code", 400);
			map.put("errmsg", "尝试登录超过5次，账号已冻结，30分钟后再试");
		}catch(IncorrectCredentialsException e2) {
			map.put("code", 400);
			map.put("errmsg", "估计密码错误哦!");
		}
		return new ResponseEntity<Object>(map,HttpStatus.OK);
    	
    }
}
