package com.example.board.security;

import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class PasswordEncoder {
    public String encode(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes());

            byte byteData[] = md.digest();

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }

            return sb.toString();
        }catch(NoSuchAlgorithmException e){
            throw new RuntimeException(e);
        }
    }

    public boolean matches(String rawPassword, String encodePassword) {
        return encode(rawPassword).equals(encodePassword);
    }
}
