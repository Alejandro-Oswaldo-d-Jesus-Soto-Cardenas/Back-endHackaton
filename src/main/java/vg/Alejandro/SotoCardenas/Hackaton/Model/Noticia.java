// src/main/java/vg/Alejandro/SotoCardenas/Hackaton/Model/Noticia.java

package vg.Alejandro.SotoCardenas.Hackaton.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "noticia")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Noticia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_noticia")
    private Long id;

    // ROMPE BUCLE CON CORRESPONSAL
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_corresponsal", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "noticias"})
    private Corresponsal corresponsal;

    @Column(nullable = false, length = 300)
    private String titulo;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String contenido;

    @Column(name = "video_url", length = 500)
    private String videoUrl;

    @CreationTimestamp
    @Column(name = "fecha_publicacion", nullable = false, updatable = false)
    private LocalDateTime fechaPublicacion;

    @Column(nullable = false)
    @Builder.Default
    private Boolean status = true;

    // ROMPE BUCLE CON FOTOS
    @OneToMany(mappedBy = "noticia", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("noticia")  // CLAVE
    @Builder.Default
    private List<FotoNoticia> fotos = new ArrayList<>();
}