package com.example.sso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.sso.mediator.ICRUDMediator;
import com.example.sso.mediator.ISimpleMVCMediator;
import com.example.sso.model.Simple;

@RestController
public class SimpleMVCController extends AbstractRestController<Simple, Integer> implements ISimpleMVCController {

	@Autowired
	private ISimpleMVCMediator simpleMVCMediator;

	@GetMapping(value = "/testing")
	@ResponseStatus(HttpStatus.OK)
	public String testing() throws Exception {		
		return "testing";
	}

	@Override
	public ICRUDMediator<Simple, Integer> getCRUDMediator() {
		return this.simpleMVCMediator;
	}

}
