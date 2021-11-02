package com.codeforge.codeforgeDemo;

import com.codeforge.codeforgeDemo.model.api.ApiResponse;
import com.codeforge.codeforgeDemo.model.dto.Release;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.LinkedHashMap;
import java.util.List;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class CodeforgeDemoApplicationTests {

	private final String jsonPayloadValid = "{\n" +
			"    \"releaseName\":\"METAL IS WAR\",\n" +
			"    \"releaseDescription\":\"111\",\n" +
			"    \"releaseDate\":\"2021-12-01\",\n" +
			"    \"releaseStatus\":\"QA Done on DEV\"\n" +
			"}";

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Test
	void contextLoads() {
	}

	@Test
	public void getSampleResponseTest() {

		ResponseEntity<Release> response = this.testRestTemplate.exchange("http://localhost:" + port + "/releases/65",
				HttpMethod.GET, null, Release.class);
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void insertSingleReleaseTest() {

		var headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> requestEntity = new HttpEntity<>(jsonPayloadValid, headers);
		ResponseEntity<ApiResponse> response = this.testRestTemplate.exchange(("http://localhost:" + port + "/releases/"),
				HttpMethod.POST, requestEntity, ApiResponse.class);
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	/**
	 * Updates the last entry from the collection
	 * @throws JsonProcessingException
	 */
	@Test
	public void updateReleaseTest() throws JsonProcessingException {

		ObjectMapper objectMapper = new ObjectMapper();
		ResponseEntity<ApiResponse> response = this.testRestTemplate.exchange("http://localhost:" + port + "/releases/",
				HttpMethod.GET, null, ApiResponse.class);
		List<LinkedHashMap<String, ?>> objectList = (List<LinkedHashMap<String, ?>>) response.getBody().getEntity();
		List<Release> releaseList = objectMapper.convertValue(objectList, new TypeReference<List<Release>> () {});

		var releaseForUpdate = releaseList.get(releaseList.size() - 1);
		releaseForUpdate.setReleaseDescription("Updated description");
		releaseForUpdate.setReleaseName("Updated name");
		String requestPayload = objectMapper.writeValueAsString(releaseForUpdate);

		var headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> requestEntity = new HttpEntity<>(jsonPayloadValid, headers);

		ResponseEntity<?> deletionResponse = this.testRestTemplate.exchange("http://localhost:" + port
						+ "/releases/" + releaseForUpdate.getId(),
				HttpMethod.PUT, requestEntity, ApiResponse.class);
		Assert.assertEquals(HttpStatus.OK, deletionResponse.getStatusCode());
	}

	/**
	 * Deletes the last entry from the collection
	 */
	@Test
	public void deleteReleaseTest() {

		ObjectMapper objectMapper = new ObjectMapper();
		ResponseEntity<ApiResponse> response = this.testRestTemplate.exchange("http://localhost:" + port + "/releases/",
				HttpMethod.GET, null, ApiResponse.class);
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
		List<LinkedHashMap<String, ?>> objectList = (List<LinkedHashMap<String, ?>>) response.getBody().getEntity();
		List<Release> releaseList = objectMapper.convertValue(objectList, new TypeReference<List<Release>> () {});

		var releaseForDeletion = releaseList.get(releaseList.size() - 1);
		ResponseEntity<?> deletionResponse = this.testRestTemplate.exchange("http://localhost:" + port
						+ "/releases/" + releaseForDeletion.getId(),
				HttpMethod.DELETE, null, ApiResponse.class);
		Assert.assertEquals(HttpStatus.NO_CONTENT, deletionResponse.getStatusCode());

	}

	

}
