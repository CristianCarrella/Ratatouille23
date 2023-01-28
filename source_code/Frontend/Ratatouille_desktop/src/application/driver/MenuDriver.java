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
import javafx.scene.control.TextField;

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
			HttpGet httpget = new HttpGet("http://localhost:8080/menu/categoria/piatti?id_ristorante=" + idRistorante + "&&categoria=" + URLEncoder.encode(categoria,"UTF-8"));
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
					Piatto a = new Piatto(menuController, jsonObject.getInt("idElemento"), jsonObject.getInt("idRistorante"), jsonObject.getInt("idCategoria"), jsonObject.getString("nome"), jsonObject.getFloat("prezzo"), jsonObject.getString("descrizione"), jsonObject.getString("allergeni"), nomeSecondaLingua, descrizioneSecondaLingua, jsonObject.getInt("posizione"));
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
					CategoriaMenu a = new CategoriaMenu(menuController, jsonObject.getInt("idCategoria"), jsonObject.getInt("idRistorante"), jsonObject.getString("nome"), jsonObject.getInt("posizione"));
					categorieRistorante.add(a);
				}
			}
		}catch (JSONException e) {
			e.printStackTrace();
			System.out.print("Errore nel parsing del JSON");
		}
	}
	
	public ArrayList<CategoriaMenu> getCategorieRistoranteLoggedUserWihoutChangeUI(){
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
					CategoriaMenu a = new CategoriaMenu(jsonObject.getInt("idCategoria"), jsonObject.getInt("idRistorante"), jsonObject.getString("nome"), jsonObject.getInt("posizione"));
					categorie.add(a);
				}
			}
			return categorie;
		}catch (Exception e) {
			e.printStackTrace();
			System.out.print("Errore nel parsing del JSON");
		}
		return null;
	}
	
	public ArrayList<Piatto> getPiattiFromCategorieWithoutChangeUI(String categoria) throws Exception {
		ArrayList<Piatto> piatti = new ArrayList<Piatto>();
		try {
			HttpClient httpclient = HttpClients.createDefault();
			HttpGet httpget = new HttpGet("http://localhost:8080/menu/categoria/piatti?id_ristorante=" + loggedUser.getIdRistorante() + "&&categoria=" + URLEncoder.encode(categoria, "UTF-8"));
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
					Piatto piatto = new Piatto(jsonObject.getInt("idElemento"), jsonObject.getInt("idRistorante"), jsonObject.getInt("idCategoria"), jsonObject.getString("nome"), jsonObject.getFloat("prezzo"), jsonObject.getString("descrizione"), jsonObject.getString("allergeni"), nomeSecondaLingua, descrizioneSecondaLingua, jsonObject.getInt("posizione"));
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

	public ArrayList<Piatto> getAllPiattiOfMenu() throws Exception {
		ArrayList<Piatto> piatti = new ArrayList<>();
		try {
			HttpClient httpclient = HttpClients.createDefault();
			HttpGet httpget = new HttpGet("http://localhost:8080/menu?id_ristorante=" + loggedUser.getIdRistorante());
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
					Piatto a = new Piatto(jsonObject.getInt("idElemento"), jsonObject.getInt("idRistorante"), jsonObject.getInt("idCategoria"), jsonObject.getString("nome"), jsonObject.getFloat("prezzo"), jsonObject.getString("descrizione"), jsonObject.getString("allergeni"), nomeSecondaLingua, descrizioneSecondaLingua, jsonObject.getInt("posizione"));
					piatti.add(a);
				}
			}
			return piatti;
		}catch (JSONException e) {
			e.printStackTrace();
			System.out.print("Errore nel parsing del JSON");
		}
		return null;
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
					Piatto a = new Piatto(jsonObject.getInt("idElemento"), jsonObject.getInt("idRistorante"), jsonObject.getInt("idCategoria"), jsonObject.getString("nome"), jsonObject.getFloat("prezzo"), jsonObject.getString("descrizione"), jsonObject.getString("allergeni"), nomeSecondaLingua, descrizioneSecondaLingua, jsonObject.getInt("posizione"));
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

	public boolean requestUpdatePositionCategoria(Integer idCategoria, Integer posizione) {
		try {
			return runUpdatePositionCategoria(idCategoria, posizione);
		} catch (Exception e) {
			return false;
		}
		
	}

	private boolean runUpdatePositionCategoria(Integer idCategoria, Integer posizione) throws Exception{
		try {
			HttpClient httpclient = HttpClients.createDefault();
			HttpPost httppost = new HttpPost("http://localhost:8080/categoria-update-posizione");
			httppost.setHeader("Authorization", loggedUser.getToken());
			
			List<NameValuePair> params = new ArrayList<NameValuePair>(2);
			params.add(new BasicNameValuePair("id_categoria", idCategoria.toString()));
			params.add(new BasicNameValuePair("posizione", posizione.toString()));
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

		}
		return false;
	}

	public boolean requestUpdatePositionInMenuPiatto(Integer idPiatto, Integer posizione) {
		try {
			return runUpdatePositionInMenuPiatto(idPiatto, posizione);
		} catch (Exception e) {
			return false;
		}
	}

	private boolean runUpdatePositionInMenuPiatto(Integer idPiatto, Integer posizione) throws Exception{
		try {
			HttpClient httpclient = HttpClients.createDefault();
			HttpPost httppost = new HttpPost("http://localhost:8080/menu/piatto-update-posizione");
			httppost.setHeader("Authorization", loggedUser.getToken());
			
			List<NameValuePair> params = new ArrayList<NameValuePair>(2);
			params.add(new BasicNameValuePair("id_piatto", idPiatto.toString()));
			params.add(new BasicNameValuePair("posizione", posizione.toString()));
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

		}
		return false;
	}
	
	public boolean requestDeleteMenuSorting() {
		try {
			return runDeleteMenuSorting(loggedUser.getIdRistorante());
		} catch (Exception e) {
			return false;
		}
	}

	private boolean runDeleteMenuSorting(Integer idRistorante) throws Exception{
		try {
			HttpClient httpclient = HttpClients.createDefault();
			HttpPost httppost = new HttpPost("http://localhost:8080/menu/delete-ordine-menu-precedente");
			httppost.setHeader("Authorization", loggedUser.getToken());
			
			List<NameValuePair> params = new ArrayList<NameValuePair>(2);
			params.add(new BasicNameValuePair("id_ristorante", idRistorante.toString()));
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

		}
		return false;
	}

	public Piatto getPiattoInfoFromServer(Integer idPiatto) {
		try {
			return runPiattoInfo(idPiatto);
		} catch (Exception e) {
			return null;
		}
		
	}
	
	private Piatto runPiattoInfo(Integer idPiatto) throws Exception {
		try {
			HttpClient httpclient = HttpClients.createDefault();
			HttpGet httpget = new HttpGet("http://localhost:8080/menu/ristorante/piatto/" + idPiatto.toString());
			httpget.setHeader("Authorization", loggedUser.getToken());
		
			HttpResponse response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();
			String json = EntityUtils.toString(response.getEntity());
			
			JSONObject jsonObject = new JSONObject(json);
			String nomeSecondaLingua = "";
			String descrizioneSecondaLingua = "";
			
			if(!jsonObject.isNull("descrizioneSecondaLingua"))
				descrizioneSecondaLingua = jsonObject.getString("descrizioneSecondaLingua");
			
			if(!jsonObject.isNull("nomeSecondaLingua"))
				nomeSecondaLingua = jsonObject.getString("nomeSecondaLingua");
				
			if(jsonObject.has("idElemento") && jsonObject.has("idRistorante")) {
				return new Piatto(jsonObject.getInt("idElemento"), jsonObject.getInt("idRistorante"), jsonObject.getInt("idCategoria"), jsonObject.getString("nome"), jsonObject.getFloat("prezzo"), jsonObject.getString("descrizione"), jsonObject.getString("allergeni"), nomeSecondaLingua, descrizioneSecondaLingua, jsonObject.getInt("posizione"));
			}
		}catch (JSONException e) {
			e.printStackTrace();
			System.out.print("Errore nel parsing del JSON");

		}
		return null;
	}

	public boolean modificaPiatto(String nomePiatto, String descrizione, String costo, String allergeni, String nomeSecondaLingua, String descrizioneSecondaLingua, String categoriaScelta, Integer idPiatto) {
		try {
			return runUpdatePiatto(loggedUser.getIdRistorante(), nomePiatto, descrizione, costo, allergeni, nomeSecondaLingua, descrizioneSecondaLingua, categoriaScelta, idPiatto);
		} catch (Exception e) {
			return false;
		}
	}

	public boolean creaPiatto(String nomePiatto, String descrizione, String costo, String allergeni, String nomeSecondaLingua, String descrizioneSecondaLingua, String categoriaScelta) {
		try {
			return runCreaPiatto(loggedUser.getIdRistorante(), nomePiatto, descrizione, costo, allergeni, nomeSecondaLingua, descrizioneSecondaLingua, categoriaScelta);
		} catch (Exception e) {
			return false;
		}
	}
	
	private boolean runCreaPiatto(Integer idRistorante, String nomePiatto, String descrizione, String costo, String allergeni, String nomeSecondaLingua, String descrizioneSecondaLingua, String categoriaScelta) throws Exception {
		try {
			HttpClient httpclient = HttpClients.createDefault();
			HttpPost httppost = new HttpPost("http://localhost:8080/menu/newPlate");
			httppost.setHeader("Authorization", loggedUser.getToken());
			
			List<NameValuePair> params = new ArrayList<NameValuePair>(2);
			params.add(new BasicNameValuePair("idRistorante", idRistorante.toString()));
			params.add(new BasicNameValuePair("categoria", categoriaScelta));
			params.add(new BasicNameValuePair("nome", nomePiatto));
			params.add(new BasicNameValuePair("prezzo", costo));
			params.add(new BasicNameValuePair("descrizione", descrizione));
			params.add(new BasicNameValuePair("allergeni", allergeni));
			params.add(new BasicNameValuePair("nomeSecondaLingua", nomeSecondaLingua));
			params.add(new BasicNameValuePair("descrizioneSecondaLingua", descrizioneSecondaLingua));
			httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
			
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			String json = EntityUtils.toString(response.getEntity());
			System.out.println(json);
			if(json.equals("true"))
				return true;
			else
				return false;
		}catch (JSONException e) {
			e.printStackTrace();
			System.out.print("Errore nel parsing del JSON");

		}
		return false;
	}
	
	private boolean runUpdatePiatto(Integer idRistorante, String nomePiatto, String descrizione, String costo, String allergeni, String nomeSecondaLingua, String descrizioneSecondaLingua, String categoriaScelta, Integer idPiatto) throws Exception {
		try {
			HttpClient httpclient = HttpClients.createDefault();
			HttpPost httppost = new HttpPost("http://localhost:8080/menu/updatePlate/" + idPiatto.toString());
			httppost.setHeader("Authorization", loggedUser.getToken());
			
			List<NameValuePair> params = new ArrayList<NameValuePair>(2);
			params.add(new BasicNameValuePair("idRistorante", idRistorante.toString()));
			params.add(new BasicNameValuePair("categoria", categoriaScelta));
			params.add(new BasicNameValuePair("nome", nomePiatto));
			params.add(new BasicNameValuePair("prezzo", costo));
			params.add(new BasicNameValuePair("descrizione", descrizione));
			params.add(new BasicNameValuePair("allergeni", allergeni));
			params.add(new BasicNameValuePair("nomeSecondaLingua", nomeSecondaLingua));
			params.add(new BasicNameValuePair("descrizioneSecondaLingua", descrizioneSecondaLingua));
			httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
			
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			String json = EntityUtils.toString(response.getEntity());
			System.out.println(json);
			if(json.equals("true"))
				return true;
			else
				return false;
		}catch (JSONException e) {
			e.printStackTrace();
			System.out.print("Errore nel parsing del JSON");

		}
		return false;
	}

	public boolean creaCategoria(String nomeCategoriaNuova) {
		try {
			return runcreaCategoria(nomeCategoriaNuova, loggedUser.getIdRistorante());
		} catch (Exception e) {
			return false;
		}
	}
	
	private boolean runcreaCategoria(String nomeCategoriaNuova, Integer idRistorante) throws Exception{
		try {
			HttpClient httpclient = HttpClients.createDefault();
			HttpPost httppost = new HttpPost("http://localhost:8080/menu/newCategoria");
			httppost.setHeader("Authorization", loggedUser.getToken());
			
			List<NameValuePair> params = new ArrayList<NameValuePair>(2);
			params.add(new BasicNameValuePair("nomeNuovaCategoria", nomeCategoriaNuova));
			params.add(new BasicNameValuePair("idRistorante", idRistorante.toString()));
			httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
			
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			String json = EntityUtils.toString(response.getEntity());
			System.out.println(json);
			if(json.equals("true"))
				return true;
			else
				return false;
		}catch (JSONException e) {
			e.printStackTrace();
			System.out.print("Errore nel parsing del JSON");

		}
		return false;
	}

	public JSONObject autocompletamentoProdotto(String nomeProdotto, Integer resultIndex) {
		String nome = nomeProdotto, categoria = null, descrizione = null, allergeni = null;
		try {
			HttpClient httpclient = HttpClients.createDefault();
			HttpGet httpget = new HttpGet("https://it.openfoodfacts.org/cgi/search.pl?search_terms=" + nomeProdotto + "&&json=true");
			httpget.setHeader("Authorization", loggedUser.getToken());
			
			HttpResponse response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();
			String json = EntityUtils.toString(response.getEntity());
			JSONObject jsonObject1 = new JSONObject(json);
			JSONArray nestedJsonA = jsonObject1.getJSONArray("products");
			JSONObject jsonObject = nestedJsonA.getJSONObject(resultIndex);
			JSONObject result = new JSONObject();
			
			categoria = jsonObject.getString("categories");
			categoria = categoria.substring(0, 28);
			categoria = categoria.replaceAll("'", "");
			categoria = categoria.replaceAll("-", "");
			categoria = categoria.replaceAll(",", "");
			if (jsonObject.has("generic_name_it")) {
                nome = jsonObject.getString("generic_name_it");
                if (nome.equals(""))
                    nome = nomeProdotto;
                nome = nome.replaceAll("'", "");
            }
			
			if(jsonObject.has("quantity") && jsonObject.has("brands") && jsonObject.has("countries") && jsonObject.has("packaging")) {
                descrizione = "Quantità: " + jsonObject.getString("quantity") + "\nBrands: " + jsonObject.getString("brands") + "\nPaesi di produzione: " + jsonObject.getString("countries") + "\nConfezionamento: " + jsonObject.getString("packaging");
                descrizione = descrizione.replaceAll("'", " ");
            }else{
                descrizione = "Non trovata";
            }
			
			if(jsonObject.has("allergens")) {
				allergeni = jsonObject.getString("allergens");
			}
			
			result.put("categoria", categoria);
			result.put("nome", nome);
			result.put("descrizione", descrizione);
			result.put("allergeni", allergeni);
			
			return result;
			
		}catch (Exception e) {
			e.printStackTrace();
			System.out.print("Errore nel parsing del JSON");

		}
		return null;
	}

}
