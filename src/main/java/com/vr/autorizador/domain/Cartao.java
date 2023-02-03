package com.vr.autorizador.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import com.vr.autorizador.dto.cartao.CartaoNovo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

@Entity
public class Cartao {

	@Id
	@Column(unique = true)
	private String numeroCartao;

	@NotNull
	private String senha;

	@NotNull
	private BigDecimal saldo;

	public Cartao() {
	}

	public Cartao(CartaoNovo cartaoNovoDto) {
		this.numeroCartao = cartaoNovoDto.getNumeroCartao();
		this.senha = cartaoNovoDto.getSenha();
		this.saldo = BigDecimal.valueOf(500.00).setScale(2, RoundingMode.DOWN);
	}

	public Cartao(String numeroCartao, String senha, BigDecimal saldo) {
		this.numeroCartao = numeroCartao;
		this.senha = senha;
		this.saldo = saldo;
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

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	@Override
	public String toString() {
		return "Cartao [numeroCartao=" + numeroCartao + ", senha=" + senha + ", saldo=" + saldo + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((numeroCartao == null) ? 0 : numeroCartao.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cartao other = (Cartao) obj;
		if (numeroCartao == null) {
			if (other.numeroCartao != null)
				return false;
		} else if (!numeroCartao.equals(other.numeroCartao))
			return false;
		return true;
	}

}