package pe.edu.vallegrande.demo.Dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record EstudianteRequest(
    @NotBlank(message = "El DNI es obligatorio")
    @Size(min = 8, max = 15)
    String numeroDocumento,

    String tipoDocumento,

    @NotBlank(message = "Los apellidos son obligatorios")
    String apellidos,

    @NotBlank(message = "Los nombres son obligatorios")
    String nombres,

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    LocalDate fechaNacimiento,

    @NotBlank
    String genero,

    String celular,
    String email,
    String direccion,
    
    @NotBlank(message = "El distrito es obligatorio")
    String distritoId,

    @NotNull(message = "El programa es obligatorio")
    Integer programaId,

    Short anioIngreso
) {}