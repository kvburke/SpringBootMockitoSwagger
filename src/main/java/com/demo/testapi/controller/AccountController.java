package com.demo.testapi.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.testapi.model.Account;
import com.demo.testapi.model.Password;
import com.demo.testapi.model.User;


@RestController
@RequestMapping("/accounts")
public class AccountController {
	
	private Map<String, String> accountHolder = new HashMap<String, String>();
	
	
	@PostMapping(value="/createAccount", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces= {MediaType.APPLICATION_JSON_VALUE})
	public void createUserAccount(@RequestBody Account account) {
		String user=account.getUsername();
		String password=account.getPassword();
		accountHolder.put(user, password);
	}
	
	@PostMapping(value="/getPassword", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Password getUserPassword(@RequestBody User user) {
		String password=accountHolder.get(user.getUsername());
		Password passwordobj = new Password();
		passwordobj.setPassword(password);
		return passwordobj;
	}
	

}
