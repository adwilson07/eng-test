import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.apache.commons.codec.binary.Base64;

/*
 * This Java source file was generated by the Gradle 'init' task.
 */
public class Auth0Login {
    public String getAuthToken() {
    	 String AuthToken = null;
    	 
    	try {
        String stringUrl = "https://auth.qa.fitpay.ninja/oauth/token?grant_type=client_credentials";
        URL url;
    	url = new URL(stringUrl);

        URLConnection uc = url.openConnection();

        uc.setRequestProperty("X-Requested-With", "Curl");
   
        //would replaces these with user creditals from prop file did not put them in for security reasons
        String userpass = "username" + ":" + "password";
        String basicAuth = "Basic " + new String(new Base64().encode(userpass.getBytes()));
        uc.setRequestProperty("Authorization", basicAuth);

        InputStreamReader inputStreamReader = new InputStreamReader(uc.getInputStream());
        // read this input
        
        String Token = IOUtils.toString(inputStreamReader);
        AuthToken = "Bearer " + Token;
    	} catch (Exception e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    	
        return AuthToken;
    }
}



/*
	def nullTrustManager = [
	checkClientTrusted: { chain, authType ->  },
	checkServerTrusted: { chain, authType ->  },
	getAcceptedIssuers: { null }
]
def nullHostnameVerifier = [
	verify: { hostname, session -> true }
]
SSLContext sc = SSLContext.getInstance("SSL")

sc.init(null, [
nullTrustManager as X509TrustManager] as TrustManager[], null)
HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory())
HttpsURLConnection.setDefaultHostnameVerifier(nullHostnameVerifier as HostnameVerifier);

*/