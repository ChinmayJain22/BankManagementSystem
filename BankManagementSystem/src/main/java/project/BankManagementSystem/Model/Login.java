package project.BankManagementSystem.Model;

import org.jnosql.artemis.Entity;

import com.couchbase.client.java.document.json.JsonObject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Login {

	
private String username;
	
	private String password;

	
}
