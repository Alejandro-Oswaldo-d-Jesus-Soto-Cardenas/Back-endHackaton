package pe.edu.vallegrande.demo.Service;

import pe.edu.vallegrande.demo.Model.*;
import pe.edu.vallegrande.demo.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UbicacionService {

    private final DepartamentoRepository depRepo;
    private final ProvinciaRepository provRepo;
    private final DistritoRepository distRepo;
    private final ProgramaRepository progRepo;

    @Transactional(readOnly = true)
    public List<Departamento> listarDepartamentos() {
        return depRepo.findAll();
    }

    @Transactional(readOnly = true)
    public List<Provincia> listarProvincias(String departamentoId) {
        return provRepo.findByDepartamentoId(departamentoId);
    }

    @Transactional(readOnly = true)
    public List<Distrito> listarDistritos(String provinciaId) {
        return distRepo.findByProvinciaId(provinciaId);
    }

    @Transactional(readOnly = true)
    public List<Programa> listarProgramas() {
        return progRepo.findByActivoTrue();
    }
}