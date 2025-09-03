package com.example.nutrition.repository;

import com.example.nutrition.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    
    // Buscar pacientes por nombre (ignorando mayúsculas/minúsculas)
    List<Paciente> findByNombreContainingIgnoreCase(String nombre);
    
    // Buscar paciente por email
    Optional<Paciente> findByEmail(String email);
    
    // Buscar pacientes por nutricionista
    @Query("SELECT p FROM Paciente p WHERE p.nutricionista.id = :nutricionistaId")
    List<Paciente> findByNutricionistaId(@Param("nutricionistaId") Long nutricionistaId);
    
    // Verificar si existe un email
    boolean existsByEmail(String email);
}