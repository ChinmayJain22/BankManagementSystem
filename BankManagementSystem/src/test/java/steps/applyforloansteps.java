package steps;


import static org.junit.Assert.assertEquals;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import io.cucumber.java.en.When;


public class applyforloansteps {
	
	
	
   private RestTemplate restTemplate;
	
	private String DetailsJson;
	
	private String serviceUrl;
	
	private String answer;
	
	private ResponseEntity<String> result;

	@When("the customer applies for loan {string} and {string} and {string} and {string} and {string}")
	public void the_customer_applies_for_loan_and_and_and_and(String string3, String string4, String string5, String string6, String string7) {
		restTemplate = new RestTemplate();
		serviceUrl = "http://localhost:8080/applyforloan";
		
		DetailsJson = "{ \"loanType\":"+"\""+string3+"\""+","
				
					+" \"loanAmount\":"+"\""+string4+"\""+","
					+" \"date\":"+"\""+string5 +"\""+","
					+" \"rateOfInterest\":"+"\""+string6 +"\""+","
					+" \"durationOfLoan\":"+"\""+ string7 + "\""+" }";
		
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(DetailsJson, headers);
		answer = restTemplate.postForObject(serviceUrl, entity, String.class);
		
		  assertEquals("Loan Applied Successfully", answer);
	}
}
