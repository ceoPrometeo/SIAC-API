package org.example.siecapi.controllers.Cliente;

import jakarta.validation.Valid;
import org.example.siecapi.config.ApiResponse;
import org.example.siecapi.controllers.Cliente.dto.ClienteDto;
import org.example.siecapi.models.usuarios.Usuarios;
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
@RequestMapping("/api/cliente")
public class ClienteController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping()
    public ResponseEntity<ApiResponse> crearCliente (@Valid @RequestBody ClienteDto clienteDto) {
        ApiResponse response = usuarioService.createCliente(clienteDto).getBody();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
