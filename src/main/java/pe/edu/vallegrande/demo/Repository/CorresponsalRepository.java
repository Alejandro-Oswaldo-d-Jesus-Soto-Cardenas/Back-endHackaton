package pe.edu.vallegrande.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.vallegrande.demo.Model.Corresponsal;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CorresponsalRepository extends JpaRepository<Corresponsal, Long> {

    Optional<Corresponsal> findByIdDoc(String idDoc);

    boolean existsByIdDoc(String idDoc);

    List<Corresponsal> findByStatusTrue();

    List<Corresponsal> findBySurnamesContainingIgnoreCase(String surnames);

    List<Corresponsal> findByRegisterDayBetween(LocalDateTime start, LocalDateTime end);
}