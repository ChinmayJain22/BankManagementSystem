package steps;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Assert;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


public class loginsteps {
	
	private RequestSpecification request;
	private Response response;
	
	private RestTemplate restTemplate;
	
	private String correctUsernameAndPasswordJson;
	
	private String serviceUrl;
	
	private String answer;

	@Given("I want to login with {string} and {string}")
	public void i_want_to_login_with_and(String string, String string2) {
		restTemplate = new RestTemplate();
		correctUsernameAndPasswordJson = "{ \"username\":"+"\""+string+"\""+","
		
				+" \"password\":"+"\""+string2+"\""+" }";
		
		serviceUrl = "http://localhost:8080/login";
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(correctUsernameAndPasswordJson, headers);
		answer = restTemplate.postForObject(serviceUrl, entity, String.class);
		assertNotNull(answer);
		assertEquals("login successfully", answer);
		
	}

	@When("I click on login in step")
	public void i_click_on_login_in_step() {
		
		
		
	}

	@Then("I verify the <statuscode>")
	public void i_verify_the_statuscode() {
		
	}
}
