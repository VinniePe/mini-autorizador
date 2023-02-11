package com.vr.autorizador.controller.transacao;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(enumAsRef = true)
public enum TransacaoEnum {
	
	OK,
    SALDO_INSUFICIENTE,
    SENHA_INVALIDA,
    CARTAO_INEXISTENTE
    
}
