package org.example.siecapi.services.Cliente;

import jakarta.transaction.Transactional;
import org.example.siecapi.config.ApiResponse;
import org.example.siecapi.controllers.Cliente.ClienteDto;
import org.example.siecapi.models.usuarios.Roles.Roles;
import org.example.siecapi.models.usuarios.Usuarios;
import org.example.siecapi.models.usuarios.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    @Autowired
    UsuariosRepository usuariosRepository;

    public ClienteService() {
        super();
    }

    public Usuarios clienteDtoMapeado(ClienteDto clienteDto) {
        Usuarios usuario = new Usuarios();

        usuario.setNombre(clienteDto.getNombre());
        usuario.setCorreo(clienteDto.getCorreo());
        usuario.setEstado(clienteDto.isEstado());
        usuario.setMonto(clienteDto.getMonto());
        usuario.setFechaContrato(clienteDto.getFechaContrato());
        usuario.setTelefono(clienteDto.getTelefono());
        usuario.setNumeroCuentaMT5(clienteDto.getCuentaMt5());
        usuario.setRol(clienteDto.getRol());
        return usuario;
    }

    @Transactional()
    public ResponseEntity<ApiResponse> createCliente(ClienteDto clienteDto) {

        try{
             Usuarios usuario = clienteDtoMapeado(clienteDto);

            Usuarios nuevoUsuario = usuariosRepository.save(usuario);

            ApiResponse response = new ApiResponse(
                    nuevoUsuario,
                    HttpStatus.OK,
                    false,
                    "Todo salio bien"
            );
             return new ResponseEntity<>(response, HttpStatus.CREATED);


        }catch (Exception e){

            ApiResponse response = new ApiResponse(
                    HttpStatus.BAD_REQUEST,
                    false,
                    "Todo salio bien"
            );
            return new ResponseEntity<>(response, HttpStatus.CREATED);


        }


    }


}
