package com.codelious.microservicios.app.cursos.microservicioscursos.services;

import com.codelious.microservicios.app.cursos.microservicioscursos.clients.RespuestaFeignClient;
import com.codelious.microservicios.app.cursos.microservicioscursos.models.entity.Curso;
import com.codelious.microservicios.app.cursos.microservicioscursos.models.repository.CursoRepository;
import com.codelious.microservicios.commons.commonsmicroservicios.services.CommonServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CursoServiceImpl extends CommonServiceImpl<Curso, CursoRepository> implements CursoService {

    @Autowired
    private RespuestaFeignClient client;

    @Override
    @Transactional(readOnly = true)
    public Curso findCursoByAlumnoId(Long id) {
        return repository.findCursoByAlumnoId(id);
    }

    @Override
    public Iterable<Long> findExamenesWithRespuestasByAlumno(Long alumnoId) {
        return client.findExamenesWithRespuestasByAlumno(alumnoId);
    }
    
}
