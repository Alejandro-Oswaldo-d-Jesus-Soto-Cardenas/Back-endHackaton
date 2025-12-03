package pe.edu.vallegrande.demo.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.demo.Model.Corresponsal;
import pe.edu.vallegrande.demo.Service.CorresponsalService;

import java.util.List;

@RestController
@RequestMapping("/api/rest/corresponsales")
@RequiredArgsConstructor
public class CorresponsalRestController {

    private final CorresponsalService service;

    @GetMapping
    public List<Corresponsal> listarTodos() {
        return service.findAll();
    }

    @GetMapping("/activos")
    public List<Corresponsal> listarActivos() {
        return service.findActive();
    }

    @GetMapping("/{id}")
    public Corresponsal obtenerPorId(@PathVariable Long id) {
        return service.findById(id).orElse(null);
    }

    @GetMapping("/por-doc/{idDoc}")
    public Corresponsal obtenerPorIdDoc(@PathVariable String idDoc) {
        return service.findByIdDoc(idDoc).orElse(null);
    }
}