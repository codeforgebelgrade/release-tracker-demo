package com.codeforge.codeforgeDemo;

import com.codeforge.codeforgeDemo.model.dto.Release;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class CodeforgeDemoApplicationTests {

	@Test
	void contextLoads() {
	}

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Test
	public void getSampleResponseTest() {

		ResponseEntity<Release> response = this.testRestTemplate.exchange("http://localhost:" + port + "/releases/65",
				HttpMethod.GET, null, Release.class);
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
	}


	@Test
	public void insertSingleReleaseTest() {

		Release release = new Release();
		release.setReleaseName("Test release");
		release.setReleaseStatus("Created");
		release.setReleaseDescription("Blah...");
		release.setReleaseDate("2021-12-12");
		String jsonPayload = "{\n" +
				"    \"releaseName\":\"METAL IS WAR\",\n" +
				"    \"releaseDescription\":\"66688899990000\",\n" +
				"    \"releaseDate\":\"2021-11-11\",\n" +
				"    \"releaseStatus\":\"QA Done on DEV\"\n" +
				"}";
		var headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> requestEntity = new HttpEntity<>(jsonPayload, headers);
		ResponseEntity<Release> response = this.testRestTemplate.exchange(("http://localhost:" + port + "/releases/"),
				HttpMethod.POST, requestEntity, Release.class);
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	

}
