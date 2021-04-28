package steps;






import java.net.URI;

import org.junit.Assert;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;




public class logoutSteps {

	private RequestSpecification request ;
	private Response response;
   private RestTemplate restTemplate;
	
	private String correctUsernameAndPasswordJson;
	
	private String serviceUrl;
	
	private String answer;
	
	private ResponseEntity<String> result;
	
	
	@Given("I am on login page with {string} and {string}")
	public void i_am_on_login_page_with_and(String string, String string2) {
	
	restTemplate = new RestTemplate();
	correctUsernameAndPasswordJson = "{ \"username\":"+"\""+string+"\""+","
	
			+" \"password\":"+"\""+string2+"\""+" }";
	
	serviceUrl = "http://localhost:8080/login";
	
	HttpHeaders headers = new HttpHeaders();
	headers.setContentType(MediaType.APPLICATION_JSON);
	HttpEntity<String> entity = new HttpEntity<String>(correctUsernameAndPasswordJson, headers);
	answer = restTemplate.postForObject(serviceUrl, entity, String.class);
	
	
	
    
}

@When("^the customer calls /logout$")
public void the_customer_calls_logout() throws Throwable {
	
serviceUrl = "http://localhost:8080/logout";
	
	HttpHeaders headers = new HttpHeaders();
	headers.setContentType(MediaType.APPLICATION_JSON);
	HttpEntity<String> entity = new HttpEntity<String>( headers);
	URI uri = new URI(serviceUrl);
	 
	 result = restTemplate.getForEntity(uri, String.class);
  
}

@Then("^customer receives status code of (\\d+)$")
public void customer_receives_status_code_of(int statuscode) throws Throwable {
//   response.then().statusCode(statuscode).log().all();
//	 Assert.isTrue(response.getStatusCode()==200);
   Assert.assertEquals(200, result.getStatusCodeValue());
}



	
}
