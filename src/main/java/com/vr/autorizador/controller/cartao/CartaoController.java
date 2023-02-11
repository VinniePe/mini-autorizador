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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@Tag(name = "Cartão", description = "Operações de cartões")
@RequestMapping(value = "/cartoes")
public class CartaoController {

	@Autowired
	private CartaoService cartaoService;

	@Operation(summary = "Cria um novo cartão")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CartaoNovo.class))),
			@ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CartaoNovo.class))), })
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

	@Operation(summary = "Consulta saldo do cartão")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BigDecimal.class))),
			@ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(hidden = true))),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(hidden = true))) })
	@GetMapping(value = "/{numeroCartao}")
	public ResponseEntity<BigDecimal> saldoCartao(
			@Schema(description = "Número do cartão", example = "4000000000000001") @PathVariable String numeroCartao) {
		try {
			BigDecimal response = cartaoService.saldoCartao(numeroCartao);
			return new ResponseEntity<>(response, OK);
		} catch (CartaoInvalidoException e) {
			return new ResponseEntity<>(null, NOT_FOUND);
		}
	}

}
