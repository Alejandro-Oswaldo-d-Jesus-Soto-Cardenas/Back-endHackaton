package pe.edu.vallegrande.demo.Repository;

import pe.edu.vallegrande.demo.Model.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {
    List<Estudiante> findByEstadoTrueOrderByFechaRegistroDesc();

    @Query("SELECT e, p, d, prov, dep FROM Estudiante e " +
           "JOIN Programa p ON e.programaId = p.id " +
           "JOIN Distrito d ON e.distritoId = d.id " +
           "JOIN Provincia prov ON d.provinciaId = prov.id " +
           "JOIN Departamento dep ON prov.departamentoId = dep.id " +
           "WHERE e.estado = true")
    List<Object[]> findAllConDetalles();
}