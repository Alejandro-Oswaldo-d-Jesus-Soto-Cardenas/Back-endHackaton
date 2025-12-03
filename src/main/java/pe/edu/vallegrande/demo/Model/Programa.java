package pe.edu.vallegrande.demo.Model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Programa")
@Data
public class Programa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 10)
    private String codigo;

    @Column(nullable = false, length = 200)
    private String nombre;

    @Column(name = "duracion_semestres", nullable = false)
    private Integer duracionSemestres;

    @Column(columnDefinition = "BIT DEFAULT 1")
    private Boolean activo;
}