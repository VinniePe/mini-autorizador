package com.vr.autorizador.repository.log;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.vr.autorizador.domain.log.LogFila;

public interface LogFilaRepository extends MongoRepository<LogFila, String> {

}
