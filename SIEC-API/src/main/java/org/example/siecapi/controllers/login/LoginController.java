package org.example.siecapi.controllers.login;

import org.example.siecapi.config.ApiResponse;
import org.example.siecapi.controllers.asesores.AsesorDto;
import org.example.siecapi.services.Token.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/login")
public class LoginController {
    @Autowired
    private TokenService tokenService;

    @PostMapping()
    public ResponseEntity<?> login(@RequestBody AsesorDto asesorDto) {

        ResponseEntity<?> response = tokenService.Login(asesorDto);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


}
