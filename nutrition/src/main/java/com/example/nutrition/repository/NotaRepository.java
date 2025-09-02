package com.example.nutrition.repository;

import com.example.nutrition.model.Nota;
import com.example.nutrition.model.Nutricionista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotaRepository extends JpaRepository<Nota, Long> {
    
    // Consulta básica - puede causar problema N+1
    List<Nota> findByPacienteId(Long pacienteId);
    
    // Consulta optimizada con JOIN FETCH para evitar N+1
    @Query("SELECT n FROM Nota n " +
           "LEFT JOIN FETCH n.paciente " +
           "LEFT JOIN FETCH n.nutricionista " +
           "WHERE n.paciente.id = :pacienteId")
    List<Nota> findByPacienteIdWithJoinFetch(@Param("pacienteId") Long pacienteId);
    
    // Consulta con EntityGraph para cargar relaciones específicas
    @EntityGraph(attributePaths = {"paciente", "nutricionista"})
    @Query("SELECT n FROM Nota n WHERE n.paciente.id = :pacienteId")
    List<Nota> findByPacienteIdWithEntityGraph(@Param("pacienteId") Long pacienteId);
    
    // Consulta para obtener notas de un nutricionista con optimización
    @Query("SELECT n FROM Nota n " +
           "LEFT JOIN FETCH n.paciente " +
           "WHERE n.nutricionista.id = :nutricionistaId")
    List<Nota> findByNutricionistaIdOptimized(@Param("nutricionistaId") Long nutricionistaId);
}