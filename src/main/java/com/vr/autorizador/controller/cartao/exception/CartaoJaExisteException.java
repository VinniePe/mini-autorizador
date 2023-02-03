package com.vr.autorizador.controller.cartao.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
@Getter
public class CartaoJaExisteException extends RuntimeException {
	
    private final String numeroCartao;

    private final String senha;

    public CartaoJaExisteException (String numeroCartao, String senha) {
        super();
        this.numeroCartao = numeroCartao;
        this.senha = senha;
    }

}
