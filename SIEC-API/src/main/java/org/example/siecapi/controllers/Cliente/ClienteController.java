package org.example.siecapi.controllers.Cliente;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.example.siecapi.config.ApiResponse;
import org.example.siecapi.models.usuarios.Usuarios;
import org.example.siecapi.services.Cliente.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/cliente/")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;
    @Autowired
    private HikariDataSource dataSource;

    @PostMapping("crear")
    public ResponseEntity<ApiResponse> crearCliente(@Valid @RequestBody ClienteDto clienteDto) {
        ApiResponse response = clienteService.createCliente(clienteDto).getBody();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


}
