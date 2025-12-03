package pe.edu.vallegrande.demo.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstudianteResponse {
    private Long id;
    private String numeroDocumento;
    private String apellidos;
    private String nombres;
    private String apellidosNombres; // Campo concatenado
    private String programa;         // Nombre del programa
    private String ubicacion;        // Dep / Prov / Dist
    private String celular;
    private String email;
    private Integer anioIngreso;
    private Boolean estado;
}