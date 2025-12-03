package pe.edu.vallegrande.demo.Model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Distrito")
@Data
public class Distrito {

    @Id
    @Column(name = "id", columnDefinition = "CHAR(6)")
    private String id;

    @Column(name = "provincia_id", columnDefinition = "CHAR(4)")
    private String provinciaId;

    @Column(columnDefinition = "VARCHAR(100)")
    private String nombre;
}
