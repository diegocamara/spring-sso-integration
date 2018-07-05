package com.example.sso.controller;

import org.springframework.web.bind.annotation.RequestMapping;

import com.example.sso.provider.ConstantsMessages;

@RequestMapping(path = ISimpleMVCController.PATH)
public interface ISimpleMVCController {

	String PATH = ConstantsMessages.API_PATH + "/simple";

}
