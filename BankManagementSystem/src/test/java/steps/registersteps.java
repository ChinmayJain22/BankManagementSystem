package steps;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class registersteps {
	
	private RequestSpecification request;
	private Response response;
	
	private RestTemplate restTemplate;
	
	private String serviceUrl;
	
	private String DetailsJson;
	
	private String answer;
	
	
	@Given("I want to register with {string} and {string} and {string} and {string} and {string} and {string} and {string} and {string} and {string} and {string}")
	public void i_want_to_register_with_and_and_and_and_and_and_and_and_and(String string, String string2, String string3, String string4, String string5, String string6, String string7, String string8, String string9, String string10) {
		restTemplate = new RestTemplate();
		serviceUrl = "http://localhost:8080/register";
		
		DetailsJson = "{ \"username\":"+"\""+string+"\""+","
				
					+" \"password\":"+"\""+string2+"\""+","
					+" \"name\":"+"\""+string3 +"\""+","
					+" \"address\":"+"\""+string4 +"\""+","
					+" \"state\":"+"\""+ string5 + "\""+","
					+" \"country\":"+"\""+ string6 + "\""+","
					+" \"email\":"+"\""+string7+"\""+","
					+" \"pan\":"+"\""+string8+"\""+","
					+" \"contactnumber\":"+"\""+string9+"\""+","
					+" \"accounttype\":"+"\""+string10+"\""+" }";
		
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(DetailsJson, headers);
		answer = restTemplate.postForObject(serviceUrl, entity, String.class);
		assertNotNull(answer);
		assertEquals("Customer added successfully", answer);
	}

	@When("I click on register in step")
	public void i_click_on_register_in_step() {
	    
	}
	


	




	

}
