package com.ingsw.DAOimplements;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.web.multipart.MultipartFile;

import com.ingsw.DAOinterface.BusinessDAOint;
import com.ingsw.ratatouille.Business;
import com.ingsw.ratatouille.DatabaseConnection;
import com.ingsw.ratatouille.LoggedUser;
import com.ingsw.ratatouille.User;

public class BusinessDAOimp implements BusinessDAOint {
	DatabaseConnection db;
	
	public BusinessDAOimp(DatabaseConnection db) {
		this.db = db;
	}
	
	public Business getBusinessFromBusinessId(Integer idRistorante) {
		String query = "SELECT * FROM ristorante WHERE id_ristorante = " + idRistorante;
		ResultSet rs = null;
		Business b;
		try {
			rs = db.getStatement().executeQuery(query);	
			while(rs.next()) {
				b = new Business(rs.getInt("id_ristorante"), rs.getString("nome"), rs.getString("telefono"), rs.getString("indirizzo"), rs.getString("nome_immagine"), rs.getInt("id_proprietario"));
				return b;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;		
	}

	@Override
	public Business modifyBusinessInfo(Integer idRistorante, String nome, String indirizzo, String telefono) {
		String query = "UPDATE ristorante SET nome = '" + nome + "', telefono = '" + telefono + "', indirizzo = '" + indirizzo + "' WHERE id_ristorante = " + idRistorante;
		try {
			db.getStatement().executeUpdate(query);
			return getBusinessFromBusinessId(idRistorante);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public void saveBusinessImage(User loggedUser, MultipartFile image, String fileName) throws IOException {
		String repository = "C:\\Users\\username\\Desktop\\logos\\" + loggedUser.getIdUtente();
		
		Path uploadPath = Paths.get(repository);
        
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
         
        try (InputStream inputStream = image.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {        
            throw new IOException("Could not save image file: " + fileName, ioe);
        }
		
		String query = "UPDATE ristorante SET logo = bytea('C:\\Users\\username\\Desktop\\logos\\') WHERE id_ristorante = " + loggedUser.getIdRistorante();
		try {
			db.getStatement().executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


}
