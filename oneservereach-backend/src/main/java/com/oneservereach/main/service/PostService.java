package com.oneservereach.main.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.oneservereach.main.model.Connection;
import com.oneservereach.main.model.Post;
import com.oneservereach.main.repository.ConnectionRepository;
import com.oneservereach.main.repository.PostRepository;

@Service
public class PostService {
	
	@Autowired
	ConnectionRepository connectionRepository;
	
	@Autowired
	PostRepository postRepository;

	public String getAllConnectionPosts1(){
		RestTemplate restTemplate  = new RestTemplate();
	      
		HttpHeaders headers = new HttpHeaders();
	    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	    HttpEntity <String> entity = new HttpEntity<String>(headers);
	    
	    List<Connection> domains = connectionRepository.findAll();
	    
	    JSONArray domainArray = new JSONArray(domains);
	    JSONArray destinationArray = new JSONArray();
	    for (int i = 0; i < domainArray.length(); i++) {
	        String value = domainArray.getJSONObject(i).getString("domain");
	        String dom = restTemplate.exchange(value + "api/v1/post", HttpMethod.GET, entity, String.class).getBody();
		    JSONArray sourceArray = new JSONArray(dom);
		    for (int j = 0; j < sourceArray.length(); j++) {
		        destinationArray.put(sourceArray.getJSONObject(j));
		    }
	    }
	    
	    // sort
	    JSONArray sortedJsonArray = new JSONArray();

	    List<JSONObject> jsonValues = new ArrayList<JSONObject>();
	    for (int i = 0; i < destinationArray.length(); i++) {
	        jsonValues.add(destinationArray.getJSONObject(i));
	    }
	    Collections.sort( jsonValues, new Comparator<JSONObject>() {
	        //You can change "Name" with "ID" if you want to sort by ID
	        private static final String KEY_NAME = "author";

	        @Override
	        public int compare(JSONObject a, JSONObject b) {
	            String valA = new String();
	            String valB = new String();

	            try {
	                valA = (String) a.get(KEY_NAME);
	                valB = (String) b.get(KEY_NAME);
	            } 
	            catch (JSONException e) {
	                //do something
	            }

	            return valA.compareTo(valB);
	            //if you want to change the sort order, simply use the following:
	            //return -valA.compareTo(valB);
	        }
	    });

	    for (int i = 0; i < destinationArray.length(); i++) {
	        sortedJsonArray.put(jsonValues.get(i));
	    }

		return sortedJsonArray.toString();
	}

	public List<Post> getAllConnectionPosts() throws IOException {
		
		ObjectMapper mapper = new ObjectMapper();
		
		RestTemplate restTemplate  = new RestTemplate();
		
		List<Post> connections = new ArrayList<Post>();
	      
		HttpHeaders headers = new HttpHeaders();
	    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	    HttpEntity <String> entity = new HttpEntity<String>(headers);
	    
	    List<Connection> domains = connectionRepository.findAll();
	    
	    Iterator<Connection> iterator = domains.iterator();
	    
	    while(iterator.hasNext()) {
	    	JSONObject jsonObject = new JSONObject(iterator.next());
	    	String domain = jsonObject.getString("domain");
	        String connectionPosts = restTemplate.exchange(domain + "api/v1/post", HttpMethod.GET, entity, String.class).getBody();
	        List<Post> postList = mapper.readValue(connectionPosts, new TypeReference<List<Post>>(){});
	        connections.addAll(postList);
	    }   
		
		//Comparator with Lambda
		//Sort all employees by first name in reverse order
		Comparator<Post> comparator = Comparator.comparing(e -> e.getTitle());
		connections.sort(comparator.reversed());
		
		//Sort natural
		//friends.sort(Comparator.comparing(e -> e.getTitle()));
		
		return connections;
	}
}
