package pe.edu.vallegrande.demo.Model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Provincia")
@Data
public class Provincia {

    @Id
    @Column(name = "id", columnDefinition = "CHAR(4)")
    private String id;

    @Column(name = "departamento_id", columnDefinition = "CHAR(2)")
    private String departamentoId;

    @Column(columnDefinition = "VARCHAR(100)")
    private String nombre;
}
