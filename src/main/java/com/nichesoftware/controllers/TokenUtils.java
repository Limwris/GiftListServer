package com.nichesoftware.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nichesoftware.exceptions.NotAuthorizedException;
import com.nichesoftware.model.User;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Created by n_che on 28/04/2016.
 */
public final class TokenUtils {
    private static final String SEPARATOR = ".";
    private static final String ESCAPED_SEPARATOR = "\\.";
    private static final String HMAC_KEY = "";
    private static final String HMAC_ALGORITHM = "HmacSHA1";

    /**
     * Constructeur privé
     */
    private TokenUtils() {
        // Nothing
    }

    public static User getUserFromToken(String token) throws NotAuthorizedException {
        final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        final String[] parts = token.split(ESCAPED_SEPARATOR);
        User user = null;
        if (parts.length == 2 && parts[0].length() > 0 && parts[1].length() > 0) {
            try {
                final byte[] userBytes = Base64.decodeBase64(parts[0]);
                final byte[] hash = Base64.decodeBase64(parts[1]);

                boolean validHash = Arrays.equals(createHmac(userBytes), hash);
                if (validHash) {
                    user = gson.fromJson(new String(userBytes), User.class);
                    // If expire token date implemented
//                    if (new Date().getTime() < user.getExpires()) {
//                        return user;
//                    }
                }
            } catch (IllegalArgumentException e) {
                throw new NotAuthorizedException("Impossible de retrouver le user à l'origine de la requête.");
            }
        }

        if (user == null) {
            throw new NotAuthorizedException("Le token n'est pas valide.");
        }
        return user;
    }

    public static String generateToken(User user) throws NotAuthorizedException {
        final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        byte[] userBytes = gson.toJson(user).getBytes();
        byte[] hash = createHmac(userBytes);
        final StringBuilder sb = new StringBuilder(170);
        sb.append(Base64.encodeBase64String(userBytes));
        sb.append(SEPARATOR);
        sb.append(Base64.encodeBase64String(hash));
        return sb.toString();
    }

    private static byte[] createHmac(byte[] message) throws NotAuthorizedException {
        SecretKeySpec keySpec = new SecretKeySpec(HMAC_KEY.getBytes(), HMAC_ALGORITHM);

        Mac mac;
        byte[] result;
        try {
            mac = Mac.getInstance("HmacSHA1");
            mac.init(keySpec);
            result = mac.doFinal(message);
        } catch (NoSuchAlgorithmException e) {
            throw new NotAuthorizedException("Impossible de générer le HMAC du token.");
        } catch (InvalidKeyException e) {
            throw new NotAuthorizedException("Impossible de générer le HMAC du token.");
        }
        return result;
    }
}
