
 */
import org.junit.Test;
import static org.junit.Assert.*;

import org.junit.Before;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import javax.json.Json;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.filter.log.ResponseLoggingFilter;
import com.jayway.restassured.response.Header;
import com.jayway.restassured.response.Response;

import org.json.simple.parser.JSONParser;

public class UserTestUnauthorized {
	JSONParser jsonParser = new JSONParser();
	Header contentHeader = new Header("Content-Type","application/json; charset=utf-8");
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	Date date = new Date();
	
	
	@Before
	public void setup() {
		//no aut0token
		//For the sake of reading the URL is stored here would put in properties file
		RestAssured.baseURI = "https://api.qa.fitpay.ninja";
		RestAssured.useRelaxedHTTPSValidation();
		
	}
	
    @Test public void get_10_Users_unauthorized() {   	
    	/*
    	 * A basic test to endpoint https://qa.fitpay.com/users which returns a list
    	 * of all users on the site
    	 */

    			Response response = RestAssured
    					.given()
	    					.header(contentHeader)
	    					.filter(ResponseLoggingFilter.logResponseIfStatusCodeIs(200))
	    				.when()
    						.get("/users?limit=10")
	    				.then()
	    					.statusCode(401)
	    					.extract()
	    					.response();
    			
    	   		String message = response.path("message");
        		
    	    	assertEquals("Unauthorized Error", "error Unauthorized", message);
    	
    }
    
    @Test public void create_a_User_unauthorized() {   	
    	/*
    	 * A basic test to post endpoint https://qa.fitpay.com/users 
    	 * to create a user
    	 */
    	String newUserId = UUID.randomUUID().toString();
    	String createdTime = sdf.format(date).toString();
    	String jsonString = Json.createObjectBuilder()
                .add("id", newUserId)
                .add("createdTs", createdTime)
                .add("createdTsEpoch","1501109470574")
                .toString();
    	
    	
    	
    			Response response = RestAssured
    					.given()
	    					.header(contentHeader)
	    					.body(jsonString)
	    					.filter(ResponseLoggingFilter.logResponseIfStatusCodeIs(200))
	    				.when()
    						.post("/users")
	    				.then()
	    					.statusCode(401)
	    					.extract()
	    					.response();
    			
    	   		String message = response.path("message");
        		
    	    	assertEquals("Unauthorized Error", "error Unauthorized", message);
    	
    }
    
    @Test public void update_a_User_unauthorized() {    	

    	String newUserId = "eb787fc0-7bc7-48fb-bcc7-7e64f9a99835";
    	String modifiedTime = sdf.format(date).toString();
    	String jsonString = Json.createObjectBuilder()
                .add("id", newUserId)
                .add("createdTs", "2017-07-26T22:51:10.574Z")
                .add("createdTsEpoch","1501109470574")
                .add("lastModifiedTs",modifiedTime)
                .add("lastModifiedTsEpoch","1501109470574")
                .toString();
    	
    
    			Response response = RestAssured
    					.given()
	    					.header(contentHeader)
	    					.body(jsonString)
	    					.filter(ResponseLoggingFilter.logResponseIfStatusCodeIs(200))
	    				.when()
    						.put("/users")
	    				.then()
	    					.statusCode(401)
	    					.extract()
	    					.response();
    			
    	   		String message = response.path("message");
        		
    	    	assertEquals("Unauthorized Error", "error Unauthorized", message);
    	
    }
    
  
}
