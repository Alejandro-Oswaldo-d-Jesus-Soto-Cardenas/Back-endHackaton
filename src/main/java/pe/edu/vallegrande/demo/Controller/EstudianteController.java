package pe.edu.vallegrande.demo.Controller;

// 1. IMPORTS DE SWAGGER (Necesarios para que funcione)
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import pe.edu.vallegrande.demo.Model.*;
import pe.edu.vallegrande.demo.Dto.*;
import pe.edu.vallegrande.demo.Service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/estudiantes")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
// 2. TÍTULO GENERAL DEL CONTROLADOR EN SWAGGER
@Tag(name = "Controlador de Estudiantes", description = "Operaciones de registro, listado y eliminación de estudiantes")
public class EstudianteController {

    private final EstudianteService service;
    private final UbicacionService ubicacionService;

    @GetMapping
    @Operation(summary = "Listar todos los estudiantes", description = "Obtiene la lista completa de estudiantes activos en el sistema")
    public ResponseEntity<List<EstudianteResponse>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @PostMapping
    @Operation(summary = "Crear nuevo estudiante", description = "Registra un estudiante enviando sus datos en el cuerpo de la petición")
    public ResponseEntity<Estudiante> crear(@Valid @RequestBody EstudianteRequest request) {
        return new ResponseEntity<>(service.crear(request), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar estudiante", description = "Realiza una eliminación lógica del estudiante por su ID")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminarLogico(id);
        return ResponseEntity.noContent().build();
    }

    // --- ENDPOINTS DE UBICACIÓN (Helpers para Selectores/Combobox) ---

    @GetMapping("/departamentos")
    @Operation(summary = "Listar Departamentos", description = "Obtiene la lista de departamentos para los selectores de ubicación")
    public List<Departamento> departamentos() { 
        return ubicacionService.listarDepartamentos(); 
    }

    @GetMapping("/provincias/{depId}")
    @Operation(summary = "Listar Provincias", description = "Filtra provincias según el ID del departamento seleccionado")
    public List<Provincia> provincias(@PathVariable String depId) { 
        return ubicacionService.listarProvincias(depId); 
    }

    @GetMapping("/distritos/{provId}")
    @Operation(summary = "Listar Distritos", description = "Filtra distritos según el ID de la provincia seleccionada")
    public List<Distrito> distritos(@PathVariable String provId) { 
        return ubicacionService.listarDistritos(provId); 
    }

    @GetMapping("/programas")
    @Operation(summary = "Listar Programas de Estudio", description = "Obtiene la lista de carreras o programas disponibles")
    public List<Programa> programas() { 
        return ubicacionService.listarProgramas(); 
    }
}