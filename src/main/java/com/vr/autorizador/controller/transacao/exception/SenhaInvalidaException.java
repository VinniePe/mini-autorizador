package com.vr.autorizador.controller.transacao.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class SenhaInvalidaException extends RuntimeException {

    public SenhaInvalidaException() {
        super();
    }

}
