package com.example.nutrition.service;

import com.example.nutrition.model.Nota;
import com.example.nutrition.repository.NotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class NotaService {

    @Autowired
    private NotaRepository notaRepository;

    /**
     * Método que puede causar problema N+1
     * Cada vez que accedas a nota.getPaciente() o nota.getNutricionista()
     * se ejecutará una consulta adicional
     */
    public void demonstrateN1Problem(Long pacienteId) {
        System.out.println("=== DEMOSTRANDO PROBLEMA N+1 ===");
        
        List<Nota> notas = notaRepository.findByPacienteId(pacienteId);
        
        // Esto causará consultas adicionales para cada nota
        for (Nota nota : notas) {
            System.out.println("Nota: " + nota.getTitulo());
            System.out.println("Paciente: " + nota.getPaciente().getNombre()); // Query adicional
            System.out.println("Nutricionista: " + nota.getNutricionista().getNombre()); // Query adicional
        }
    }

    /**
     * Método optimizado usando JOIN FETCH
     * Solo se ejecuta una consulta para cargar todo
     */
    public void demonstrateOptimizedQuery(Long pacienteId) {
        System.out.println("=== CONSULTA OPTIMIZADA CON JOIN FETCH ===");
        
        List<Nota> notas = notaRepository.findByPacienteIdWithJoinFetch(pacienteId);
        
        // No hay consultas adicionales
        for (Nota nota : notas) {
            System.out.println("Nota: " + nota.getTitulo());
            System.out.println("Paciente: " + nota.getPaciente().getNombre());
            System.out.println("Nutricionista: " + nota.getNutricionista().getNombre());
        }
    }

    /**
     * Método usando EntityGraph
     */
    public void demonstrateEntityGraph(Long pacienteId) {
        System.out.println("=== CONSULTA CON ENTITY GRAPH ===");
        
        List<Nota> notas = notaRepository.findByPacienteIdWithEntityGraph(pacienteId);
        
        for (Nota nota : notas) {
            System.out.println("Nota: " + nota.getTitulo());
            System.out.println("Paciente: " + nota.getPaciente().getNombre());
            System.out.println("Nutricionista: " + nota.getNutricionista().getNombre());
        }
    }
}