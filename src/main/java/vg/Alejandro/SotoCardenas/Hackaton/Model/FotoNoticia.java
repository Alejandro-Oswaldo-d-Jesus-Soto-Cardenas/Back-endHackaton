// src/main/java/vg/Alejandro/SotoCardenas/Hackaton/Model/FotoNoticia.java

package vg.Alejandro.SotoCardenas.Hackaton.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "fotos", "corresponsal"})
    private Noticia noticia;

    @Column(name = "ruta_foto", length = 500, nullable = false)
    @JsonProperty("rutaFoto")  // Acepta el campo del frontend
    private String rutaFoto;
}