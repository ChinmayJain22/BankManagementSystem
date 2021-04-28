package project.BankManagementSystem;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutionException;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.env.CouchbaseEnvironment;
import com.couchbase.client.java.env.DefaultCouchbaseEnvironment;
import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.cloud.FirestoreClient;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import project.BankManagementSystem.Model.Customer;
import project.BankManagementSystem.Model.LoanDetails;
import project.BankManagementSystem.Model.Login;

public class App {
	public static void main(String[] args) throws Exception {
//       System.out.println( "Hello World!" );
		
		
		
		Logger logger = LoggerFactory.getLogger(App.class);
		logger.info("Hello world");
		
		Firestore db;
		
		 String bms = "{\r\n"
		 		+ "  \"type\": \"service_account\",\r\n"
		 		+ "  \"project_id\": \"bankmanagementsystem\",\r\n"
		 		+ "  \"private_key_id\": \"d61c72f0571be58f196d08f4d8fe716e9a87aa1e\",\r\n"
		 		+ "  \"private_key\": \"-----BEGIN PRIVATE KEY-----\\nMIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCVbnFelOF/tnNt\\n7OClvyLPV4O3fEr/YsMl7NB1gM2mWzLKYWZdVCFJ6fVCN4cLkP4e6BTt91ihQbSi\\n3dUKVroGZshd7tY7F86MaoweRckOLpqRDcu+SvQgMQZzvR+RFCGatdGhASnxdUSe\\n+6/nz/cV1Ui0H6oyN4qowpzr6ZzCAJZSqgdKRVnT2XUxp99E7+wrSvX6Nhhz3oic\\ngA7D8CqDek1KMT3lJ7wNC12+XeFOSGCRTgzqlVSvLLYN02TVk6dTSoi+O+eKCf87\\nyDSlSr4V+R5bLElV+4zvbec3ptz11V6erc/HN5x+BYUAvK6Aj1oB1SI0sdcQPuG5\\nGCqrUgubAgMBAAECggEAAkNcuGmntmuP+RaPNxqGEv7vwdzemQUhyX2WkU87+MWT\\nQ66Cnu4eeoCnXs+yenOWta47QD/UMs3o8o71TOyscMyZ7c+ZMzuw9Xc8hnhm/QM4\\nWEh/igFCKJeK0gd5WFwmP3lBsPRwjryd0f6VnfyacNU1xayZGIDv86sG4SmSQlEC\\nQgHmLGeWtBnpaK1IoW1zbFlEMIsJkF/504Jl2OTNftSMTeuzccLRUTpBcb2YgIuE\\nKY0GnWE7Glyaae/HNNp0Umdf8GI4hVRk4vKQm/XH2hlUNdhYGHUvw0yBbDgPdh40\\nfZlDd3NTb8o3LGu6MFzeLW1csHrr1jvOUNKfqhmmIQKBgQDFKQiFVa7lnuJoLdB4\\nxAGUetbsuME7GPIL563t5JwWowx3e3O58Y3c1B4h3BvUH9SHRh2meDdt+zyUq4RO\\nYXog0EH/MsmLs4ZzBBvD7zZOIvnChRmI1T4MAaEUP1bFDO/MS+sD0d1PusGS1iau\\nMgEQYd+RPLlNqPP6roZ4EztxEQKBgQDCBvFx4tLsMOitJWvwvL9ahVFAAzXnkM3x\\ngUPWJMVC8QK8Jw/tUlyyMHfZrK+w3foK8LmoK0cqaWOQheGMpbU05wM7r75a7BYi\\nRHtZZEajDGYhuwybGSfTDTJ1h9mIWHYqQpqhXFhSqkA62a6TWvqIMdTdyJXX8XU7\\nThLnkU0x6wKBgQDBsEkFrCJQxGEKFZALRAuFUZRZJDv4kc4f8e+FmKPa/T0fGDGd\\nh3dlbSFZBEJ2hvqkFjbEtq9FpZ5jM4ib89kl6VIcokkrrCc2UN/yAcvowSV6eoWW\\nTl9RLjyPhpX4qXfezOpJa1+ulvraBPYlBUzUSkJ3DR/B8mwVZmUGC2bG8QKBgDWQ\\nDR9PasLhTZpe9kljEcA/hYqSWFMw2mmNMdjwFbTBK+wdLHxrfEOEtdFVprrAzMIw\\nalBOgjrnFSFUlEtrg3azP2U+wuH+wq17EkI/OHDSf29YX8aBT0koH4HqmtbU9On9\\nsQpSn99SOKEGbvcm6nqmpwkC0rrmYWKZzwY2SoU5AoGBAJNPUikTrqSpQr37RWkW\\nLQWg9tzxnZRasidBRQeUtbo/RXJn/ZBfnxr90R035Uu7v4x01mFvxx4iYwWWQNEH\\ngLyKX6dLQYg8kZTdHdkCFHLCzde9kG0oF8zfpGvBsjI/mKkY+dgxPVFTY44C01L2\\nmAaMsn1pYpoA5HTG7ssPzV6p\\n-----END PRIVATE KEY-----\\n\",\r\n"
		 		+ "  \"client_email\": \"firebase-adminsdk-w8u1u@bankmanagementsystem.iam.gserviceaccount.com\",\r\n"
		 		+ "  \"client_id\": \"118083294823439280706\",\r\n"
		 		+ "  \"auth_uri\": \"https://accounts.google.com/o/oauth2/auth\",\r\n"
		 		+ "  \"token_uri\": \"https://oauth2.googleapis.com/token\",\r\n"
		 		+ "  \"auth_provider_x509_cert_url\": \"https://www.googleapis.com/oauth2/v1/certs\",\r\n"
		 		+ "  \"client_x509_cert_url\": \"https://www.googleapis.com/robot/v1/metadata/x509/firebase-adminsdk-w8u1u%40bankmanagementsystem.iam.gserviceaccount.com\"\r\n"
		 		+ "}";
		 
		InputStream serviceAccount =new ByteArrayInputStream(bms.getBytes(StandardCharsets.UTF_8));

				FirebaseOptions options = new FirebaseOptions.Builder()
				  .setCredentials(GoogleCredentials.fromStream(serviceAccount))
				  .build();

				FirebaseApp.initializeApp(options);
				db = FirestoreClient.getFirestore();
				

		Customer customer = new Customer();
		
		Vertx vertx = Vertx.vertx();

		HttpServer httpServer = vertx.createHttpServer();

		Router router = Router.router(vertx);
		Route handler2 = router.post("/register").handler(BodyHandler.create()).handler(routingContext -> {
			final Customer employee = Json.decodeValue(routingContext.getBody(), Customer.class);
			
			
			DocumentReference docRef = db.collection("Client").document(employee.getUsername()+employee.getPassword());
			ApiFuture<WriteResult> result = docRef.set(employee);

			HttpServerResponse response = routingContext.response();
			response.setChunked(true);
			response.end("Customer added successfully");
		
			logger.info("customer : {}", result);

		});

		Route handler1 = router.post("/login").handler(BodyHandler.create()).handler(routingContext -> {
			final Login login = Json.decodeValue(routingContext.getBody(), Login.class);
			
			DocumentReference docRef = db.collection("Client").document(login.getUsername()+login.getPassword());
	    	ApiFuture<DocumentSnapshot> future = docRef.get();
	    	try {
	    		
				DocumentSnapshot document = future.get();
				if(document.exists()) {
					
					customer.setName(document.toObject(Customer.class).getName());
					customer.setUsername(document.toObject(Customer.class).getUsername());
					customer.setPassword(document.toObject(Customer.class).getPassword());
					customer.setState(document.toObject(Customer.class).getState());
					customer.setCountry(document.toObject(Customer.class).getCountry());
					customer.setPan(document.toObject(Customer.class).getPan());
					customer.setAccountNumber(document.toObject(Customer.class).getAccountNumber());
					customer.setAccounttype(document.toObject(Customer.class).getAccounttype());
					customer.setEmail(document.toObject(Customer.class).getEmail());
					customer.setAddress(document.toObject(Customer.class).getAddress());
					customer.setContactnumber(document.toObject(Customer.class).getContactnumber());
					
					HttpServerResponse response = routingContext.response();
					response.setChunked(true);
					response.end("login successfully");
					logger.info("customer : {}", "login successufully");
		
				}
				
				 else {
						HttpServerResponse response = routingContext.response();
						response.setChunked(true);
						response.end("Invalid Credential");
						logger.error("customer : {} ", "Invalid Credential");
					}
				
			} catch (InterruptedException | ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		Route handler3 = router.post("/update").handler(BodyHandler.create()).handler(routingContext -> {
			final Customer customer1;

			if (customer.getName() == "") {
				HttpServerResponse response = routingContext.response();
				response.setChunked(true);
				response.end("User Not logged In ");
			} else {

				customer1 = Json.decodeValue(routingContext.getBody(), Customer.class);
				
				db.collection("Client").document(customer.getUsername()+customer.getPassword()).delete();
				
		    	try {
		    		
		    		customer.setName(customer1.getName());
					customer.setUsername(customer1.getUsername());
					customer.setPassword(customer1.getPassword());
					customer.setAccountNumber(customer1.getAccountNumber());
					customer.setAccounttype(customer1.getAccounttype());
					customer.setAddress(customer1.getAddress());
					customer.setContactnumber(customer1.getContactnumber());
					customer.setCountry(customer1.getCountry());
					customer.setEmail(customer1.getEmail());
					customer.setPan(customer1.getPan());
					customer.setState(customer1.getState());
						
						DocumentReference docRef = db.collection("Client").document(customer1.getUsername()+customer1.getPassword());
						ApiFuture<WriteResult> result = docRef.set(customer1);
			
						
				HttpServerResponse response = routingContext.response();
				response.setChunked(true);
				response.end("Employee updated successfully");
		    	}
		    	 catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		    	
			}
		});

		Route handler4 = router.get("/logout").handler(routingContext -> {

			if (customer.getName() == "") {

				HttpServerResponse response = routingContext.response();
				response.setChunked(true);
				response.end("User Not logged In ");
				logger.error("user : {}",response);

			} else {

				customer.setName("");
				HttpServerResponse response = routingContext.response();
				response.setChunked(true);
				response.end("Logout successfully ");
				logger.info("customer : {}", response);

			}
		});

		Route handler5 = router.post("/applyforloan").handler(BodyHandler.create()).handler(routingContext -> {
			if(customer.getUsername() != "") {
				final LoanDetails loan = Json.decodeValue(routingContext.getBody(), LoanDetails.class);
				
				DocumentReference docRef = db.collection("Client").document(customer.getAccountNumber());
				ApiFuture<WriteResult> result = docRef.set(loan);
				
				HttpServerResponse response = routingContext.response();
				response.setChunked(true);
				response.end("Loan Applied Successfully");
				
			}
			else {
				HttpServerResponse response = routingContext.response();
				response.setChunked(true);
				response.end("User Not logged In ");
				
			}
			
		});
		



		httpServer.requestHandler(router::accept).listen(8080);

	}

}
