package application.driver;

import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import application.controller.LoginController;
import application.controller.MenuController;
import application.model.CategoriaMenu;
import application.model.Piatto;
import application.model.Utente;

public class MenuDriver {
	private Utente loggedUser = LoginController.loggedUser;
	private ArrayList<CategoriaMenu> categorieRistorante = new ArrayList<CategoriaMenu>();
	public MenuDriver() { }
	
	public void requestMenuFromServer(MenuController menuController) {
		try {
			runGetCategorie(loggedUser.getIdRistorante(), menuController);
			for(CategoriaMenu categoriaMenu : categorieRistorante)
				runGetPiattiFromCategorie(loggedUser.getIdRistorante(), menuController, categoriaMenu.getNome());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void requestGetPiattiFromServer(MenuController menuController, String categoria) {
		try {
			runGetPiattiFromCategorie(loggedUser.getIdRistorante(), menuController, categoria);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void runGetPiattiFromCategorie(Integer idRistorante, MenuController menuController, String categoria) throws Exception {
		try {
			HttpClient httpclient = HttpClients.createDefault();
			HttpGet httpget = new HttpGet("http://localhost:8080/menu/categoria/piatti?id_ristorante=" + idRistorante + "&&categoria=" + categoria);
			httpget.setHeader("Authorization", loggedUser.getToken());
			
			HttpResponse response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();
			String json = EntityUtils.toString(response.getEntity());
			JSONArray jsonArray = new JSONArray(json);
			for(int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				String nomeSecondaLingua = "";
				String descrizioneSecondaLingua = "";
				
				if(!jsonObject.isNull("descrizioneSecondaLingua"))
					descrizioneSecondaLingua = jsonObject.getString("descrizioneSecondaLingua");
				
				if(!jsonObject.isNull("nomeSecondaLingua"))
					nomeSecondaLingua = jsonObject.getString("nomeSecondaLingua");
					
				if(jsonObject.has("idElemento") && jsonObject.has("idRistorante")) {
					Piatto a = new Piatto(menuController, jsonObject.getInt("idElemento"), jsonObject.getInt("idRistorante"), jsonObject.getInt("idCategoria"), jsonObject.getString("nome"), jsonObject.getFloat("prezzo"), jsonObject.getString("descrizione"), jsonObject.getString("allergeni"), nomeSecondaLingua, descrizioneSecondaLingua);
					System.out.print(a.getIdElemento());
				}
			}
		}catch (JSONException e) {
			e.printStackTrace();
			System.out.print("Errore nel parsing del JSON");
		}
	}

	private void runGetCategorie(Integer idRistorante, MenuController menuController) throws Exception {
		try {
			HttpClient httpclient = HttpClients.createDefault();
			HttpGet httpget = new HttpGet("http://localhost:8080/menu/categoria?id_ristorante=" + idRistorante);
			httpget.setHeader("Authorization", loggedUser.getToken());
			
			HttpResponse response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();
			String json = EntityUtils.toString(response.getEntity());
			JSONArray jsonArray = new JSONArray(json);
			for(int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				if(jsonObject.has("idCategoria") && jsonObject.has("idRistorante")) {
					CategoriaMenu a = new CategoriaMenu(menuController, jsonObject.getInt("idCategoria"), jsonObject.getInt("idRistorante"), jsonObject.getString("nome"));
					categorieRistorante.add(a);
				}
			}
		}catch (JSONException e) {
			e.printStackTrace();
			System.out.print("Errore nel parsing del JSON");
		}
	}

	private void runGetMenu(Integer idRistorante, MenuController menuController) throws Exception {
		try {
			HttpClient httpclient = HttpClients.createDefault();
			HttpGet httpget = new HttpGet("http://localhost:8080/menu?id_ristorante=" + idRistorante);
			httpget.setHeader("Authorization", loggedUser.getToken());
			
			HttpResponse response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();
			String json = EntityUtils.toString(response.getEntity());
			JSONArray jsonArray = new JSONArray(json);
			for(int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				String nomeSecondaLingua = "";
				String descrizioneSecondaLingua = "";
				
				if(!jsonObject.isNull("descrizioneSecondaLingua"))
					descrizioneSecondaLingua = jsonObject.getString("descrizioneSecondaLingua");
				
				if(!jsonObject.isNull("nomeSecondaLingua"))
					nomeSecondaLingua = jsonObject.getString("nomeSecondaLingua");
					
				if(jsonObject.has("idElemento") && jsonObject.has("idRistorante")) {
					Piatto a = new Piatto(menuController, jsonObject.getInt("idElemento"), jsonObject.getInt("idRistorante"), jsonObject.getInt("idCategoria"), jsonObject.getString("nome"), jsonObject.getFloat("prezzo"), jsonObject.getString("descrizione"), jsonObject.getString("allergeni"), nomeSecondaLingua, descrizioneSecondaLingua);
					System.out.print(a.getIdElemento());
				}
			}
		}catch (JSONException e) {
			e.printStackTrace();
			System.out.print("Errore nel parsing del JSON");
		}
	}
	
	
}
