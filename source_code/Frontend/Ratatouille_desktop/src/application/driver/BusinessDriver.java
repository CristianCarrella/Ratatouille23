package application.driver;

import java.io.IOException;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
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
			return runBusiness(loggedUser.getId_ristorante());
		} catch (Exception e) {
			e.printStackTrace();
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
