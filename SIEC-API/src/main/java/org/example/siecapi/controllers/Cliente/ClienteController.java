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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/cliente")
public class ClienteController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping()
    public ResponseEntity<ApiResponse> crearCliente (@Valid @RequestBody  ClienteDto clienteDto) {
        ApiResponse response = usuarioService.createCliente(clienteDto).getBody();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @GetMapping("/obtenerTodos")
    public ResponseEntity<ApiResponse> obtenerTodos(){
        ApiResponse response = usuarioService.getAllClientes().getBody();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/cliente/cargar-archivo")
    public ResponseEntity<ApiResponse> cargarClientes(@RequestParam("file") MultipartFile file) {
        return usuarioService.registrarClientesDesdeArchivo(file);
    }
    @GetMapping("/contratos/estatus")
    public ApiResponse contratosPorEstatus(@RequestParam String estatus) {
        return usuarioService.findClientesByEstatusRenovacion(estatus);
    }
    @GetMapping("/contratos/tipoContrato")
    public ApiResponse contratosPorTipoContrato(@RequestParam String estatus) {
        return usuarioService.findByTipoContrato(estatus);
    }

    @PostMapping("/contratos/{id}/cambiar-estatus")
    public ResponseEntity<ApiResponse> cambiarEstatus(
            @PathVariable Long id,
            @RequestParam String nuevoEstatus) {
        return ResponseEntity.ok(usuarioService.actualizarEstatusContrato(id, nuevoEstatus));
    }

    @GetMapping("/cartera/monto")
    public ApiResponse montoCartera() {
        return usuarioService.getMontoCarteraAsesor();
    }

    @GetMapping("/contratos/tipos")
    public ApiResponse contarContratosPorTipo() {
        return usuarioService.contarContratosPorTipo();
    }


}
