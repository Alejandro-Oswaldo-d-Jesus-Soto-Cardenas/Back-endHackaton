package pe.edu.vallegrande.demo.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;

@Entity
@Table(name = "Estudiante")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Estudiante {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank 
    @Size(max = 15)
    @Column(name = "numero_documento", unique = true, nullable = false)
    private String numeroDocumento;

    @NotBlank
    @Column(name = "tipo_documento", nullable = false)
    @Builder.Default
    private String tipoDocumento = "DNI";

    @NotBlank @Size(max = 100)
    private String apellidos;

    @NotBlank @Size(max = 100)
    private String nombres;

    @NotNull @Past
    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @NotBlank
    @Column(columnDefinition = "CHAR(1)")
    private String genero;

    @Pattern(regexp = "^9\\d{8}$", message = "Celular debe tener 9 dígitos y empezar con 9")
    private String celular;

    @Email @Size(max = 150)
    @Column(unique = true)
    private String email;

    @NotBlank @Size(min = 10, max = 255)
    private String direccion;

    // --- CORRECCIÓN IMPORTANTE: Mapeo simple de IDs ---
    @NotBlank
    @Column(name = "distrito_id", columnDefinition = "CHAR(6)")
    private String distritoId;

    @NotNull
    @Column(name = "programa_id")
    private Integer programaId;

    // --- AQUÍ ESTABA EL ERROR 500 ---
    // En tu SQL es "año_ingreso", así que en name debe ir "año_ingreso"
    @Column(name = "año_ingreso") 
    @Builder.Default 
    private Short anioIngreso = (short) Year.now().getValue(); 

    @Column(name = "fecha_registro")
    @Builder.Default 
    private LocalDateTime fechaRegistro = LocalDateTime.now();

    @Builder.Default 
    private Boolean estado = true;
}