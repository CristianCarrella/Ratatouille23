package com.ingsw.ratatouille.security;


import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;

import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestClientException;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.ingsw.DAOimplements.UserDAOimp;
import com.ingsw.ratatouille.LoggedUser;
import com.ingsw.ratatouille.User;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class CustomInterceptor implements HandlerInterceptor {
	UserDAOimp userDao;
	ArrayList<LoggedUser> loggedUsers = new ArrayList<LoggedUser>();
	
	@Autowired
	CustomInterceptor(UserDAOimp userDao){
		this.userDao = userDao;
	}
	
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    	
    	if(request.getHeader("Authorization") == null) {
    		
    		if(!(request.getRequestURI().toString().equals("/login") || request.getRequestURI().toString().equals("/verify"))) {
    			System.out.print(request.getRequestURI().toString());
    			response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    			throw new RestClientException("Token non presente");
    		} else {
    			if(request.getRequestURI().toString().equals("/login")) {
	    			String token = generateToken();
	    			ZoneId z = ZoneId.of("Europe/Paris");
	    			ZonedDateTime zdt = ZonedDateTime.now(z);
	    			ZonedDateTime later = zdt.plusMinutes(15); 
	    			String expirationTime = later.toString();
	    			LoggedUser u = userDao.login(request.getParameter("email"), request.getParameter("password"), token, expirationTime);
	    			u.setToken(token);
	    			u.setTk_expiration_timestamp(expirationTime);
	    		    HttpSession session = request.getSession();
	    		    session.setAttribute("attributeToPass", u);
	    			loggedUsers.add(u);
    			}
    		}
    	} else {
    		if(isValidToken(request.getHeader("Authorization"))) {
    			System.out.print("Token valido");
    			if(request.getRequestURI().toString().equals("/logout")) {
    				LoggedUser u = removeLoggedUser(Integer.valueOf(request.getParameter("idUtente")));
    			}
    		}
    	}
        return true;
        
    }
    

	private boolean isValidToken(String token) {
		for(LoggedUser u : loggedUsers) {
			if(u.getToken().equals(token)) {
    			ZoneId z = ZoneId.of("Europe/Paris");
    			ZonedDateTime zdt = ZonedDateTime.now(z);
    			ZonedDateTime tk_timestamp = ZonedDateTime.parse(u.getTk_expiration_timestamp());
    			if(tk_timestamp.compareTo(zdt) < 0){
	    			ZonedDateTime later = zdt.plusMinutes(15); 
	    			u.setTk_expiration_timestamp(later.toString());
					return true;
    			}
			}
		}
		return false;
	}

	@Override
    public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3) throws Exception {
        // TODO Auto-generated method stub
    }

    @Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // TODO Auto-generated method stub
    }
    
    
    public String generateToken() {
    	SecureRandom secureRandom = new SecureRandom(); //threadsafe
    	Base64.Encoder base64Encoder = Base64.getUrlEncoder(); //threadsafe
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        String token = base64Encoder.encodeToString(randomBytes);
    	for (LoggedUser u : loggedUsers) {
    		if(u.getToken().equals(token)) {
    			generateToken();
    		}
    	}
    	return token;
    }
    
    
    public LoggedUser removeLoggedUser(Integer idUtente) {
    	for(LoggedUser u : loggedUsers) {
    		if(u.getIdUtente() == idUtente) {
    			loggedUsers.remove(u);
    			return u;
    		}
    	}
    	return null;
    }
    

} 

