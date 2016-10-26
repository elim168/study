package com.elim.learn.jpa.spring.data.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.elim.learn.jpa.spring.data.dao.OrganizationRepository;
import com.elim.learn.jpa.spring.data.service.OrganizationService;

@Transactional
@Service
public class OrganizationServiceImpl implements OrganizationService {

	@Autowired
	private OrganizationRepository orgRepository;
	
	@Override
	public void updateNo(String no, Integer id) {
		orgRepository.updateNo(no, id);
	}

}
