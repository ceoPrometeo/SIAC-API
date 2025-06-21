package org.example.siecapi.controllers.asesores;

import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.example.siecapi.config.ApiResponse;
import org.example.siecapi.services.Token.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/asesor")
public class AsesorController {

    @Autowired
    private TokenService tokenService;

    @PostMapping()
    public ResponseEntity<ApiResponse> crearAsesor (@Valid @RequestBody AsesorDto asesorDto) {
        ApiResponse response = tokenService.crearAsesor(asesorDto).getBody();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
