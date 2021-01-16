package com.oneservereach.main.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oneservereach.main.model.Connection;
import com.oneservereach.main.repository.ConnectionRepository;

@Service
public class ConnectionService {
	
	@Autowired
	ConnectionRepository connectionRepository;

	public List<Connection> findAll() {
	return connectionRepository.findAll();
	}
}
