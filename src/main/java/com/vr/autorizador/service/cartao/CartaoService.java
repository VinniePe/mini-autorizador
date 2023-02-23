package com.vr.autorizador.service.cartao;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.vr.autorizador.controller.cartao.exception.CartaoInvalidoException;
import com.vr.autorizador.controller.cartao.exception.CartaoJaExisteException;
import com.vr.autorizador.domain.cartao.Cartao;
import com.vr.autorizador.domain.log.LogFila;
import com.vr.autorizador.dto.cartao.CartaoNovo;
import com.vr.autorizador.queue.Producer;
import com.vr.autorizador.repository.cartao.CartaoRepository;
import com.vr.autorizador.repository.log.LogFilaRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class CartaoService {

	@Autowired
	CartaoRepository cartaoRepository;
	
	@Autowired
	private LogFilaRepository logFilaRepository;
	
	@Autowired
	private Producer producer;

	public CartaoNovo criar(CartaoNovo cartaoNovo) {

		validarNumeroCartao(cartaoNovo.getNumeroCartao());
		validarCartaoJaExiste(cartaoNovo.getNumeroCartao());

		Cartao cartao = new Cartao(cartaoNovo);
		cartaoRepository.saveAndFlush(cartao);

		return cartaoNovo;
	}

	public BigDecimal saldoCartao(String numeroCartao) {
		Cartao cartao = cartaoRepository.findByNumeroCartao(numeroCartao);
		if (cartao == null) {
			throw new CartaoInvalidoException();
		}
		return cartao.getSaldo();
	}

	private void validarNumeroCartao(String numeroCartao) {
		if (!numeroCartao.matches("^\\d+$"))
			throw new CartaoInvalidoException();
	}

	private void validarCartaoJaExiste(String numeroCartao) {
		Cartao cartao = cartaoRepository.findByNumeroCartao(numeroCartao);
		if (cartao != null) {
			throw new CartaoJaExisteException(cartao.getNumeroCartao(), cartao.getSenha());
		}
	}
	
	public List<CartaoNovo> criar(List<CartaoNovo> cartaoNovo) {
		cartaoNovo.stream()
		.forEach(cartao -> {
			try {
				producer.send(cartao);
			} catch (Exception e) {
				logFilaRepository.save(new LogFila("Erro ao enviar cartão <" + cartao + ">", e.getLocalizedMessage()));
				e.printStackTrace();
			}
		});
		return cartaoNovo;
	}

}
