package com.vr.autorizador.controller.transacao;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.vr.autorizador.controller.cartao.exception.CartaoInvalidoException;
import com.vr.autorizador.controller.transacao.exception.SaldoInsuficienteException;
import com.vr.autorizador.controller.transacao.exception.SenhaInvalidaException;
import com.vr.autorizador.dto.transacao.TransacaoNova;
import com.vr.autorizador.service.transacao.TransacaoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/transacoes")
public class TransacaoController {

	@Autowired
	private TransacaoService transacaoService;

	@PostMapping
	public ResponseEntity<TransacaoEnum> transacao(@RequestBody @Valid TransacaoNova transacaoNova) {
		try {
			transacaoService.transacao(transacaoNova);
			return new ResponseEntity<>(TransacaoEnum.OK, CREATED);
		} catch (CartaoInvalidoException e) {
			return new ResponseEntity<>(TransacaoEnum.CARTAO_INEXISTENTE, UNPROCESSABLE_ENTITY);
		} catch (SenhaInvalidaException e) {
			return new ResponseEntity<>(TransacaoEnum.SENHA_INVALIDA, UNPROCESSABLE_ENTITY);
		} catch (SaldoInsuficienteException e) {
			return new ResponseEntity<>(TransacaoEnum.SALDO_INSUFICIENTE, UNPROCESSABLE_ENTITY);
		}
	}

}
