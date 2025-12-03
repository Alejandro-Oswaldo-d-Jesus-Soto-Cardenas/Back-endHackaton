// src/main/java/pe/edu/vallegrande/demo/service/CorresponsalService.java
package pe.edu.vallegrande.demo.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.vallegrande.demo.Model.Corresponsal;
import pe.edu.vallegrande.demo.Repository.CorresponsalRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CorresponsalService {

    private final CorresponsalRepository repository;

    public Page<Corresponsal> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<Corresponsal> findActive(Pageable pageable) {
        return repository.findByStatusTrue(pageable);
    }

    public List<Corresponsal> findBySurnames(String surnames) {
        return repository.findBySurnamesContainingIgnoreCase(surnames);
    }

    public List<Corresponsal> findByDateRange(LocalDateTime start, LocalDateTime end) {
        return repository.findByRegisterDayBetween(start, end);
    }

    public Corresponsal findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Corresponsal no encontrado: " + id));
    }

    public Corresponsal create(Corresponsal c) {
        if (repository.existsByIdDoc(c.getIdDoc())) {
            throw new RuntimeException("Ya existe un corresponsal con documento: " + c.getIdDoc());
        }
        return repository.save(c);
    }

    public Corresponsal update(Long id, Corresponsal cambios) {
        Corresponsal existente = findById(id);

        if (!existente.getIdDoc().equals(cambios.getIdDoc()) && 
            repository.existsByIdDoc(cambios.getIdDoc())) {
            throw new RuntimeException("El documento ya está registrado");
        }

        existente.setName(cambios.getName());
        existente.setSurnames(cambios.getSurnames());
        existente.setIdDoc(cambios.getIdDoc());
        existente.setCountry(cambios.getCountry());
        existente.setDepartment(cambios.getDepartment());
        existente.setProvince(cambios.getProvince());
        existente.setDistrict(cambios.getDistrict());
        existente.setLocality(cambios.getLocality());
        existente.setStatus(cambios.getStatus());

        return repository.save(existente);
    }

    public void desactivar(Long id) {
        Corresponsal c = findById(id);
        c.setStatus(false);
        repository.save(c);
    }

    public Corresponsal activar(Long id) {
        Corresponsal c = findById(id);
        c.setStatus(true);
        return repository.save(c);
    }

    public Page<Corresponsal> search(String surnames, String idDoc, Boolean status,
                                     LocalDateTime from, LocalDateTime to, Pageable pageable) {
        return repository.search(surnames, idDoc, status, from, to, pageable);
    }
}