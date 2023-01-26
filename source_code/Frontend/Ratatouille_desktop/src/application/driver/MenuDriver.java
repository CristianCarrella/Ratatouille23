package application.driver;

import java.net.URLEncoder;
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
	
	public ArrayList<CategoriaMenu> getCategorieRistoranteLoggedUserWihoutChangeUI() throws Exception {
		ArrayList<CategoriaMenu> categorie = new ArrayList<CategoriaMenu>();
		try {
			HttpClient httpclient = HttpClients.createDefault();
			HttpGet httpget = new HttpGet("http://localhost:8080/menu/categoria?id_ristorante=" + loggedUser.getIdRistorante());
			httpget.setHeader("Authorization", loggedUser.getToken());
			
			HttpResponse response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();
			String json = EntityUtils.toString(response.getEntity());
			JSONArray jsonArray = new JSONArray(json);
			for(int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				if(jsonObject.has("idCategoria") && jsonObject.has("idRistorante")) {
					CategoriaMenu a = new CategoriaMenu(jsonObject.getInt("idCategoria"), jsonObject.getInt("idRistorante"), jsonObject.getString("nome"));
					categorie.add(a);
				}
			}
			return categorie;
		}catch (JSONException e) {
			e.printStackTrace();
			System.out.print("Errore nel parsing del JSON");
		}
		return null;
	}
	
	public ArrayList<Piatto> getPiattiFromCategorieWithoutChangeUI(String categoria) throws Exception {
		ArrayList<Piatto> piatti = new ArrayList<Piatto>();
		try {
			HttpClient httpclient = HttpClients.createDefault();
			HttpGet httpget = new HttpGet("http://localhost:8080/menu/categoria/piatti?id_ristorante=" + loggedUser.getIdRistorante() + "&&categoria=" + categoria);
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
					Piatto piatto = new Piatto(jsonObject.getInt("idElemento"), jsonObject.getInt("idRistorante"), jsonObject.getInt("idCategoria"), jsonObject.getString("nome"), jsonObject.getFloat("prezzo"), jsonObject.getString("descrizione"), jsonObject.getString("allergeni"), nomeSecondaLingua, descrizioneSecondaLingua);
					piatti.add(piatto);
				}
			}
			return piatti;
		}catch (JSONException e) {
			e.printStackTrace();
			System.out.print("Errore nel parsing del JSON");
		}
		return null;
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
	
	public boolean requestDeletePiatto(MenuController menuController, Integer idPiatto) {
		try {
			return runDeletePiatto(menuController, idPiatto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	private boolean runDeletePiatto(MenuController menuController, Integer idPiatto) throws Exception {
		try {
			HttpClient httpclient = HttpClients.createDefault();
			HttpPost httppost = new HttpPost("http://localhost:8080/menu/deletePlate");
			httppost.setHeader("Authorization", loggedUser.getToken());
			
			List<NameValuePair> params = new ArrayList<NameValuePair>(2);
			params.add(new BasicNameValuePair("idPiatto", idPiatto.toString()));
			httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
			
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			String json = EntityUtils.toString(response.getEntity());
			System.out.print(json);
			if(json.equals("true"))
				return true;
			else
				return false;
		}catch (JSONException e) {
			e.printStackTrace();
			System.out.print("Errore nel parsing del JSON");
			return false;
		}
	}
	
	public ArrayList<Piatto> requestSearchPiatto(String nomePiatto) {
		try {
			return runCercaPiatto(nomePiatto);
		} catch (Exception e) {
			return null;
		}
	}
	
	private ArrayList<Piatto> runCercaPiatto(String nomePiatto) throws Exception{
		try {
			HttpClient httpclient = HttpClients.createDefault();
			String restUrl = "http://localhost:8080/menu/ristorante/piatto?id_ristorante="+String.valueOf(loggedUser.getIdRistorante())+"&&nomePiatto="+ URLEncoder.encode(nomePiatto, "UTF-8");
			HttpGet httpget = new HttpGet(restUrl);
			httpget.setHeader("Authorization", loggedUser.getToken());
			
			
			HttpResponse response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();
			String json = EntityUtils.toString(response.getEntity());
			JSONArray jsonArray = new JSONArray(json);
			ArrayList<Piatto> result = new ArrayList<Piatto>();
			for(int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				String nomeSecondaLingua = "";
				String descrizioneSecondaLingua = "";
				
				if(!jsonObject.isNull("descrizioneSecondaLingua"))
					descrizioneSecondaLingua = jsonObject.getString("descrizioneSecondaLingua");
				
				if(!jsonObject.isNull("nomeSecondaLingua"))
					nomeSecondaLingua = jsonObject.getString("nomeSecondaLingua");
					
				if(jsonObject.has("idElemento") && jsonObject.has("idRistorante")) {
					Piatto a = new Piatto(jsonObject.getInt("idElemento"), jsonObject.getInt("idRistorante"), jsonObject.getInt("idCategoria"), jsonObject.getString("nome"), jsonObject.getFloat("prezzo"), jsonObject.getString("descrizione"), jsonObject.getString("allergeni"), nomeSecondaLingua, descrizioneSecondaLingua);
					result.add(a);
				}
			}
			return result;
		}catch (JSONException e) {
			e.printStackTrace();
			System.out.print("Errore nel parsing del JSON");
			
		}
		return null;
	}
	
}
