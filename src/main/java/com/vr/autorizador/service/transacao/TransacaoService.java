package com.vr.autorizador.service.transacao;

import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.vr.autorizador.controller.cartao.exception.CartaoInvalidoException;
import com.vr.autorizador.controller.transacao.exception.SaldoInsuficienteException;
import com.vr.autorizador.controller.transacao.exception.SenhaInvalidaException;
import com.vr.autorizador.domain.cartao.Cartao;
import com.vr.autorizador.domain.transacao.Transacao;
import com.vr.autorizador.dto.transacao.TransacaoNova;
import com.vr.autorizador.repository.cartao.CartaoRepository;
import com.vr.autorizador.repository.transacao.TransacaoRepository;
import jakarta.transaction.Transactional;
import static com.vr.autorizador.controller.transacao.TransacaoEnum.OK;
import static com.vr.autorizador.controller.transacao.TransacaoEnum.SALDO_INSUFICIENTE;
import static com.vr.autorizador.controller.transacao.TransacaoEnum.SENHA_INVALIDA;
import static com.vr.autorizador.controller.transacao.TransacaoEnum.CARTAO_INEXISTENTE;

@Service
@Transactional(Transactional.TxType.SUPPORTS)
public class TransacaoService {

	@Autowired
	private TransacaoRepository transacaoRepository;
	
	@Autowired
	private CartaoRepository cartaoRepository;

	public void transacao(TransacaoNova transacaoNova) {
		Cartao cartao = cartaoRepository.findByNumeroCartao(transacaoNova.getNumeroCartao());

		validarCartao(cartao, transacaoNova);
		validarSenhaCartao(cartao.getSenha(), transacaoNova);
		validarSaldoCartao(cartao.getSaldo(), transacaoNova);
		debitarSaldoCartao(cartao, transacaoNova);
	}
	
	@Transactional(dontRollbackOn = CartaoInvalidoException.class)
	private void validarCartao(Cartao cartao, TransacaoNova transacaoNova) {
		if (cartao == null) {
			transacaoRepository.save(new Transacao(transacaoNova, CARTAO_INEXISTENTE));
			throw new CartaoInvalidoException();
		}
	}

	@Transactional(dontRollbackOn = SenhaInvalidaException.class)
	private void validarSenhaCartao(String senhaCartao, TransacaoNova transacaoNova) {
		if (!senhaCartao.equals(transacaoNova.getSenhaCartao())) {
			transacaoRepository.save(new Transacao(transacaoNova, SENHA_INVALIDA));
			throw new SenhaInvalidaException();
		}
	}

	@Transactional(dontRollbackOn = SaldoInsuficienteException.class)
	private void validarSaldoCartao(BigDecimal saldoCartao, TransacaoNova transacaoNova) {
		if ((saldoCartao.subtract(transacaoNova.getValor())).signum() == -1) {
			transacaoRepository.save(new Transacao(transacaoNova, SALDO_INSUFICIENTE));		
			throw new SaldoInsuficienteException();
		}
	}

	@Transactional 
	private void debitarSaldoCartao(Cartao cartao, TransacaoNova transacaoNova) {
		synchronized (this) {
			cartao.debitar(transacaoNova.getValor());
			cartaoRepository.save(cartao);
			transacaoRepository.save(new Transacao(transacaoNova, OK));
		}
	}

}
