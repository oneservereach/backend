package com.oneservereach.main.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oneservereach.main.exception.ResourceNotFoundException;
import com.oneservereach.main.model.Connection;
import com.oneservereach.main.repository.ConnectionRepository;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/")
public class ConnectionController {

	@Autowired
	private ConnectionRepository connectionRepository;
	
	// get all connection
	@GetMapping("/connection")
	public List<Connection> getAllConnections(){
		return connectionRepository.findAll();
	}
	
	// create connection rest api
	@PostMapping("/connection")
	public Connection createConnection(@RequestBody Connection connection) {
		return connectionRepository.save(connection);
	}
	
	// get connection by id rest api
	@GetMapping("/connection/{id}")
	public ResponseEntity<Connection> getConnectionById(@PathVariable Long id) throws ResourceNotFoundException {
		Connection connection = connectionRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Connection not exist with id :" + id));
		return ResponseEntity.ok(connection);
	}
	
		// update connection rest api
	
	@PutMapping("/connection/{id}")
	public ResponseEntity<Connection> updateConnection(@PathVariable Long id, @RequestBody Connection connectionDetails) throws ResourceNotFoundException{
		Connection connection = connectionRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Connection not exist with id :" + id));
		
		connection.setAuthorized(connectionDetails.getAuthorized());
		connection.setDomain(connectionDetails.getDomain());	
		
		Connection updatedConnection = connectionRepository.save(connection);
		return ResponseEntity.ok(updatedConnection);
	}
	
	// delete connection rest api
	@DeleteMapping("/connection/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteConnection(@PathVariable Long id) throws ResourceNotFoundException{
		Connection connection = connectionRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Connection not exist with id :" + id));
		
		connectionRepository.delete(connection);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
}
