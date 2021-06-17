package com.codelious.microservicios.app.cursos.microservicioscursos.services;

import com.codelious.microservicios.app.cursos.microservicioscursos.models.entity.Curso;
import com.codelious.microservicios.commons.commonsmicroservicios.services.CommonService;

public interface CursoService extends CommonService<Curso> {
    
    public Curso findCursoByAlumnoId(Long id);

    public Iterable<Long> findExamenesWithRespuestasByAlumno(Long alumnoId);

}
