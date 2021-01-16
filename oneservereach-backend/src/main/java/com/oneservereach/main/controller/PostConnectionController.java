package com.oneservereach.main.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

public class PostConnectionController {
	// get all connection posts
	@RestController
	public class PostConnectionControler{
	   @Autowired
	   RestTemplate restTemplate;

	   @RequestMapping(value = "/conpost")
	   public String getProductList() {
	      HttpHeaders headers = new HttpHeaders();
	      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	      HttpEntity <String> entity = new HttpEntity<String>(headers);
	      
	      return restTemplate.exchange("http://localhost:8080/api/v1/post", HttpMethod.GET, entity, String.class).getBody();
	   }
	}
}
