package pe.edu.vallegrande.demo.Repository;

import pe.edu.vallegrande.demo.Model.Distrito;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DistritoRepository extends JpaRepository<Distrito, String> {
    List<Distrito> findByProvinciaId(String provinciaId);
}