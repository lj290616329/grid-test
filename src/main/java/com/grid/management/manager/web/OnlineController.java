package com.grid.management.manager.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.grid.management.manager.config.shior.session.entity.UserOnline;
import com.grid.management.manager.config.shior.session.service.SessionService;

@Controller
@RequestMapping("/online")
public class OnlineController {
	
	@Autowired
	private SessionService sessionService;
	
	@GetMapping("lists")
	public String list(Model model) {
		List<UserOnline> list = sessionService.list();
		model.addAttribute("list", list);
		return "/online/lists";
	}
	
	@DeleteMapping("lists")
	public ResponseEntity<Object> delete(String id) {
		System.out.println(id);
		Boolean flag = sessionService.forceLogout(id);
		return new ResponseEntity<Object>(flag,HttpStatus.OK);
	}

}
