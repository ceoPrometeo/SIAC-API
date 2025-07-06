package org.example.siecapi.services.Usuario;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.example.siecapi.config.ApiResponse;
import org.example.siecapi.controllers.Cliente.dto.ClienteDto;
import org.example.siecapi.controllers.Cliente.dto.ContratoDto;
import org.example.siecapi.controllers.User.dto.SuperUserDto;
import org.example.siecapi.models.cliente.Cliente;
import org.example.siecapi.models.cliente.ClienteRepository;
import org.example.siecapi.models.contratos.Contratos;
import org.example.siecapi.models.contratos.ContratosRepository;
import org.example.siecapi.models.usuarios.Roles.Roles;
import org.example.siecapi.models.usuarios.Usuarios;
import org.example.siecapi.models.usuarios.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    UsuariosRepository usuariosRepository;

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    ContratosRepository contratosRepository;

    @Autowired
    HttpSession sesion;

    public UsuarioService() {
        super();
    }

    public Cliente clienteDtoMapeado(ClienteDto clienteDto) {
        Cliente cliente = new Cliente();

        cliente.setNombre(clienteDto.getNombre());
        cliente.setCorreo(clienteDto.getCorreo());
        cliente.setEstado(clienteDto.isEstado());
        cliente.setTelefono(clienteDto.getTelefono());

        Usuarios usuarioEnSesion = (Usuarios) sesion.getAttribute("usuario");
        if (usuarioEnSesion != null && usuarioEnSesion.getRol() != null &&
                usuarioEnSesion.getRol().stream().anyMatch(r -> "ASESOR".equalsIgnoreCase(r.getRol()))) {
            cliente.setAsesor(usuarioEnSesion);
        }

        Roles rolCliente = new Roles();
        rolCliente.setRol("CLIENTE");
        cliente.setRol(List.of(rolCliente));

        cliente.setCartera(clienteDto.getCartera());

        return cliente;
    }

    @Transactional
    public ResponseEntity<ApiResponse> createCliente(ClienteDto clienteDto) {
        try {
            Cliente cliente = clienteDtoMapeado(clienteDto);

            Usuarios usuarioEnSesion = (Usuarios) sesion.getAttribute("usuario");

            List<ContratoDto> contratosDto = clienteDto.getContratos();
            if (contratosDto != null && !contratosDto.isEmpty()) {
                List<Contratos> contratos = new ArrayList<>();
                for (ContratoDto contratoDto : contratosDto) {
                    Contratos contrato = new Contratos();
                    contrato.setCuentaMT5(contratoDto.getCuentaMT5());
                    contrato.setMonto(contratoDto.getMonto());
                    contrato.setFecha_inicio(contratoDto.getFecha_inicio());
                    contrato.setFecha_renovacion(contratoDto.getFecha_renovacion());
                    contrato.setEstatus_renovacion(contratoDto.isEstatus_renovacion());

                    if (usuarioEnSesion != null) {
                        contrato.setUsuario(usuarioEnSesion);
                    }

                    contratosRepository.save(contrato);
                    contratos.add(contrato);
                }

                cliente.setContratos(contratos);
            }

            Cliente nuevoCliente = clienteRepository.save(cliente);

            ApiResponse response = new ApiResponse(
                    nuevoCliente,
                    HttpStatus.CREATED,
                    false,
                    "Cliente creado correctamente"
            );
            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (Exception e) {
            ApiResponse response = new ApiResponse(
                    HttpStatus.BAD_REQUEST,
                    true,
                    "Error al crear cliente"
            );
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }



}
