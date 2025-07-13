package org.example.siecapi.controllers.User;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.validation.Valid;
import org.example.siecapi.config.ApiResponse;
import org.example.siecapi.controllers.Cliente.dto.ClienteDto;
import org.example.siecapi.controllers.User.dto.AsesorDto;
import org.example.siecapi.controllers.User.dto.SuperUserDto;
import org.example.siecapi.services.Token.TokenService;
import org.example.siecapi.services.Usuario.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuario/")
public class UsuarioController {


    @Autowired
    private TokenService tokenService;

    @PostMapping("crearAsesor")
    public ResponseEntity<ApiResponse> crearCliente(@Valid @RequestBody AsesorDto asesorDto) {
        ApiResponse response = tokenService.crearAsesor(asesorDto).getBody();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("crearSuperUsuario")
    public ResponseEntity<ApiResponse> crearSuperUsuario(@Valid @RequestBody SuperUserDto superUserDto){
        ApiResponse response = tokenService.crearSuperUsuario(superUserDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }



}
