package org.example.siecapi.services.Token;

import jakarta.servlet.http.HttpSession;
import org.example.siecapi.config.ApiResponse;
import org.example.siecapi.controllers.User.dto.AsesorDto;
import org.example.siecapi.controllers.User.dto.LoginDto;
import org.example.siecapi.controllers.User.dto.SuperUserDto;
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

    @Autowired
    private HttpSession session;

    public Usuarios asesorDtoMapeado(AsesorDto dto) {
        Roles rol = rolesRepository.findByRol("ASESOR").orElseGet(() -> {
            Roles nuevoRol = new Roles();
            nuevoRol.setRol("ASESOR");
            return rolesRepository.save(nuevoRol);
        });

        Usuarios usuario = new Usuarios();
        usuario.setNombre(dto.getNombre());
        usuario.setCorreo(dto.getCorreo());
        usuario.setEstado(dto.isEstado());
        usuario.setTelefono(dto.getTelefono());
        usuario.setPassword(passwordEncoder.encode(dto.getPassword()));
        usuario.setRol(Collections.singletonList(rol));

        return usuario;
    }

    public ResponseEntity<ApiResponse> crearAsesor(AsesorDto dto) {
        try {
            if (usuariosRepository.findByCorreo(dto.getCorreo()).isPresent()) {
                ApiResponse response = new ApiResponse(
                        HttpStatus.BAD_REQUEST,
                        true,
                        "Error: el correo ya existe"
                );
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            Usuarios usuario = asesorDtoMapeado(dto);
            usuariosRepository.save(usuario);

            ApiResponse response = new ApiResponse(
                    usuario,
                    HttpStatus.OK,
                    false,
                    "Asesor creado correctamente"
            );
            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (Exception e) {
            ApiResponse response = new ApiResponse(
                    HttpStatus.BAD_REQUEST,
                    true,
                    "Error al crear el asesor: " + e.getMessage()
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    public ResponseEntity<?> login(LoginDto dto) {
        try {
            Usuarios user = usuariosRepository.findByCorreo(dto.getCorreo())
                    .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

            if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas");
            }

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(dto.getCorreo(), dto.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String token = jwtTokenProvider.generateToken(authentication);

            String rol = user.getRol().stream()
                    .map(Roles::getRol)
                    .findFirst()
                    .orElse("SIN_ROL");

            // ✅ Aquí guardamos el objeto usuario completo en la sesión
            session.setAttribute("usuario", user);

            return ResponseEntity.ok(Map.of(
                    "token", token,
                    "rol", rol,
                    "email", user.getCorreo()
            ));
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(
                    null,
                    true,
                    e.getMessage()
            );
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    public ApiResponse crearSuperUsuario(SuperUserDto superUserDto) {
        try {
            if (usuariosRepository.findByCorreo(superUserDto.getCorreo()).isPresent()) {
                return new ApiResponse(
                        null,
                        HttpStatus.LOCKED,
                        "El correo " + superUserDto.getCorreo() + " ya existe"
                );
            }

            Usuarios user = new Usuarios();
            user.setCorreo(superUserDto.getCorreo());
            user.setPassword(passwordEncoder.encode(superUserDto.getPassword()));
            user.setEstado(true);

            Roles rol = rolesRepository.findByRol("SUPERUSUARIO").orElseGet(() -> {
                Roles nuevoRol = new Roles();
                nuevoRol.setRol("SUPERUSUARIO");
                return rolesRepository.save(nuevoRol);
            });

            user.setRol(Collections.singletonList(rol));

            Usuarios response = usuariosRepository.save(user);

            return new ApiResponse(
                    response,
                    HttpStatus.OK,
                    "SuperUsuario creado correctamente"
            );

        } catch (Exception e) {
            return new ApiResponse(
                    e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "SuperUsuario no pudo ser creado correctamente"
            );
        }
    }

}
