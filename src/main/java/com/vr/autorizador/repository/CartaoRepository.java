package com.vr.autorizador.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.vr.autorizador.domain.Cartao;

public interface CartaoRepository extends JpaRepository<Cartao,String>	{

	Cartao findByNumeroCartao(String numeroCartao);
	
}
