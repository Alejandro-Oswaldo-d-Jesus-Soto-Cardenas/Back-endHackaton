package pe.edu.vallegrande.demo.Model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Departamento")
@Data
public class Departamento {

    @Id
    @Column(name = "id", columnDefinition = "CHAR(2)")
    private String id;

    @Column(columnDefinition = "VARCHAR(100)")
    private String nombre;
}
