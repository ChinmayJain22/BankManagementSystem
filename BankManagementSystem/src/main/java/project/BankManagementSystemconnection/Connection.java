//package project.BankManagementSystemconnection;
//
//import java.util.UUID;
//
//import com.couchbase.client.java.Bucket;
//import com.couchbase.client.java.Cluster;
//import com.couchbase.client.java.CouchbaseCluster;
//import com.couchbase.client.java.document.JsonDocument;
//import com.couchbase.client.java.document.json.JsonObject;
//import com.couchbase.client.java.env.CouchbaseEnvironment;
//import com.couchbase.client.java.env.DefaultCouchbaseEnvironment;
//
//public class Connection {
//	public static void main( String[] args ) {
//		
//		CouchbaseEnvironment envr = DefaultCouchbaseEnvironment.builder().connectTimeout(1000).build();
//		
//	  Cluster cluster = CouchbaseCluster.create(envr,"127.0.0.1");
//      cluster.authenticate("Administrator" , "qwerty1234");
//      Bucket bucket = cluster.openBucket("heroes");
//      
//      String uniquekey = UUID.randomUUID().toString();
//      
//      JsonObject json = JsonObject.empty().put("name", "chinmay")
//   		   .put("age", "32")
//   		   .put("email", "chinmay@abc.com")
//   		   .put("address", "Kota");
//      
//      JsonDocument content = JsonDocument.create(uniquekey,json);
//      bucket.insert(content);
//}
//}
