package com.vr.autorizador.service.cartao;

import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.vr.autorizador.controller.cartao.exception.CartaoInvalidoException;
import com.vr.autorizador.controller.cartao.exception.CartaoJaExisteException;
import com.vr.autorizador.domain.cartao.Cartao;
import com.vr.autorizador.dto.cartao.CartaoNovo;
import com.vr.autorizador.repository.cartao.CartaoRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class CartaoService {

	@Autowired
	CartaoRepository cartaoRepository;

	public CartaoNovo criar(CartaoNovo cartaoNovo) {

		validarNumeroCartao(cartaoNovo.getNumeroCartao());
		validarCartaoJaExiste(cartaoNovo);

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

	private void validarCartaoJaExiste(CartaoNovo cartaoNovoDto) {
		Cartao cartao = cartaoRepository.findByNumeroCartao(cartaoNovoDto.getNumeroCartao());
		if (cartao != null) {
			throw new CartaoJaExisteException(cartao.getNumeroCartao(), cartao.getSenha());
		}
	}

}
