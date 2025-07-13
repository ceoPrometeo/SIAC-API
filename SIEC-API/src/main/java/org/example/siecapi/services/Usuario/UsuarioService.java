package org.example.siecapi.services.Usuario;

import jakarta.transaction.Transactional;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.example.siecapi.config.ApiResponse;
import org.example.siecapi.controllers.Cliente.dto.ClienteDto;
import org.example.siecapi.controllers.Cliente.dto.ClienteResponseDto;
import org.example.siecapi.controllers.Cliente.dto.ContratoDto;
import org.example.siecapi.controllers.Cliente.dto.ContratoResponseDto;
import org.example.siecapi.models.cateras.Cartera;
import org.example.siecapi.models.cateras.CarteraRepository;
import org.example.siecapi.models.cliente.Cliente;
import org.example.siecapi.models.cliente.ClienteRepository;
import org.example.siecapi.models.contratos.Contratos;
import org.example.siecapi.models.contratos.ContratosRepository;
import org.example.siecapi.models.usuarios.Roles.Roles;
import org.example.siecapi.models.usuarios.Roles.RolesRepository;
import org.example.siecapi.models.usuarios.Usuarios;
import org.example.siecapi.models.usuarios.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ContratosRepository contratosRepository;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private CarteraRepository carteraRepository;

    private static final int MAX_CONTRATOS = 6;



    public UsuarioService() {
        super();
    }


    @Transactional
    public ResponseEntity<ApiResponse> createCliente(ClienteDto clienteDto) {
        try {
            Usuarios usuarioEnSesion = obtenerUsuarioEnSesion();
            validarPermisos(usuarioEnSesion);

            Optional<Cliente> optionalCliente = clienteRepository.findByCorreo(clienteDto.getCorreo());
            Cliente cliente;
            boolean esNuevo = false;

            double montoContratos = clienteDto.getContratos() != null
                    ? clienteDto.getContratos().stream()
                    .mapToDouble(c -> c.getMonto() != null ? c.getMonto() : 0.0).sum()
                    : 0.0;

            if (optionalCliente.isPresent()) {
                cliente = optionalCliente.get();
            } else {
                cliente = mapearClienteDesdeDto(clienteDto, usuarioEnSesion);
                cliente = clienteRepository.saveAndFlush(cliente);
                esNuevo = true;
            }

            Cartera cartera = cliente.getCartera();
            if (cartera == null) {
                cartera = new Cartera();
                cartera.setCliente(cliente);
                cartera.setUsuario(usuarioEnSesion);
                cartera.setMonto(0.0);
                cartera.setNumero_de_Clientes(0);
            }

            cartera.setMonto(cartera.getMonto() + montoContratos);
            if (esNuevo) {
                cartera.setNumero_de_Clientes(cartera.getNumero_de_Clientes() + 1);
            }
            carteraRepository.save(cartera);
            cliente.setCartera(cartera);

            if (clienteDto.getContratos() != null) {
                final Cliente finalCliente = cliente;

                List<Contratos> contratos = clienteDto.getContratos().stream().map(dto -> {
                    Contratos contrato = new Contratos();
                    contrato.setCuentaMT5(dto.getCuentaMT5());
                    contrato.setMonto(dto.getMonto());
                    contrato.setFecha_inicio(dto.getFecha_inicio());
                    contrato.setFechaRenovacion(dto.getFecha_renovacion());
                    contrato.setEstatusRenovacion(dto.getEstatus_renovacion());
                    contrato.setTipoContrato(dto.getTipo_de_Contrato());
                    contrato.setUsuario(usuarioEnSesion);
                    contrato.setCliente(finalCliente);
                    return contrato;
                }).toList();

                contratosRepository.saveAll(contratos);

                if (cliente.getContratos() == null) {
                    cliente.setContratos(new ArrayList<>());
                }
                cliente.getContratos().addAll(contratos);
            }

            clienteRepository.saveAndFlush(cliente);

            return ResponseEntity.ok(new ApiResponse(
                    mapToResponseDto(cliente),
                    HttpStatus.OK,
                    false,
                    esNuevo ? "Cliente creado correctamente" : "Contratos agregados al cliente"
            ));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, true, e.getMessage()));
        }
    }


    private Usuarios obtenerUsuarioEnSesion() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {
            throw new UsernameNotFoundException("No hay usuario autenticado.");
        }

        Object principal = authentication.getPrincipal();
        String username = principal instanceof UserDetails userDetails ? userDetails.getUsername() : principal.toString();

        return usuariosRepository.findByCorreo(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));
    }











    private void validarPermisos(Usuarios usuarioEnSesion) {
        boolean esAsesor = usuarioEnSesion.getRol().stream().anyMatch(r -> "ASESOR".equalsIgnoreCase(r.getRol()));
        boolean esSuperusuario = usuarioEnSesion.getRol().stream().anyMatch(r -> "SUPERUSUARIO".equalsIgnoreCase(r.getRol()));

        if (!esAsesor && !esSuperusuario) {
            throw new IllegalArgumentException("Permiso denegado. Solo ASESOR o SUPERUSUARIO puede registrar clientes.");
        }
    }

    private Cliente mapearClienteDesdeDto(ClienteDto clienteDto, Usuarios usuarioEnSesion) {
        Cliente cliente = new Cliente();
        cliente.setNombre(clienteDto.getNombre());
        cliente.setCorreo(clienteDto.getCorreo());
        cliente.setEstado(clienteDto.isEstado());
        cliente.setTelefono(clienteDto.getTelefono());

        if (usuarioEnSesion != null && usuarioEnSesion.getRol() != null) {
            boolean esAsesor = usuarioEnSesion.getRol().stream()
                    .anyMatch(r -> "ASESOR".equalsIgnoreCase(r.getRol()));
            boolean esSuperusuario = usuarioEnSesion.getRol().stream()
                    .anyMatch(r -> "SUPERUSUARIO".equalsIgnoreCase(r.getRol()));

            if (esAsesor || esSuperusuario) {
                cliente.setAsesor(usuarioEnSesion);
            }
        }

        Roles rolCliente = rolesRepository.findByRol("CLIENTE").orElseGet(() -> {
            Roles nuevoRol = new Roles();
            nuevoRol.setRol("CLIENTE");
            return rolesRepository.save(nuevoRol);
        });

        List<Roles> rolesCliente = new ArrayList<>();
        rolesCliente.add(rolCliente);
        cliente.setRol(rolesCliente);

        return cliente;
    }

    private ClienteResponseDto mapToResponseDto(Cliente cliente) {
        ClienteResponseDto dto = new ClienteResponseDto();
        dto.setId(cliente.getId());
        dto.setNombre(cliente.getNombre());
        dto.setCorreo(cliente.getCorreo());
        dto.setTelefono(cliente.getTelefono());
        dto.setEstado(cliente.isEstado());
        dto.setCarteraMonto(cliente.getCartera() != null ? cliente.getCartera().getMonto() : 0.0);

        List<ContratoResponseDto> contratosDto = new ArrayList<>();
        if (cliente.getContratos() != null) {
            cliente.getContratos().forEach(c -> {
                ContratoResponseDto cDto = new ContratoResponseDto();
                cDto.setCuentaMT5(c.getCuentaMT5());
                cDto.setMonto(c.getMonto());
                cDto.setFechaInicio(c.getFecha_inicio());
                cDto.setFechaRenovacion(c.getFechaRenovacion());
                cDto.setEstatusRenovacion(c.getEstatusRenovacion());
                cDto.setTipoContrato(c.getTipoContrato());
                cDto.setUsuario(c.getUsuario());
                contratosDto.add(cDto);
            });
        }

        dto.setContratos(contratosDto);
        return dto;
    }

    public ResponseEntity<ApiResponse> getAllClientes() {
        try {
            List<Cliente> clientes = clienteRepository.findAll();

            List<ClienteResponseDto> dtoList = clientes.stream().map(cliente -> {
                ClienteResponseDto dto = new ClienteResponseDto();
                dto.setId(cliente.getId());
                dto.setNombre(cliente.getNombre());
                dto.setCorreo(cliente.getCorreo());
                dto.setTelefono(cliente.getTelefono());
                dto.setEstado(cliente.isEstado());
                dto.setCarteraMonto(cliente.getCartera() != null ? cliente.getCartera().getMonto() : 0.0);

                List<ContratoResponseDto> contratosDto = new ArrayList<>();

                if (cliente.getContratos() != null && !cliente.getContratos().isEmpty()) {
                    // tomar el primer contrato o el más reciente (si deseas, aquí puedes ordenar por fecha)
                    Contratos contrato = cliente.getContratos().get(0);

                    ContratoResponseDto cDto = new ContratoResponseDto();
                    cDto.setCuentaMT5(contrato.getCuentaMT5());
                    cDto.setMonto(contrato.getMonto());
                    cDto.setFechaInicio(contrato.getFecha_inicio());
                    cDto.setFechaRenovacion(contrato.getFechaRenovacion());
                    cDto.setEstatusRenovacion(contrato.getEstatusRenovacion());
                    cDto.setTipoContrato(contrato.getTipoContrato());
                    cDto.setUsuario(contrato.getUsuario());
                    contratosDto.add(cDto);
                }

                dto.setContratos(contratosDto);

                return dto;
            }).toList();

            ApiResponse response = new ApiResponse(
                    dtoList,
                    HttpStatus.OK,
                    false,
                    "Lista de clientes obtenida correctamente"
            );

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, true, "Error al obtener clientes: " + e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }


    public ApiResponse findClientesByEstatusRenovacion(String estatus) {
        List<Contratos> contratos = contratosRepository.findByEstatusRenovacion(estatus);

        List<Map<String, Object>> clientesConContratos = contratos.stream().map(contrato -> {
            Map<String, Object> map = new HashMap<>();

            Cliente cliente = contrato.getCliente();
            Usuarios asesor = contrato.getUsuario();

            map.put("contratoId", contrato.getId());
            map.put("cuentaMT5", contrato.getCuentaMT5());
            map.put("monto", contrato.getMonto());
            map.put("fechaRenovacion", contrato.getFechaRenovacion());
            map.put("estatusRenovacion", contrato.getEstatusRenovacion());

            map.put("clienteId", cliente.getId());
            map.put("clienteNombre", cliente.getNombre());
            map.put("clienteCorreo", cliente.getCorreo());

            if (asesor != null) {
                map.put("asesorNombre", asesor.getNombre());
                map.put("asesorCorreo", asesor.getCorreo());
            }

            return map;
        }).toList();

        return new ApiResponse(
                clientesConContratos,
                HttpStatus.OK,
                false,
                contratos.size() + " contratos con estatus_renovacion=" + estatus
        );
    }
    @Transactional
    public ApiResponse actualizarEstatusContrato(Long contratoId, String nuevoEstatus) {
        Contratos contrato = contratosRepository.findById(contratoId)
                .orElseThrow(() -> new IllegalArgumentException("Contrato no encontrado con id: " + contratoId));

        contrato.setEstatusRenovacion(nuevoEstatus);
        contratosRepository.save(contrato);

        return new ApiResponse(
                contrato,
                HttpStatus.OK,
                false,
                "Estatus actualizado a " + nuevoEstatus + " para contrato id: " + contratoId
        );
    }


    public ApiResponse findByTipoContrato(String status) {
        List<Contratos> contratos = contratosRepository.findByTipoContrato(status);

        int cantidadEstado = contratos.size();

        return new ApiResponse(
                HttpStatus.OK,
                false,
                cantidadEstado + " contratos con estatus_renovacion=" + status
        );
    }




    public ResponseEntity<ApiResponse> registrarClientesDesdeArchivo(MultipartFile file) {
        System.out.println("Iniciando carga de clientes desde archivo CSV: {}"+ file.getOriginalFilename());

        try {
            Usuarios usuarioEnSesion = obtenerUsuarioEnSesion();
            validarPermisos(usuarioEnSesion);
            System.out.println("Usuario autenticado para la carga: {}"+ usuarioEnSesion.getCorreo());

            List<String> errores = new ArrayList<>();
            int registrados = 0;

            CSVParser parser = CSVParser.parse(file.getInputStream(), java.nio.charset.StandardCharsets.UTF_8, CSVFormat.DEFAULT.withHeader());

            for (CSVRecord record : parser) {
                System.out.println("Procesando línea {}: {}"+ record.getRecordNumber()+record);

                try {
                    ClienteDto dto = new ClienteDto();
                    dto.setNombre(record.get("nombre"));
                    dto.setCorreo(record.get("correo"));
                    dto.setTelefono(record.get("telefono"));
                    dto.setEstado(true);

                    ContratoDto contrato = new ContratoDto();
                    contrato.setCuentaMT5(record.get("cuentaMT5"));
                    contrato.setMonto(Double.parseDouble(record.get("monto")));

                    LocalDate fechaInicio = LocalDate.parse(record.get("fecha_inicio"));
                    LocalDate fechaRenovacion = fechaInicio.plusYears(1);

                    contrato.setFecha_inicio(fechaInicio);
                    contrato.setFecha_renovacion(fechaRenovacion);

                    contrato.setEstatus_renovacion(record.get("estatus_renovacion"));
                    contrato.setTipo_de_Contrato(record.get("tipo_de_Contrato"));


                    dto.setContratos(List.of(contrato));

                    System.out.println("ClienteDTO construido: nombre={}, correo={}, telefono={}"+
                            dto.getNombre()+ dto.getCorreo()+ dto.getTelefono());
                    System.out.println("ContratoDTO: cuentaMT5={}, monto={}, fecha_inicio={}, fecha_renovacion={}, tipoContrato={}"+
                            contrato.getCuentaMT5()+ contrato.getMonto()+ contrato.getFecha_inicio()+
                            contrato.getFecha_renovacion()+ contrato.getTipo_de_Contrato());

                    ResponseEntity<ApiResponse> response = createCliente(dto);
                    if (!response.getBody().isError()) {
                        System.out.println("Cliente registrado correctamente: {}"+ dto.getCorreo());
                        registrados++;
                    } else {
                        System.out.println("Error registrando cliente en línea {}: {}"+ record.getRecordNumber()+ response.getBody().getMessage());
                        errores.add("Error en línea " + record.getRecordNumber() + ": " + response.getBody().getMessage());
                    }

                } catch (Exception e) {
                    System.out.println("Excepción procesando línea {}: {}"+ record.getRecordNumber()+ e.getMessage()+ e);
                    errores.add("Error en línea " + record.getRecordNumber() + ": " + e.getMessage());
                }
            }

            System.out.println("Proceso finalizado. Clientes registrados: {}, con {} errores."+  registrados + errores.size());

            ApiResponse resp = new ApiResponse(
                    Map.of("registrados", registrados, "errores", errores),
                    HttpStatus.OK,
                    false,
                    "Proceso de carga finalizado"
            );

            return ResponseEntity.ok(resp);

        } catch (Exception e) {
            System.out.println("Error fatal procesando archivo CSV: {}"+ e.getMessage() +e);
            return new ResponseEntity<>(
                    new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, true, "Error al procesar archivo: " + e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }


    public ApiResponse getMontoCarteraAsesor() {
        Usuarios usuarioEnSesion = obtenerUsuarioEnSesion();
        double total = carteraRepository.findByUsuario(usuarioEnSesion).stream()
                .mapToDouble(Cartera::getMonto)
                .sum();

        return new ApiResponse(
                HttpStatus.OK,
                false,
                "Monto total en cartera: " + total
        );
    }

    public ApiResponse contarContratosPorTipo() {
        Map<String, Long> conteo = contratosRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(Contratos::getTipoContrato, Collectors.counting()));

        return new ApiResponse(
                conteo,
                HttpStatus.OK,
                false,
                "Conteo de contratos por tipo"
        );
    }

}
