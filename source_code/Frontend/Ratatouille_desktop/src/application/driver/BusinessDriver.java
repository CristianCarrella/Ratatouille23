package application.driver;

import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import application.controller.LoginController;
import application.model.Business;
import application.model.Utente;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.WritableImage;


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
	
	public Image requestGetLogoToServer() {
		try {
			return runGetLogo();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Business runModifyBusiness(int idRistorante, String nome, String indirizzo, String numeroTelefono) throws IOException {
		try {
			HttpClient httpclient = HttpClients.createDefault();
			HttpPost httppost = new HttpPost("http://localhost:8080/business");
			httppost.setHeader("Authorization", loggedUser.getToken());
		
			List<NameValuePair> params = new ArrayList<NameValuePair>(2);
			params.add(new BasicNameValuePair("id_ristorante", String.valueOf(idRistorante)));
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
	

	@SuppressWarnings("deprecation")
	public Business runSetLogo(File image, String fileName) throws Exception{
		HttpClient httpclient = new DefaultHttpClient();
	    httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
//	    System.out.println("nomeImmaginePassata " + fileName);

	    HttpPost httppost = new HttpPost("http://localhost:8080/business/image");
	    httppost.setHeader("Authorization", loggedUser.getToken());

	    MultipartEntity mpEntity = new MultipartEntity();
	    ContentBody cbFile = new FileBody(image, "image/jpg");
	    mpEntity.addPart("image", cbFile);
	    
	    mpEntity.addPart("fileName",new StringBody(fileName, Charset.forName("utf-8")));


	    httppost.setEntity(mpEntity);
	    System.out.println("executing request " + httppost.getRequestLine());
	    HttpResponse response = httpclient.execute(httppost);
	    HttpEntity resEntity = response.getEntity();
	    
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
	
	public Image runGetLogo() throws Exception {
        try {
            HttpClient httpclient = HttpClients.createDefault();
            HttpGet httpget = new HttpGet("http://localhost:8080/business/getImage");
            httpget.setHeader("Authorization", loggedUser.getToken());

            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            String json = EntityUtils.toString(response.getEntity());
            System.out.println("jsonerrormannagg" + json);
            JSONObject jsonObject = new JSONObject(json);
            
            String encodedImage = jsonObject.getString("image");
            byte[] imageBytes = Base64.getDecoder().decode(encodedImage);
            Image image = new Image(new ByteArrayInputStream(imageBytes));
            System.out.println(image);
            return image;
            
            
//			Image myImage = Toolkit.getDefaultToolkit().createImage();
//			Image myImage = Toolkit.getDefaultToolkit().createImage(output.toByteArray());

        }catch (JSONException e) {
            e.printStackTrace();
            System.out.print("Errore nel parsing del JSON");
        }
        return null;
    }
}
