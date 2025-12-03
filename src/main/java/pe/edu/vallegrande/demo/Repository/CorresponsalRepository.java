// src/main/java/pe/edu/vallegrande/demo/repository/CorresponsalRepository.java
package pe.edu.vallegrande.demo.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.edu.vallegrande.demo.Model.Corresponsal;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CorresponsalRepository extends JpaRepository<Corresponsal, Long> {

    Optional<Corresponsal> findByIdDoc(String idDoc);
    boolean existsByIdDoc(String idDoc);

    // CON paginación (esto es obligatorio ahora)
    Page<Corresponsal> findByStatusTrue(Pageable pageable);

    // Búsqueda por apellidos
    List<Corresponsal> findBySurnamesContainingIgnoreCase(String surnames);

    // Búsqueda por rango de fechas
    List<Corresponsal> findByRegisterDayBetween(LocalDateTime start, LocalDateTime end);

    // Búsqueda avanzada con filtros (opcional, pero útil)
    @Query("SELECT c FROM Corresponsal c WHERE " +
           "(:surnames IS NULL OR LOWER(c.surnames) LIKE LOWER(CONCAT('%', :surnames, '%'))) AND " +
           "(:idDoc IS NULL OR c.idDoc = :idDoc) AND " +
           "(:status IS NULL OR c.status = :status) AND " +
           "(:from IS NULL OR c.registerDay >= :from) AND " +
           "(:to IS NULL OR c.registerDay <= :to)")
    Page<Corresponsal> search(
            @Param("surnames") String surnames,
            @Param("idDoc") String idDoc,
            @Param("status") Boolean status,
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to,
            Pageable pageable);
}