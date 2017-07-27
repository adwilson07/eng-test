import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.codec.binary.Base64;

/*
 * This Java source file was generated by the Gradle 'init' task.
 */
public class Library {
    public String someLibraryMethod() {
    	String AuthToken
        String stringUrl = "https://qualysapi.qualys.eu/api/2.0/fo/report/?action=list";
        URL url;
    	try {
    	url = new URL(stringUrl);

        URLConnection uc = url.openConnection();

        uc.setRequestProperty("X-Requested-With", "Curl");

        String userpass = "username" + ":" + "password";
        String basicAuth = "Basic " + new String(new Base64().encode(userpass.getBytes()));
        uc.setRequestProperty("Authorization", basicAuth);

        InputStreamReader inputStreamReader = new InputStreamReader(uc.getInputStream());
        // read this input
        
    	} catch (MalformedURLException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    	
        return AuthToken;
    }
}