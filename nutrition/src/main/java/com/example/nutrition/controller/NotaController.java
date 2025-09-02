
package com.example.nutrition.controller;

import com.example.nutrition.service.NotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notas")
public class NotaController {

    @Autowired
    private NotaService notaService;

    @GetMapping("/demo-n1/{pacienteId}")
    public String demonstrateN1Problem(@PathVariable Long pacienteId) {
        notaService.demonstrateN1Problem(pacienteId);
        return "Revisa la consola para ver las consultas SQL ejecutadas";
    }

    @GetMapping("/demo-optimized/{pacienteId}")
    public String demonstrateOptimizedQuery(@PathVariable Long pacienteId) {
        notaService.demonstrateOptimizedQuery(pacienteId);
        return "Revisa la consola para ver la consulta SQL optimizada";
    }

    @GetMapping("/demo-entitygraph/{pacienteId}")
    public String demonstrateEntityGraph(@PathVariable Long pacienteId) {
        notaService.demonstrateEntityGraph(pacienteId);
        return "Revisa la consola para ver la consulta con EntityGraph";
    }
}