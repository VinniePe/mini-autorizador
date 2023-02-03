package com.vr.autorizador.service.transacao;

import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.vr.autorizador.controller.cartao.exception.CartaoInvalidoException;
import com.vr.autorizador.controller.transacao.exception.SaldoInsuficienteException;
import com.vr.autorizador.controller.transacao.exception.SenhaInvalidaException;
import com.vr.autorizador.domain.Cartao;
import com.vr.autorizador.dto.transacao.TransacaoNova;
import com.vr.autorizador.repository.CartaoRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class TransacaoService {

	@Autowired
	private CartaoRepository cartaoRepository;

	public void transacao(TransacaoNova transacaoNova) {
		Cartao cartao = cartaoRepository.findByNumeroCartao(transacaoNova.getNumeroCartao());

		if (cartao == null) {
			throw new CartaoInvalidoException();
		}

		validarSenhaCartao(cartao.getSenha(), transacaoNova.getSenhaCartao());
		validarSaldoCartao(cartao.getSaldo(), transacaoNova.getValor());
		debitarSaldoCartao(cartao, transacaoNova);
	}

	private void debitarSaldoCartao(Cartao cartao, TransacaoNova transacaoNova) {
		synchronized (this) {
			cartao.setSaldo(cartao.getSaldo().subtract(transacaoNova.getValor()));
			cartaoRepository.save(cartao);
		}
	}

	private void validarSenhaCartao(String senhaCartao, String senhaTransacao) {
		if (!senhaCartao.equals(senhaTransacao))
			throw new SenhaInvalidaException();
	}

	private void validarSaldoCartao(BigDecimal saldoCartao, BigDecimal valorTransacao) {
		if ((saldoCartao.subtract(valorTransacao)).signum() == -1)
			throw new SaldoInsuficienteException();
	}

}
