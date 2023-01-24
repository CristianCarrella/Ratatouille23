package application.driver;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import application.controller.LoginController;
import application.model.Business;
import application.model.Utente;

public class BusinessDriver {
	public static Business business;

private Utente loggedUser = LoginController.loggedUser;
	
	public BusinessDriver() {
		
	}
	
	public Business requestBusinessToServer(){
		try {
			return runBusiness(loggedUser.getIdRistorante());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Business requestModifyBusinessToServer(String nome, String indirizzo, String numeroTelefono){
		try {
			return runModifyBusiness(loggedUser.getIdRistorante(), nome, indirizzo, numeroTelefono);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Business runModifyBusiness(int idRistorante, String nome, String indirizzo, String numeroTelefono) throws IOException {
		try {
			HttpClient httpclient = HttpClients.createDefault();
			HttpPost httppost = new HttpPost("http://localhost:8080/business");
			httppost.setHeader("Autentication", loggedUser.getToken());
		
			List<NameValuePair> params = new ArrayList<NameValuePair>(2);
			params.add(new BasicNameValuePair("idRistorante", String.valueOf(idRistorante)));
			params.add(new BasicNameValuePair("nome", nome));
			params.add(new BasicNameValuePair("indirizzo", indirizzo));
			params.add(new BasicNameValuePair("numeroTelefono", numeroTelefono));
			httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			String json = EntityUtils.toString(response.getEntity());
			JSONObject jsonObject = new JSONObject(json);
			business = new Business(jsonObject.getInt("idRistorante"), jsonObject.getString("nome"), jsonObject.getString("numeroTelefono"), jsonObject.getString("indirizzo"), jsonObject.getString("nomeImmagine"), jsonObject.getInt("idProprietario"));
            return business;
		}catch (Exception e) {
			System.out.print("Errore nella connessione");
		}
		return null;
	}
	
	private Business runBusiness(int idRistorante) throws Exception {
        try {
            HttpClient httpclient = HttpClients.createDefault();
            HttpGet httpget = new HttpGet("http://localhost:8080/business/nomeAttivita?id_ristorante=" + idRistorante);
            httpget.setHeader("Authorization", loggedUser.getToken());

            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            String json = EntityUtils.toString(response.getEntity());
            JSONObject jsonObject = new JSONObject(json);
            business = new Business(jsonObject.getInt("idRistorante"), jsonObject.getString("nome"), jsonObject.getString("numeroTelefono"), jsonObject.getString("indirizzo"), jsonObject.getString("nomeImmagine"), jsonObject.getInt("idProprietario"));
            return business;
        }catch (JSONException e) {
            e.printStackTrace();
            System.out.print("Errore nel parsing del JSON");
        }
        return null;
    }
}
