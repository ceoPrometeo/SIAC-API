package org.example.siecapi.security.Token;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

public class SecurityConstants {
    public static final long JWT_EXPIRATION_TOKEN =86400000;
    public static final SecretKey JWT_FIRMA = Keys.secretKeyFor(SignatureAlgorithm.HS512);;

}
