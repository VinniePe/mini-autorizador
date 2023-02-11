package com.vr.autorizador.dto.transacao;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TransacaoNova {

	@NotNull
	@NotBlank
	@Schema(description = "Número do cartão", example = "4000000000000001")
	private String numeroCartao;

	@NotNull
	@NotBlank
	@Schema(description = "Senha do cartão", example = "1234")
	private String senhaCartao;

	@NotNull
	@Schema(description = "Valor da transação", example = "50.00")
	private BigDecimal valor;

	public TransacaoNova() {
	}

	public TransacaoNova(String numeroCartao, String senhaCarto, BigDecimal valor) {
		this.numeroCartao = numeroCartao;
		this.senhaCartao = senhaCarto;
		this.valor = valor;
	}

	public String getNumeroCartao() {
		return numeroCartao;
	}

	public void setNumeroCartao(String numeroCartao) {
		this.numeroCartao = numeroCartao;
	}

	public String getSenhaCartao() {
		return senhaCartao;
	}

	public void setSenhaCartao(String senhaCartao) {
		this.senhaCartao = senhaCartao;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

}
