package com.example.sso.mediator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sso.dao.ICRUDDAO;
import com.example.sso.dao.SimpleMVCDAO;
import com.example.sso.model.Simple;

@Service
public class SimpleMVCMediator extends AbstractCRUDMediator<Simple, Integer> implements ISimpleMVCMediator {

	@Autowired
	private SimpleMVCDAO simpleMVCDAO;

	@Override
	protected ICRUDDAO<Simple, Integer> getDAO() {
		return this.simpleMVCDAO;
	}

}
