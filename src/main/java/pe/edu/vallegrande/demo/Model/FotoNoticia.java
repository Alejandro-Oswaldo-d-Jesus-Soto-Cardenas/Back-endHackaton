// src/main/java/pe/edu/vallegrande/demo/Model/FotoNoticia.java
package pe.edu.vallegrande.demo.Model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "foto_noticia")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FotoNoticia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_foto")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_noticia", nullable = false)
    private Noticia noticia;

    @Column(name = "ruta_foto", length = 500, nullable = false)
    private String rutaFoto;
}