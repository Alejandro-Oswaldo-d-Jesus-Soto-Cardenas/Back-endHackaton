// src/main/java/pe/edu/vallegrande/demo/Repository/UbigeoRepository.java
package vg.Alejandro.SotoCardenas.Hackaton.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import vg.Alejandro.SotoCardenas.Hackaton.Model.Ubigeo;

import java.util.List;

public interface UbigeoRepository extends JpaRepository<Ubigeo, Long> {
    List<Ubigeo> findByStatusTrue();
    Page<Ubigeo> findByStatusTrue(Pageable pageable);
    List<Ubigeo> findByProvinciaIgnoreCase(String provincia);
}