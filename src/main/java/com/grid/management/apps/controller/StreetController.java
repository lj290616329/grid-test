package com.grid.management.apps.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.grid.management.apps.entity.Street;
import com.grid.management.apps.service.StreetService;

@RestController
@RequestMapping("/api")
public class StreetController {
	
	@Autowired
	StreetService streetService;
	
	/**
	 * 获取所有
	 * @param id
	 * @param model
	 * @return
	 */
	@GetMapping("/streets")
	public ResponseEntity<Object> streets(@RequestParam(required=false) String name) {
		return new ResponseEntity<Object>(streetService.findByName(name),HttpStatus.OK);
	}
	
	/**
	 * 获取单个
	 * @param id
	 * @param model
	 * @return
	 */
	@GetMapping("/streets/{id}")
	public ResponseEntity<Object> get(@PathVariable("id") String id) {
		System.out.println(id);
		//return streetService.getById(id);
		return new ResponseEntity<Object>(streetService.getById(id),HttpStatus.OK);
	}
	/**
	 * 新增
	 * @param model
	 * @return
	 */
	@PostMapping("/streets")
	public ResponseEntity<Object> insert(@RequestBody Street street) {
		int result = streetService.insert(street);
		return new ResponseEntity<Object>(result,HttpStatus.OK);
	}
	
	/**
	 * 修改
	 * @param model
	 * @return
	 */
	@PutMapping("/streets")
	public ResponseEntity<Object> update(@RequestBody Street street) {
		int result = streetService.update(street);
		return new ResponseEntity<Object>(result,HttpStatus.OK);
	}
	
	/**
	 * 删除
	 * @param model
	 * @return
	 */
	@DeleteMapping("/streets/{id}")
	public ResponseEntity<Object> delete(@PathVariable("id") String id) {
		int result = streetService.delete(id);
		return new ResponseEntity<Object>(result,HttpStatus.OK);
	}
}
