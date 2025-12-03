package pe.edu.vallegrande.demo.Repository;

import pe.edu.vallegrande.demo.Model.Provincia;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProvinciaRepository extends JpaRepository<Provincia, String> {
    List<Provincia> findByDepartamentoId(String departamentoId);
}