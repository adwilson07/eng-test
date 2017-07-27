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

public class UserTest {
	JSONParser jsonParser = new JSONParser();
	Header authHeader;
	Header contentHeader = new Header("Content-Type","application/json; charset=utf-8");
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	Date date = new Date();
	
	
	@Before
	public void setup() {
		//get aut0token from curl request
		authHeader = new Header("Authorization", Auth0Login.getAuthToken());
		
		//For the sake of reading the URL is stored here would put in properties file
		RestAssured.baseURI = "https://api.qa.fitpay.ninja";
		RestAssured.useRelaxedHTTPSValidation();
		
	}
	
    @Test public void get_10_Users() {   	
    	/*
    	 * A basic test to endpoint https://qa.fitpay.com/users which returns a list
    	 * of all users on the site
    	 */

    			Response response = RestAssured
    					.given()
	    					.header(authHeader)
	    					.header(contentHeader)
	    				.when()
    						.get("/users?limit=10")
	    				.then()
	    					.statusCode(200)
	    					.extract()
	    					.response();
    			
    		String[] users = response.path("results");
    		
    	assertEquals("the number of users returned is 10", 10, users.length);
    	
    }
    
    @Test public void create_a_User() {   	
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
	    					.header(authHeader)
	    					.header(contentHeader)
	    					.body(jsonString)
	    				.when()
    						.post("/users")
	    				.then()
	    					.statusCode(200)
	    					.extract()
	    					.response();
    			
    		String usersId = response.path("results.id");
    		String timeCreated = response.path("results.createdTs");
    		String message = response.path("message");
    		
        assertEquals("the user was  created", "created new user", message);
        assertEquals("the time created matches the expected value", createdTime, timeCreated);
    	assertEquals("the userId matches the one we sent", newUserId, usersId);
    	
    }
    
    @Test public void update_a_User() {    	
    	/*
    	 * A basic test to put endpoint https://qa.fitpay.com/users 
    	 * to create a user
    	 */
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
	    					.header(authHeader)
	    					.header(contentHeader)
	    					.body(jsonString)
	    				.when()
    						.put("/users")
	    				.then()
	    					.statusCode(200)
	    					.extract()
	    					.response();
    			
    		String usersId = response.path("results.id");
    		String lastModified = response.path("results.lastModifiedTs");
    		String message = response.path("message");
    		
        assertEquals("the user was updated", "updated user", message);
        assertEquals("the last modified time was updated", lastModified, modifiedTime);
    	assertEquals("the userId matches the one we sent", newUserId, usersId);
    	
    }
    
    @Test public void update_a_User_Bad_UserId() {    	
    	/*
    	 * A basic test to put endpoint https://qa.fitpay.com/users 
    	 * update a a user with bad userId
    	 */
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
	    					.header(authHeader)
	    					.header(contentHeader)
	    					.body(jsonString)
	    					.filter(ResponseLoggingFilter.logResponseIfStatusCodeIs(200))
	    				.when()
    						.put("/users")
	    				.then()
	    					.statusCode(404)
	    					.extract()
	    					.response();
    			
    		String usersId = response.path("results.id");
    		String lastModified = response.path("results.lastModifiedTs");
    		String message = response.path("message");
    		
        assertEquals("the user was not found", "user not found", message);

    	
    }
    
    @Test public void create_a_User_missing_body() {   	
    	/*
    	 * A basic test to post endpoint https://qa.fitpay.com/users 
    	 * to create a user without a body
    	 */
   	
    			Response response = RestAssured
    					.given()
	    					.header(authHeader)
	    					.header(contentHeader)
	    					.filter(ResponseLoggingFilter.logResponseIfStatusCodeIs(200))
	    				.when()
    						.post("/users")
	    				.then()
	    					.statusCode(400)
	    					.extract()
	    					.response();
    			
    		String message = response.path("message");
    		
    	assertEquals("the user was not able to be created", "Bad Request error", message);
    	
    }
}
