package org.example.siecapi.services.Token;

import org.apache.coyote.Response;
import org.aspectj.apache.bcel.classfile.annotation.RuntimeTypeAnnos;
import org.example.siecapi.config.ApiResponse;
import org.example.siecapi.controllers.Cliente.ClienteDto;
import org.example.siecapi.controllers.asesores.AsesorDto;
import org.example.siecapi.models.usuarios.Roles.Roles;
import org.example.siecapi.models.usuarios.Roles.RolesRepository;
import org.example.siecapi.models.usuarios.Usuarios;
import org.example.siecapi.models.usuarios.UsuariosRepository;
import org.example.siecapi.security.Token.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class TokenService {

    @Autowired
    UsuariosRepository usuariosRepository;
    @Autowired
    RolesRepository rolesRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private AuthenticationManager authenticationManager;

    public Usuarios asesorDtoMapeado(AsesorDto asesorDto) {

        Roles rol = rolesRepository.findByRol("ASESOR").orElseGet(() -> {
            Roles nuevoRol = new Roles();
            nuevoRol.setRol("ASESOR");
            return rolesRepository.save(nuevoRol);
        });

            Usuarios usuario = new Usuarios();
            usuario.setNombre(asesorDto.getNombre());
            usuario.setCorreo(asesorDto.getCorreo());
            usuario.setEstado(asesorDto.isEstado());
            usuario.setMonto(asesorDto.getMonto());
            usuario.setFechaContrato(asesorDto.getFechaContrato());
            usuario.setTelefono(asesorDto.getTelefono());
            usuario.setNumeroCuentaMT5(asesorDto.getCuentaMt5());
            usuario.setRol(Collections.singletonList(rol));
            usuario.setPassword(passwordEncoder.encode(asesorDto.getPassword()));
            return usuario;


    }

    public ResponseEntity<ApiResponse> crearAsesor (AsesorDto asesorDto) {

        try{
            if (usuariosRepository.findByCorreo(asesorDto.getCorreo()).isPresent()) {
                ApiResponse response = new ApiResponse(
                        HttpStatus.BAD_REQUEST,
                        true,
                        "Error: el correo ya existe"
                );
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            Usuarios usuario = asesorDtoMapeado(asesorDto);
            usuariosRepository.save(usuario);
            ApiResponse  response = new ApiResponse(
                    HttpStatus.OK,
                    false,
                    "asesor creado correctamente"

            );
            return ResponseEntity.status(HttpStatus.OK).body(response);


        }catch (Exception e){
            ApiResponse response = new ApiResponse(

                    HttpStatus.BAD_REQUEST,
                    true,
                    "Error al crear el asesor por " + e.getMessage()
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

        }

    }


    public ResponseEntity<?> Login(AsesorDto asesorDto) {

        // Buscar el usuario
        Usuarios user = usuariosRepository.findByCorreo(asesorDto.getCorreo())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        // Validar contraseña
        if (!passwordEncoder.matches(asesorDto.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas");
        }

        // Autenticar en el contexto de Spring Security
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(asesorDto.getCorreo(), asesorDto.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generar token JWT
        String token = jwtTokenProvider.generateToken(authentication);
        System.out.println("Tu token es :  " + token);
        // Obtener el rol
        String rol = user.getRol().stream()
                .map(Roles::getRol)
                .findFirst()
                .orElse("SIN_ROL");

        // Devolver respuesta
        return ResponseEntity.ok(Map.of(
                "token", token,
                "rol", rol,
                "email", user.getCorreo(),
                "nombre", user.getNombre()
        ));
    }



}
