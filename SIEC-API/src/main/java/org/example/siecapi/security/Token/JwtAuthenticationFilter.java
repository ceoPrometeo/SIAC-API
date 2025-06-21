package org.example.siecapi.security.Token;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

// Esta clase validará el token JWT y establecerá la autenticación en el contexto de seguridad
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private CustomerUserDetailsService customerUserDetailsService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;


    private String obtenerTokenDeSolicitudes(HttpServletRequest request) {
        String bearertoken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearertoken) && bearertoken.startsWith("Bearer ")) {
            return bearertoken.substring(7);
        }
        return null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

          //Rutas públicas que NO requieren autenticación

//        String requestURI = request.getRequestURI();
//        if (requestURI.startsWith("/api/auth/RUTA QUE SE DESEE ") ) {
//            filterChain.doFilter(request, response);
//            return;
//        }


        // Obtener el token de la solicitud
        String token = obtenerTokenDeSolicitudes(request);
        System.out.println(" Token recibido: " + (token != null ? token : "No se recibió token"));

        //  Validar el token
        if (StringUtils.hasText(token) && jwtTokenProvider.validarToken(token)) {
            String username = jwtTokenProvider.obtenerUsernameDeJwt(token);
            UserDetails userDetails = customerUserDetailsService.loadUserByUsername(username);
            List<String> userRoles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

            System.out.println(" Usuario autenticado: " + username);
            System.out.println(" Roles del usuario: " + userRoles);

            //  Verifica que el usuario tenga un rol válido antes de autenticarlo
            if (!userRoles.isEmpty()) {
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                System.out.println(" Autenticación establecida correctamente.");
            } else {
                System.out.println(" Usuario sin roles asignados. No se autentica.");
            }
        } else {
            System.out.println("️ Token inválido o no presente. Se deja la solicitud sin autenticación.");
        }

        filterChain.doFilter(request, response);
    }
}