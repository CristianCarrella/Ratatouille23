package application.driver;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import application.controller.LoginController;
import application.model.Utente;

public class UtenteDriver {
	
	private Utente loggedUser = LoginController.loggedUser;
	private ArrayList<Utente> utenti  = new ArrayList<Utente>();
	
	public UtenteDriver() {
		
	}
	
	public Utente requestLoginToServer(String email, String password){
		try {
			//run(email, password);
			return runLogin("teka.freitas@example.com", "meister");
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
	
	public ArrayList<Utente> showEmplyees(){
		try {
			return runShowEmplyees(loggedUser.getIdRistorante());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public boolean requestModifyAccountToServer(String nome, String email, String cognome) {
		try {
			return runModificaAccount(nome, email, cognome);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	

	public boolean requestSignUpToServer(String nome, String email, String password, String cognome, String dataNascita, String nomeAttivita){
		try {
			return runSignUp(nome, email, password, cognome, dataNascita, nomeAttivita);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	private ArrayList<Utente> runShowEmplyees(int idRistorante) throws Exception {
        try {
            HttpClient httpclient = HttpClients.createDefault();
            HttpGet httpget = new HttpGet("http://localhost:8080/user?id_ristorante=" + idRistorante);
            httpget.setHeader("Authorization", loggedUser.getToken());

            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            String json = EntityUtils.toString(response.getEntity());
            JSONArray jsonArray = new JSONArray(json);
            for(int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Utente u = new Utente(jsonObject.getInt("idUtente"), jsonObject.getString("nome"), jsonObject.getString("cognome"), jsonObject.getString("email"), jsonObject.getString("ruolo"));
                utenti.add(u);
            }
            return utenti;


        }catch (JSONException e) {
            e.printStackTrace();
            System.out.print("Errore nel parsing del JSON");
        }
        return null;
    }
	
	public Utente runLogin(String email, String password) throws IOException {
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
	
	public boolean runSignUp(String nome, String email, String password, String cognome, String dataNascita, String nomeAttivita) throws IOException {
		try {
			HttpClient httpclient = HttpClients.createDefault();
			HttpPost httppost = new HttpPost("http://localhost:8080/signup-admin");
		
			List<NameValuePair> params = new ArrayList<NameValuePair>(2);
			params.add(new BasicNameValuePair("nome", nome));
			params.add(new BasicNameValuePair("cognome", cognome));
			params.add(new BasicNameValuePair("email", email));
			params.add(new BasicNameValuePair("password", password));
			params.add(new BasicNameValuePair("dataNascita", dataNascita));
			params.add(new BasicNameValuePair("nomeAttivita", nomeAttivita));
			httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			String json = EntityUtils.toString(response.getEntity());
			System.out.print(json);
			JSONObject jsonObject = new JSONObject(json);
			
			if(jsonObject.has("nome") && jsonObject.has("email")) {
				return true;
			}
		}catch (Exception e) {
			System.out.print("Errore nella connessione");
			return false;
		}
		return false;
	}
	
	public boolean runModificaAccount(String nome, String email, String cognome) throws IOException {
		try {
			HttpClient httpclient = HttpClients.createDefault();
			HttpPost httppost = new HttpPost("http://localhost:8080/user/accountDesktop");
			httppost.setHeader("Autorization", loggedUser.getToken());
		
			List<NameValuePair> params = new ArrayList<NameValuePair>(2);
			params.add(new BasicNameValuePair("nome", nome));
			params.add(new BasicNameValuePair("cognome", cognome));
			params.add(new BasicNameValuePair("email", email));
			httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			String json = EntityUtils.toString(response.getEntity());
			System.out.print(json);
			JSONObject jsonObject = new JSONObject(json);
			
			if(jsonObject.has("nome") && jsonObject.has("email")) {
				return true;
			}
		}catch (Exception e) {
			System.out.print("Errore nella connessione");
			return false;
		}
		return false;
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
