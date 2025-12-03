package pe.edu.vallegrande.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.vallegrande.demo.Model.Departamento;

@Repository
public interface DepartamentoRepository extends JpaRepository<Departamento, String> {
    // No se requieren métodos adicionales por ahora, 
    // JpaRepository ya incluye findAll(), findById(), save(), etc.
}