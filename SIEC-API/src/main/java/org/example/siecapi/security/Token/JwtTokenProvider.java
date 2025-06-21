package org.example.siecapi.security.Token;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.example.siecapi.models.usuarios.Usuarios;
import org.example.siecapi.models.usuarios.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Autowired
    private UsuariosRepository userRepository;

    @Value("${jwt.secret}")
    private String secretKeyBase64;

    private SecretKey secretKey;

    @PostConstruct
    public void init() {
        byte[] decodedKey = Base64.getDecoder().decode(secretKeyBase64);
        secretKey = Keys.hmacShaKeyFor(decodedKey);
    }

    public String generateToken(Authentication authentication) {
        String rol = authentication.getAuthorities().stream()
                .map(grantedAuthority -> grantedAuthority.getAuthority())
                .findFirst()
                .orElse("CLIENTE");

        String username = authentication.getName();
        Usuarios usuario = userRepository.findByCorreo(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado en la base de datos"));

        Date tiempoActual = new Date();
        Date expiracionToken = new Date(tiempoActual.getTime() + SecurityConstants.JWT_EXPIRATION_TOKEN);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(tiempoActual)
                .setExpiration(expiracionToken)
                .claim("rol", rol)
                .claim("id", usuario.getId())
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }


    public String generateTokenFromEmail(String correo) {
        Usuarios usuario = userRepository.findByCorreo(correo)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Date now = new Date();
        Date expiry = new Date(now.getTime() + 15 * 60 * 10000); // 15 minutos

        return Jwts.builder()
                .setSubject(correo)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .claim("rol", usuario.getRol()) // Asegúrate de tener getRol() en UsersEntity
                .claim("id", usuario.getId())
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }

    public String extractRoleFromJwt(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("rol", String.class);
    }

    public String obtenerUsernameDeJwt(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public Long extractUserIdFromJwt(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("user_id", Long.class);
    }

    public boolean validarToken(String token) {
        try {
            if (token == null || token.trim().isEmpty()) {
                System.out.println("Error: Token vacío o nulo");
                return false;
            }
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            System.out.println("Error: Token expirado");
        } catch (MalformedJwtException e) {
            System.out.println("Error: Token mal formado");
        } catch (UnsupportedJwtException e) {
            System.out.println("Error: Token no soportado");
        } catch (Exception e) {
            System.out.println("Error: No se pudo validar el token");
        }
        return false;
    }
}
