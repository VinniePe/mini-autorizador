package com.vr.autorizador.repository.cartao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.vr.autorizador.domain.cartao.Cartao;

public interface CartaoRepository extends JpaRepository<Cartao,String>	{

	Cartao findByNumeroCartao(String numeroCartao);
	
}
