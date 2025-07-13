package org.example.siecapi.config;

import jakarta.validation.ConstraintViolationException;
//import org.example.siecapi.exceptions.RecursoNoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Manejar errores de validación de campos (@Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {

        Map<String, String> errores = new HashMap<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errores.put(error.getField(), error.getDefaultMessage());
        }

        ApiResponse response = new ApiResponse(
                errores,
                HttpStatus.BAD_REQUEST,
                true,
                "Error en la validación de campos"
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // Manejar ConstraintViolation (por ejemplo en validaciones a nivel de parámetro en @PathVariable, @RequestParam)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse> handleConstraintViolation(ConstraintViolationException ex) {

        ApiResponse response = new ApiResponse(
                null,
                HttpStatus.BAD_REQUEST,
                true,
                "Violación de restricciones: " + ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // Manejar entidad no encontrada (RecursoNoEncontradoException personalizada)
//    @ExceptionHandler(RecursoNoEncontradoException.class)
//    public ResponseEntity<ApiResponse> handleRecursoNoEncontrado(RecursoNoEncontradoException ex) {
//
//        ApiResponse response = new ApiResponse(
//                null,
//                HttpStatus.NOT_FOUND,
//                ex.getMessage()
//        );
//
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
//    }

    // Manejar cualquier otro error (general)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleGeneralException(Exception ex) {

        ApiResponse response = new ApiResponse(
                null,
                HttpStatus.INTERNAL_SERVER_ERROR,
                true,
                "Error interno del servidor: " + ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
