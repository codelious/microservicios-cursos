package com.codelious.microservicios.app.cursos.microservicioscursos.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.codelious.microservicios.app.cursos.microservicioscursos.models.entity.Curso;
import com.codelious.microservicios.app.cursos.microservicioscursos.services.CursoService;
import com.codelious.microservicios.commons.alumnos.commonsalumnos.models.entity.Alumno;
import com.codelious.microservicios.commons.commonsmicroservicios.controllers.CommonController;
import com.codelious.microservicios.commons.examenes.commonsexamenes.models.entity.Examen;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CursoController extends CommonController<Curso, CursoService> {
    
    @Value("${config.balanceador.test}")
    private String balanceadorTest;

    @GetMapping("/balanceador-test")
    public ResponseEntity<?> balanceadorPrueba() {
        Map<String, Object> response = new HashMap<String, Object>();
        response.put("balanceador", balanceadorTest);
        response.put("cursos", service.findAll());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Curso curso, BindingResult result, @PathVariable Long id) {

        if (result.hasErrors()) {
            return this.validar(result);
        }

        Optional<Curso> o = service.findById(id);
        if (o.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Curso cursoDb = o.get();
        cursoDb.setNombre(curso.getNombre()); 

        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(cursoDb));
    }

    @PutMapping("/{id}/asignar-alumnos")
    public ResponseEntity<?> asignarAlumnos(@RequestBody List<Alumno> alumnos, @PathVariable Long id) {
        
        Optional<Curso> o = service.findById(id);
        if (o.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Curso cursoDb = o.get();

        alumnos.forEach(a -> {
            cursoDb.addAlumno(a);
        });

        return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(cursoDb));
    }

    @PutMapping("/{id}/eliminar-alumno")
    public ResponseEntity<?> eliminarAlumno(@RequestBody Alumno alumno, @PathVariable Long id) {
        
        Optional<Curso> o = service.findById(id);
        if (o.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Curso cursoDb = o.get();

        cursoDb.removeAlumno(alumno);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(cursoDb));
    }

    @GetMapping("/alumno/{alumnoId}")
    public ResponseEntity<?> findByAlumnoId(@PathVariable Long alumnoId) {
        Curso curso = service.findCursoByAlumnoId(alumnoId);

        if (curso != null) {
            List<Long> examenesIds = (List<Long>) service.findExamenesWithRespuestasByAlumno(alumnoId);
            List<Examen> examenes = curso.getExamenes().stream().map(examen -> {
                if (examenesIds.contains(examen.getId())) {
                    examen.setRespondido(true);
                }
                return examen;
            }).collect(Collectors.toList());

            curso.setExamenes(examenes);
        }
        return ResponseEntity.ok(curso);
    }

    @PutMapping("/{id}/asignar-examenes")
    public ResponseEntity<?> asignarExamenes(@RequestBody List<Examen> examenes, @PathVariable Long id) {
        
        Optional<Curso> o = service.findById(id);
        if (o.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Curso cursoDb = o.get();

        examenes.forEach(cursoDb::addExamen);

        return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(cursoDb));
    }

    @PutMapping("/{id}/eliminar-examen")
    public ResponseEntity<?> eliminarExamen(@RequestBody Examen examen, @PathVariable Long id) {
        
        Optional<Curso> o = service.findById(id);
        if (o.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Curso cursoDb = o.get();

        cursoDb.removeExamen(examen);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(cursoDb));
    }

}
