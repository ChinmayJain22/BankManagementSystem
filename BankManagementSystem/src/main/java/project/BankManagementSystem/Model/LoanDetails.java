package project.BankManagementSystem.Model;

import java.util.Date;

import org.jnosql.artemis.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class LoanDetails {
	
	private String loanType;
	private String loanAmount;
	private String date;
	private String rateOfInterest;
	private String durationOfLoan;

}
