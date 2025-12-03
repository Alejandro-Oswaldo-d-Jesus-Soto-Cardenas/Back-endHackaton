// src/main/java/pe/edu/vallegrande/demo/Repository/UbigeoRepository.java
package pe.edu.vallegrande.demo.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.vallegrande.demo.Model.Ubigeo;

import java.util.List;

public interface UbigeoRepository extends JpaRepository<Ubigeo, Long> {
    List<Ubigeo> findByStatusTrue();
    Page<Ubigeo> findByStatusTrue(Pageable pageable);
    List<Ubigeo> findByProvinciaIgnoreCase(String provincia);
}