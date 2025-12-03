package pe.edu.vallegrande.demo.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "corresponsal")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Corresponsal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank @Size(max = 200)
    @Column(name = "name", nullable = false)
    private String name;

    @NotBlank @Size(max = 150)
    @Column(name = "surnames", nullable = false)
    private String surnames;

    @NotBlank @Size(max = 30)
    @Column(name = "id_doc", unique = true, nullable = false)
    private String idDoc;

    @NotBlank @Size(max = 100)
    @Column(name = "country", nullable = false)
    private String country;

    @NotBlank @Size(max = 100)
    @Column(name = "department", nullable = false)
    private String department;

    @NotBlank @Size(max = 100)
    @Column(name = "province", nullable = false)
    private String province;

    @NotBlank @Size(max = 100)
    @Column(name = "district", nullable = false)
    private String district;

    @NotBlank @Size(max = 150)
    @Column(name = "locality", nullable = false)
    private String locality;

    @Builder.Default
    @Column(nullable = false)
    private Boolean status = true;

    @Builder.Default
    @Column(name = "register_day", nullable = false)
    private LocalDateTime registerDay = LocalDateTime.now();
}