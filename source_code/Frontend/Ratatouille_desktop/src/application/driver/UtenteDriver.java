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

import application.controller.LoginController;
import application.model.Utente;

public class UtenteDriver {
	
	private Utente loggedUser = LoginController.loggedUser;
	
	public UtenteDriver() {
		
	}
	
	public Utente requestToServer(String email, String password){
		try {
			//run(email, password);
			return run("teka.freitas@example.com", "meister");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Utente addNewEmployee(String nome, String cognome, String email, String password, String dataNascita, String ruolo){
		try {
			return runNewEmployee(nome, cognome, email, password, dataNascita, ruolo);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Utente run(String email, String password) throws IOException {
		try {
			HttpClient httpclient = HttpClients.createDefault();
			HttpPost httppost = new HttpPost("http://localhost:8080/login");
		
			List<NameValuePair> params = new ArrayList<NameValuePair>(2);
			params.add(new BasicNameValuePair("email", email));
			params.add(new BasicNameValuePair("password", password));
			httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			String json = EntityUtils.toString(response.getEntity());
			JSONObject jsonObject = new JSONObject(json);
			if(jsonObject.has("nome") && jsonObject.has("token")) {
				loggedUser = new Utente(jsonObject.getInt("idUtente"), jsonObject.getString("nome"), jsonObject.getString("cognome"), jsonObject.getString("dataNascita"), jsonObject.getString("email"), jsonObject.getString("ruolo"), jsonObject.getBoolean("firstAccess"), jsonObject.getInt("aggiuntoDa"), jsonObject.getString("dataAggiunta"), jsonObject.getInt("idRistorante"), jsonObject.getString("token"), jsonObject.getString("tk_expiration_timestamp"));
				return loggedUser;
			}
		}catch (Exception e) {
			System.out.print("Errore nella connessione");
		}
		return null;
		
	}
	
	public Utente runNewEmployee(String nome, String cognome, String email, String password, String dataNascita, String ruolo) throws IOException {
		try {
			HttpClient httpclient = HttpClients.createDefault();
			HttpPost httppost = new HttpPost("http://localhost:8080/signup/newEmployee");
			
			List<NameValuePair> params = new ArrayList<NameValuePair>(2);
			params.add(new BasicNameValuePair("nome", nome));
			params.add(new BasicNameValuePair("cognome", cognome));
			params.add(new BasicNameValuePair("email", email));
			params.add(new BasicNameValuePair("passwordTemporanea", password));
			params.add(new BasicNameValuePair("dataNascita", dataNascita));
			params.add(new BasicNameValuePair("ruolo", ruolo));
			httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			String json = EntityUtils.toString(response.getEntity());
			JSONObject jsonObject = new JSONObject(json);
			if(jsonObject.has("nome") && jsonObject.has("token")) {
				loggedUser = new Utente(jsonObject.getInt("idUtente"), jsonObject.getString("nome"), jsonObject.getString("cognome"), jsonObject.getString("dataNascita"), jsonObject.getString("email"), jsonObject.getString("ruolo"), jsonObject.getBoolean("firstAccess"), jsonObject.getInt("aggiuntoDa"), jsonObject.getString("dataAggiunta"), jsonObject.getInt("idRistorante"), jsonObject.getString("token"), jsonObject.getString("tk_expiration_timestamp"));
				return loggedUser;
			}
		}catch (Exception e) {
			System.out.print("Errore nella connessione");
		}
		return null;
		
	}
	
	public Utente getLoggedUser() {
		return loggedUser;
	}
	
}
