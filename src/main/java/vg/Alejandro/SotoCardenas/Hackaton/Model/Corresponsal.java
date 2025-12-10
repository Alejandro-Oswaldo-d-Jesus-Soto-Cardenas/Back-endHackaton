// src/main/java/vg/Alejandro/SotoCardenas/Hackaton/Model/Corresponsal.java
package vg.Alejandro.SotoCardenas.Hackaton.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "corresponsal")
@Data
@NoArgsConstructor(force = true)  // FUERZA EL CONSTRUCTOR VACÍO
@AllArgsConstructor
@Builder
public class Corresponsal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 200, nullable = false)
    private String name;

    @Column(name = "surnames", length = 150, nullable = false)
    private String surnames;

    @Column(name = "id_doc", length = 30, nullable = false, unique = true)
    @JsonProperty("idDoc")
    private String idDoc;

    @Column(length = 100, nullable = false)
    private String country;

    @Column(length = 100, nullable = false)
    private String department;

    @Column(length = 100, nullable = false)
    private String province;

    @Column(length = 100, nullable = false)
    private String district;

    @Column(length = 150, nullable = false)
    private String locality;

    @Column(nullable = false)
    @Builder.Default
    private Boolean status = true;

    @Column(name = "register_day", nullable = false, updatable = false)
    private LocalDateTime registerDay = LocalDateTime.now();

    @OneToMany(mappedBy = "corresponsal", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties({"corresponsal"})
    @Builder.Default
    private List<Noticia> noticias = new ArrayList<>();
}