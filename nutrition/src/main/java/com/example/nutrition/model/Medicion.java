package com.example.nutrition.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "medicion")
public class Medicion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "peso", nullable = false)
    private Double peso; // en kg

    @Column(name = "altura", nullable = false)
    private Double altura; // en cm

    @Column(name = "circunferencia_cintura")
    private Double circunferenciaCintura; // en cm

    @Column(name = "circunferencia_cadera")
    private Double circunferenciaCadera; // en cm
    
    @Column(name = "porcentaje_grasa_corporal")
    private Double porcentajeGrasaCorporal;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "nutricionista_id", nullable = false)
    private Nutricionista nutricionista;

    // Constructores
    public Medicion() {
    }

    public Medicion(LocalDate fecha, Double peso, Double altura, Paciente paciente, Nutricionista nutricionista) {
        this.fecha = fecha;
        this.peso = peso;
        this.altura = altura;
        this.paciente = paciente;
        this.nutricionista = nutricionista;
    }

    // Método para calcular el IMC
    public Double calcularIMC() {
        if (altura == null || peso == null || altura <= 0) {
            return null;
        }
        double alturaEnMetros = altura / 100.0;
        return peso / (alturaEnMetros * alturaEnMetros);
    }

    // Método para calcular la relación cintura-cadera
    public Double calcularRelacionCinturaCadera() {
        if (circunferenciaCintura == null || circunferenciaCadera == null || circunferenciaCadera <= 0) {
            return null;
        }
        return circunferenciaCintura / circunferenciaCadera;
    }

    // Método para clasificar el IMC
    public String clasificarIMC() {
        Double imc = calcularIMC();
        if (imc == null) {
            return "No calculable";
        }
        
        if (imc < 18.5) {
            return "Bajo peso";
        } else if (imc < 25.0) {
            return "Peso normal";
        } else if (imc < 30.0) {
            return "Sobrepeso";
        } else {
            return "Obesidad";
        }
    }

    // Propiedades virtuales para JSON
    @JsonProperty("pacienteInfo")
    public Map<String, Object> getPacienteInfo() {
        if (paciente == null) {
            return null;
        }
        
        Map<String, Object> info = new HashMap<>();
        info.put("id", paciente.getId());
        info.put("nombre", paciente.getNombre());
        info.put("apellido", paciente.getApellido());
        info.put("email", paciente.getEmail());
        return info;
    }

    @JsonProperty("nutricionistaInfo")
    public Map<String, Object> getNutricionistaInfo() {
        if (nutricionista == null) {
            return null;
        }
        
        Map<String, Object> info = new HashMap<>();
        info.put("id", nutricionista.getId());
        info.put("nombre", nutricionista.getNombre());
        info.put("apellido", nutricionista.getApellido());
        info.put("numeroLicencia", nutricionista.getNumeroLicencia());
        info.put("especialidad", nutricionista.getEspecialidad());
        return info;
    }

    @JsonProperty("imcCalculado")
    public Double getImcCalculado() {
        return calcularIMC();
    }

    @JsonProperty("clasificacionIMC")
    public String getClasificacionIMC() {
        return clasificarIMC();
    }

    @JsonProperty("relacionCinturaCadera")
    public Double getRelacionCinturaCadera() {
        return calcularRelacionCinturaCadera();
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Double getAltura() {
        return altura;
    }

    public void setAltura(Double altura) {
        this.altura = altura;
    }

    public Double getCircunferenciaCintura() {
        return circunferenciaCintura;
    }

    public void setCircunferenciaCintura(Double circunferenciaCintura) {
        this.circunferenciaCintura = circunferenciaCintura;
    }

    public Double getCircunferenciaCadera() {
        return circunferenciaCadera;
    }

    public void setCircunferenciaCadera(Double circunferenciaCadera) {
        this.circunferenciaCadera = circunferenciaCadera;
    }

    public Double getPorcentajeGrasaCorporal() {
        return porcentajeGrasaCorporal;
    }

    public void setPorcentajeGrasaCorporal(Double porcentajeGrasaCorporal) {
        this.porcentajeGrasaCorporal = porcentajeGrasaCorporal;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Nutricionista getNutricionista() {
        return nutricionista;
    }

    public void setNutricionista(Nutricionista nutricionista) {
        this.nutricionista = nutricionista;
    }

    @PrePersist
    protected void onCreate() {
        if (fecha == null) {
            fecha = LocalDate.now();
        }
    }

    @Override
    public String toString() {
        return "Medicion{" +
                "id=" + id +
                ", fecha=" + fecha +
                ", peso=" + peso +
                ", altura=" + altura +
                ", IMC=" + calcularIMC() +
                ", clasificacion='" + clasificarIMC() + '\'' +
                '}';
    }
}