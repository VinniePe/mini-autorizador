package com.vr.autorizador.controller.cartao;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.vr.autorizador.controller.cartao.exception.CartaoInvalidoException;
import com.vr.autorizador.controller.cartao.exception.CartaoJaExisteException;
import com.vr.autorizador.dto.cartao.CartaoNovo;
import com.vr.autorizador.service.cartao.CartaoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/cartoes")
public class CartaoController {

	@Autowired
	private CartaoService cartaoService;

	@PostMapping
	public ResponseEntity<CartaoNovo> criar(@RequestBody @Valid CartaoNovo cartaoNovo) {
		try {
			return new ResponseEntity<>(cartaoService.criar(cartaoNovo), CREATED);
		} catch (CartaoJaExisteException e) {
			return new ResponseEntity<>(cartaoNovo, UNPROCESSABLE_ENTITY);
		} catch (CartaoInvalidoException e) {
			return new ResponseEntity<>(null, UNPROCESSABLE_ENTITY);
		}
	}

	@GetMapping(value = "/{numeroCartao}")
	public ResponseEntity<BigDecimal> saldoCartao(@PathVariable String numeroCartao) {
		try {
			BigDecimal response = cartaoService.saldoCartao(numeroCartao);
			return new ResponseEntity<>(response, OK);
		} catch (CartaoInvalidoException e) {
			return new ResponseEntity<>(null, NOT_FOUND);
		}
	}

}
