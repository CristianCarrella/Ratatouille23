package application.driver;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class UtenteDriver {
	
	public UtenteDriver() {
		
	}
	
	public void requestToServer(String email, String password){
		try {
			//run(email, password);
			run("teka.freitas@example.com", "meister");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run(String email, String password) throws IOException {
		HttpClient httpclient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost("http://localhost:8080/login");
	
		// Request parameters and other properties.
		List<NameValuePair> params = new ArrayList<NameValuePair>(2);
		params.add(new BasicNameValuePair("email", email));
		params.add(new BasicNameValuePair("password", password));
		httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
	
		//Execute and get the response.
		HttpResponse response = httpclient.execute(httppost);
		HttpEntity entity = response.getEntity();
		String json = EntityUtils.toString(response.getEntity());
		System.out.print(json);
	}
	
}
