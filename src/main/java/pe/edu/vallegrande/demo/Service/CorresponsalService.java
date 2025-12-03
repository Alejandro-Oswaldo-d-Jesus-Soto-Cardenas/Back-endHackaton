package pe.edu.vallegrande.demo.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.vallegrande.demo.Model.Corresponsal;
import pe.edu.vallegrande.demo.Repository.CorresponsalRepository;

import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CorresponsalService {

    private final CorresponsalRepository repository;

    public List<Corresponsal> findAll() {
        return repository.findAll();
    }

    public List<Corresponsal> findActive() {
        return repository.findByStatusTrue();
    }

    public Optional<Corresponsal> findById(Long id) {
        return repository.findById(id);
    }

    public Optional<Corresponsal> findByIdDoc(String idDoc) {
        return repository.findByIdDoc(idDoc);
    }

    public List<Corresponsal> findBySurnames(String surnames) {
        return repository.findBySurnamesContainingIgnoreCase(surnames);
    }

    public List<Corresponsal> findByRegisterRange(LocalDateTime start, LocalDateTime end) {
        return repository.findByRegisterDayBetween(start, end);
    }

    public Corresponsal create(Corresponsal nuevo) {
        if (repository.existsByIdDoc(nuevo.getIdDoc())) {
            throw new IllegalArgumentException("Ya existe un corresponsal con ese id_doc");
        }
        return repository.save(nuevo);
    }

    public Corresponsal update(Long id, Corresponsal cambios) {
        Corresponsal existente = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Corresponsal no encontrado: " + id));

        existente.setIdDoc(cambios.getIdDoc());
        existente.setSurnames(cambios.getSurnames());
        existente.setName(cambios.getName());
        existente.setCountry(cambios.getCountry());
        existente.setDepartment(cambios.getDepartment());
        existente.setProvince(cambios.getProvince());
        existente.setDistrict(cambios.getDistrict());
        existente.setLocality(cambios.getLocality());
        existente.setStatus(cambios.getStatus());

        return repository.save(existente);
    }

    public void deleteLogical(Long id) {
        Corresponsal c = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Corresponsal no encontrado: " + id));
        c.setStatus(false);
        repository.save(c);
    }

    public Corresponsal activate(Long id) {
        Corresponsal c = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Corresponsal no encontrado: " + id));
        c.setStatus(true);
        return repository.save(c);
    }
}