Feature: BankManagement Scenario

@P1
Scenario Outline: customer makes call to GET /logout
Given I am on login page with <username> and <password>
When the customer calls /logout
Then customer receives status code of 200

Examples: 
      | username  | password | status  |
      | "chinnu" |    "qwerty" | 200 |

@P2
Scenario Outline: To check customer login 
    Given I want to login with <username> and <password>
    When I click on login in step
    Then I verify the <statuscode>

    Examples:
      | username  | password | status  |
      | "sachin" |    "qwerty" | 200 |
      | "chinmay"| "qwerty" | 200|
      | "sfef" | "wrw" | 200|
  
@P3
Scenario Outline: To check customer registration 
    Given I want to register with <username> and <password> and <name> and <address> and <state> and <country> and <email> and <pan> and <contactnumber> and <accounttype> 
    When I click on register in step
    Then I verify the <statuscode>
    Examples:
    | username  | password | name  | address  | state | country  |   email  | pan  | contactnumber | accounttype  | status  |
    | "ramesh"  | "qwerty" | "sachin"  | "Kota"  | "rajasthsn" | "India"  |   "chinmay@abc.com"  | "qwedwsr1"  | "1234567" | "carloan"  |  200  |
     
@P4
Scenario Outline: To Apply for loan to customer
Given I am on login page with <username> and <password>
When the customer applies for loan <loanType> and <loanAmount> and <date> and <rateOfInterest> and <durationOfLoan> 
Then  I verify the <statuscode>

	Examples: 
      | username  | password|loanType | loanAmount |date  | rateOfInterest | durationOfLoan| status|
      | "chinnu" | "qwerty"|  "carloan"| "123355"  | "12-12-2021"  | "10.5" | "5"  |200|
 
@P5
Scenario Outline: To update customer details
Given I am on login page with <username> and <password>  
When the customer wants to  update <updated_username> and <updated_password> and <updated_name> and <updated_address> and <updated_state> and <updated_country> and <updated_email> and <updated_pan> and <updated_contactnumber> and <updated_accounttype> 
Then  I verify the <statuscode>

 Examples:
    | username  | password | updated_username  | updated_password | updated_name  | updated_address  | updated_state | updated_country  |   updated_email  | updated_pan  | updated_contactnumber | updated_accounttype  | status  |
    | "chinmay"  | "qwerty" | "chinmay1234"  | "qwerty12" | "chinmayJain"  | "baran"  | "rajasthan" | "India"  |   "chinnu1234@abc.com"  | "qwedwsrrfe1"  | "123356789" | "bikeloan"  |  200  |
     

  
