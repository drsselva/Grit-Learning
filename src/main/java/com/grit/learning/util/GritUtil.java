package com.grit.learning.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grit.learning.model.Token;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

public class GritUtil {
	
	private static final SecureRandom secureRandom = new SecureRandom(); //threadsafe
	private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();
	    
	public static boolean validateToken(Token token){

        String [] times = token.toString().split("-");
        String time = times[0];
        Date date = new Date(time);
        System.out.println(date);
        return true;
    }
    
    public static String objectAsJsonString(final Object obj) throws Exception {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception ex) {
			StringWriter stack = new StringWriter();
			ex.printStackTrace(new PrintWriter(stack));
			throw new Exception(ex.getMessage());
		}
	}
    
    public static String generateToken(){
        StringBuilder token = new StringBuilder();
        long currentTimeInMilisecond = Instant.now().toEpochMilli();
        return token.append(currentTimeInMilisecond).append("-")
               .append(UUID.randomUUID().toString()).toString();
    }
}
