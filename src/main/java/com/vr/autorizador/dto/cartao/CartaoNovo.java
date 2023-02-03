package com.vr.autorizador.dto.cartao;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CartaoNovo {

	@NotNull
	@Size(min = 16, message = "O cartão deve ter 16 números")
	@Size(max = 16, message = "O cartão deve ter 16 números")
	private String numeroCartao;

	@NotNull
	@Size(min = 4, message = "A senha deve ter 4 números")
	@Size(max = 4, message = "A senha deve ter 4 números")
	private String senha;

	public CartaoNovo() {
	}

	public CartaoNovo(String numeroCartao, String senha) {
		this.numeroCartao = numeroCartao;
		this.senha = senha;
	}

	public String getNumeroCartao() {
		return numeroCartao;
	}

	public void setNumeroCartao(String numeroCartao) {
		this.numeroCartao = numeroCartao;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

}
