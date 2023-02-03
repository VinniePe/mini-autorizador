package com.vr.autorizador.dto.transacao;

import java.math.BigDecimal;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TransacaoNova {

	@NotNull
	@NotBlank
	private String numeroCartao;

	@NotNull
	@NotBlank
	private String senhaCartao;

	@NotNull
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
