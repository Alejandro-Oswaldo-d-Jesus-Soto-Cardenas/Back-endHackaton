package pe.edu.vallegrande.demo.Repository;

import pe.edu.vallegrande.demo.Model.Programa;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProgramaRepository extends JpaRepository<Programa, Integer> {
	List<Programa> findByActivoTrue();
}