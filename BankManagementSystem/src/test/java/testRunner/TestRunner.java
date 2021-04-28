package testRunner;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
		features = {"features"},
				glue = {"steps"},
		plugin = {"pretty","json:report2"},
		tags = "@P4"
		)

public class TestRunner {
	
	
}
