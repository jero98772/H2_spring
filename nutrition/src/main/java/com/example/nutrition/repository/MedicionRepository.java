package com.example.nutrition.repository;

import com.example.nutrition.model.Medicion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MedicionRepository extends JpaRepository<Medicion, Long> {
    
    // Buscar mediciones por paciente ordenadas por fecha descendente
    List<Medicion> findByPacienteIdOrderByFechaDesc(Long pacienteId);
    
    // Obtener la última medición de un paciente
    Optional<Medicion> findFirstByPacienteIdOrderByFechaDesc(Long pacienteId);
    
    // Buscar mediciones por nutricionista
    List<Medicion> findByNutricionistaIdOrderByFechaDesc(Long nutricionistaId);
    
    // Buscar mediciones en un rango de fechas
    @Query("SELECT m FROM Medicion m WHERE m.paciente.id = :pacienteId " +
           "AND m.fecha BETWEEN :fechaInicio AND :fechaFin " +
           "ORDER BY m.fecha DESC")
    List<Medicion> findByPacienteIdAndFechaBetween(
            @Param("pacienteId") Long pacienteId,
            @Param("fechaInicio") LocalDate fechaInicio,
            @Param("fechaFin") LocalDate fechaFin);
    
    // Obtener estadísticas de peso por paciente
    @Query("SELECT AVG(m.peso) FROM Medicion m WHERE m.paciente.id = :pacienteId")
    Double findAverageWeightByPacienteId(@Param("pacienteId") Long pacienteId);
    
    // Contar mediciones por paciente
    Long countByPacienteId(Long pacienteId);
}