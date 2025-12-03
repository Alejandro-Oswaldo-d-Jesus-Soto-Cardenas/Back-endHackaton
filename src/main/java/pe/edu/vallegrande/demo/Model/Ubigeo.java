// src/main/java/pe/edu/vallegrande/demo/Model/Ubigeo.java
package pe.edu.vallegrande.demo.Model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ubigeo")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ubigeo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 6, unique = true, nullable = false)
    private String codigo;

    @Column(length = 100, nullable = false)
    private String departamento;

    @Column(length = 100, nullable = false)
    private String provincia;

    @Column(length = 150, nullable = false)
    private String distrito;

    @Column(nullable = false)
    @Builder.Default
    private Boolean status = true;

    @Column(name = "register_day", nullable = false, updatable = false)
    private LocalDateTime registerDay = LocalDateTime.now();
}