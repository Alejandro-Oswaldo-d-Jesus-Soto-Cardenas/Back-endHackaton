package pe.edu.vallegrande.demo.Service;

import pe.edu.vallegrande.demo.Model.*;
import pe.edu.vallegrande.demo.Dto.EstudianteRequest;
import pe.edu.vallegrande.demo.Dto.EstudianteResponse;
import pe.edu.vallegrande.demo.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Importante para consistencia

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EstudianteService {

    private final EstudianteRepository estudianteRepo;
    // Estos repositorios ya no son estrictamente necesarios para listar, 
    // pero pueden servirte para validaciones futuras.
    private final ProgramaRepository programaRepo;
    private final DistritoRepository distritoRepo;

    // LISTAR: Usa la consulta optimizada del Repositorio
    @Transactional(readOnly = true) // Mejora el rendimiento de lectura
    public List<EstudianteResponse> listar() {
        // Obtenemos el arreglo de objetos [Estudiante, Programa, Distrito, Provincia, Departamento]
        List<Object[]> rawResults = estudianteRepo.findAllConDetalles();

        return rawResults.stream()
                .map(row -> {
                    // Mapeo según el orden del SELECT en el Repository
                    Estudiante e = (Estudiante) row[0];
                    Programa p = (Programa) row[1];
                    Distrito d = (Distrito) row[2];
                    Provincia prov = (Provincia) row[3];
                    Departamento dep = (Departamento) row[4];

                    // Construcción del String de ubicación
                    String distritoCompleto = dep.getNombre() + " / " 
                                            + prov.getNombre() + " / " 
                                            + d.getNombre();

                    String apellidosNombres = e.getApellidos() + " " + e.getNombres();

                    // Conversión segura de Short a Integer para el DTO
                    Integer anio = (e.getAnioIngreso() != null) 
                                   ? e.getAnioIngreso().intValue() 
                                   : 0;

                    return new EstudianteResponse(
                            e.getId(),
                            e.getNumeroDocumento(),
                            e.getApellidos(),
                            e.getNombres(),
                            apellidosNombres,
                            p.getNombre(),      // Nombre del programa
                            distritoCompleto,   // Ubicación concatenada
                            e.getCelular(),
                            e.getEmail(),
                            anio,
                            e.getEstado()
                    );
                }).collect(Collectors.toList());
    }

    // CREAR: Guarda usando los IDs directos
    @Transactional
    public Estudiante crear(EstudianteRequest request) {
        Estudiante e = Estudiante.builder()
                .numeroDocumento(request.numeroDocumento())
                .tipoDocumento(request.tipoDocumento() != null ? request.tipoDocumento() : "DNI")
                .apellidos(request.apellidos().toUpperCase()) // Guardar apellidos en mayúsculas
                .nombres(request.nombres())
                .fechaNacimiento(request.fechaNacimiento())
                .genero(request.genero())
                .celular(request.celular())
                .email(request.email())
                .direccion(request.direccion())
                .distritoId(request.distritoId()) // ID directo
                .programaId(request.programaId()) // ID directo
                // Convertimos el Integer del Request al Short de la Entidad
                .anioIngreso(request.anioIngreso() != null ? request.anioIngreso().shortValue() : null)
                .build();

        return estudianteRepo.save(e);
    }

    // ELIMINAR (Lógico)
    @Transactional
    public void eliminarLogico(Long id) {
        // Buscamos al estudiante, si no existe no hacemos nada (o podrías lanzar excepción)
        estudianteRepo.findById(id).ifPresent(e -> {
            e.setEstado(false);
            estudianteRepo.save(e);
        });
    }
}