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

import application.Main;
import application.controller.LoginController;
import application.model.Utente;

public class UtenteDriver {
	
	private Utente loggedUser = LoginController.loggedUser;
	private ArrayList<Utente> utenti  = new ArrayList<Utente>();
	private String url = Main.address;
	public UtenteDriver() {
		
	}
	
	public Utente requestLoginToServer(String email, String password){
		try {
			return runLogin(email, password);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	

	public Utente addNewEmployee(String nome, String cognome, String email, String password, String dataNascita, String ruolo){
		try {
			HttpClient httpclient = HttpClients.createDefault();
			HttpPost httppost = new HttpPost("http://37.100.241.91:5000/signup/newEmployee");
			httppost.setHeader("Authorization", loggedUser.getToken());
			
			List<NameValuePair> params = new ArrayList<NameValuePair>(2);
			params.add(new BasicNameValuePair("nome", nome));
			params.add(new BasicNameValuePair("cognome", cognome));
			params.add(new BasicNameValuePair("passwordTemporanea", password));
			params.add(new BasicNameValuePair("email", email));
			params.add(new BasicNameValuePair("dataNascita", dataNascita));
			params.add(new BasicNameValuePair("ruolo", ruolo));
			params.add(new BasicNameValuePair("idUtente", String.valueOf(loggedUser.getIdUtente())));
			params.add(new BasicNameValuePair("idRistorante", String.valueOf(loggedUser.getIdRistorante())));
			httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			String json = EntityUtils.toString(response.getEntity());
			JSONObject jsonObject = new JSONObject(json);
			loggedUser = new Utente(jsonObject.getInt("idUtente"), jsonObject.getString("nome"), jsonObject.getString("cognome"), jsonObject.getString("dataNascita"), jsonObject.getString("email"), jsonObject.getString("ruolo"), jsonObject.getBoolean("firstAccess"), jsonObject.getInt("aggiuntoDa"), jsonObject.getString("dataAggiunta"), jsonObject.getInt("idRistorante"), jsonObject.getString("token"), jsonObject.getString("tk_expiration_timestamp"));
			return loggedUser;
						
		}catch (Exception e) {
			System.out.print("Errore nella connessione");
		}
		return null;
	}
	
	public ArrayList<Utente> showEmplyees(){
        try {
            HttpClient httpclient = HttpClients.createDefault();
            HttpGet httpget = new HttpGet(url + "/user?id_ristorante=" + loggedUser.getIdRistorante());
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


        }catch (Exception e) {
            e.printStackTrace();
            System.out.print("Errore");
        }
        return null;
	}
	
	
	public boolean requestModifyAccountToServer(String nome, String email, String cognome) {
		try {
			HttpClient httpclient = HttpClients.createDefault();
			HttpPost httppost = new HttpPost(url + "/user/accountDesktop");
			httppost.setHeader("Authorization", loggedUser.getToken());
		
			List<NameValuePair> params = new ArrayList<NameValuePair>(2);
			params.add(new BasicNameValuePair("nome", nome));
			params.add(new BasicNameValuePair("cognome", cognome));
			params.add(new BasicNameValuePair("email", email));
			params.add(new BasicNameValuePair("idUtente", String.valueOf(loggedUser.getIdUtente())));
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
	

	public boolean requestSignUpToServer(String nome, String email, String password, String cognome, String dataNascita, String nomeAttivita){
		try {
			HttpClient httpclient = HttpClients.createDefault();
			HttpPost httppost = new HttpPost(url + "/signup-admin");
		
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
	
	public Utente requestUpgradeRoleToServer(String idUtente, String ruolo){
		try {
			HttpClient httpclient = HttpClients.createDefault();
			HttpPost httppost = new HttpPost(url + "/user/upgradeRole");
			httppost.setHeader("Authorization", loggedUser.getToken());
		
			List<NameValuePair> params = new ArrayList<NameValuePair>(2);
			params.add(new BasicNameValuePair("idUtente", idUtente));
			params.add(new BasicNameValuePair("ruolo", ruolo));
			httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			String json = EntityUtils.toString(response.getEntity());
			System.out.print(json);
			JSONObject jsonObject = new JSONObject(json);
			
			Utente u = new Utente(jsonObject.getInt("id_utente"), jsonObject.getString("nome"), jsonObject.getString("cognome"), jsonObject.getString("email"), jsonObject.getString("ruolo"));
			return u;
			
		}catch (Exception e) {
			System.out.print("Errore nella connessione");
		}
		return null;
	}
	
	public Utente requestDowngradeRoleToServer(String idUtente, String ruolo){
		try {
			HttpClient httpclient = HttpClients.createDefault();
			HttpPost httppost = new HttpPost(url + "/user/downgradeRole");
			httppost.setHeader("Authorization", loggedUser.getToken());
		
			List<NameValuePair> params = new ArrayList<NameValuePair>(2);
			params.add(new BasicNameValuePair("idUtente", idUtente));
			params.add(new BasicNameValuePair("ruolo", ruolo));
			params.add(new BasicNameValuePair("ruoloLogged", loggedUser.getRuolo()));
			httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			String json = EntityUtils.toString(response.getEntity());
			System.out.print(json);
			JSONObject jsonObject = new JSONObject(json);
			
			Utente u = new Utente(jsonObject.getInt("idUtente"), jsonObject.getString("nome"), jsonObject.getString("cognome"), jsonObject.getString("email"), jsonObject.getString("ruolo"));
			return u;
			
		}catch (Exception e) {
			System.out.print("Errore nella connessione");
		}
		return null;
	}
	
	public boolean requestFireUserToServer(String idUtente, String ruolo){
		try {
			HttpClient httpclient = HttpClients.createDefault();
			HttpPost httppost = new HttpPost(url + "/user/fire");
			httppost.setHeader("Authorization", loggedUser.getToken());
		
			List<NameValuePair> params = new ArrayList<NameValuePair>(2);
			params.add(new BasicNameValuePair("idUtente", idUtente));
			params.add(new BasicNameValuePair("ruolo", ruolo));
			params.add(new BasicNameValuePair("ruoloLogged", loggedUser.getRuolo()));
			httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			String json = EntityUtils.toString(response.getEntity());
			System.out.print(json);
			JSONObject jsonObject = new JSONObject(json);
			
			return true;
			
		}catch (Exception e) {
			System.out.print("Errore nella connessione");
		}
		return false;
	}
	
	
	public Utente runLogin(String email, String password) throws IOException {
		try {
			HttpClient httpclient = HttpClients.createDefault();
			HttpPost httppost = new HttpPost(url + "/login");
		
			List<NameValuePair> params = new ArrayList<NameValuePair>(2);
			params.add(new BasicNameValuePair("email", email));
			params.add(new BasicNameValuePair("password", password));
			httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			String json = EntityUtils.toString(response.getEntity());
			JSONObject jsonObject = new JSONObject(json);
			if(jsonObject.has("nome") && jsonObject.has("token")) {
				if(jsonObject.getString("ruolo").equals("admin") || jsonObject.getString("ruolo").equals("supervisore")){
					loggedUser = new Utente(jsonObject.getInt("idUtente"), jsonObject.getString("nome"), jsonObject.getString("cognome"), jsonObject.getString("dataNascita"), jsonObject.getString("email"), jsonObject.getString("ruolo"), jsonObject.getBoolean("firstAccess"), jsonObject.getInt("aggiuntoDa"), jsonObject.getString("dataAggiunta"), jsonObject.getInt("idRistorante"), jsonObject.getString("token"), jsonObject.getString("tk_expiration_timestamp"));
					return loggedUser;
				}
			}
		}catch (Exception e) {
			System.out.print("Errore nella connessione");
		}
		return null;
	}

	
}
