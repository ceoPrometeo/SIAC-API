package org.example.siecapi.controllers.login;

import org.example.siecapi.controllers.User.dto.LoginDto;
import org.example.siecapi.models.usuarios.Usuarios;
import org.example.siecapi.services.Token.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login")
public class LoginController {
    @Autowired
    private TokenService tokenService;

    @PostMapping()
    public ResponseEntity<?> login(@RequestBody LoginDto usuarios) {
        ResponseEntity<?> response = tokenService.login(usuarios);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


}
