package com.vr.autorizador.queue;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import com.vr.autorizador.controller.cartao.exception.CartaoInvalidoException;
import com.vr.autorizador.controller.cartao.exception.CartaoJaExisteException;
import com.vr.autorizador.domain.log.LogFila;
import com.vr.autorizador.dto.cartao.CartaoNovo;
import com.vr.autorizador.repository.log.LogFilaRepository;
import com.vr.autorizador.service.cartao.CartaoService;

@Component
public class Consumer {
	
	@Autowired
	private LogFilaRepository logFilaRepository;
	
	@Autowired
	private CartaoService cartaoService;
	
	@RabbitListener(queues = { "springboot.miniautorizador.queue" })
	public void receiveMessage(@Payload CartaoNovo mensagem) {

		System.out.println("Recebido via fila <" + mensagem + ">");
				
		try {
			cartaoService.criar(mensagem);			
			logFilaRepository.save(new LogFila("Cartão criado.", mensagem.toString()));
			System.out.println("Cartão criado <" + mensagem + ">");
		} catch (CartaoJaExisteException e) {
			logFilaRepository.save(new LogFila("Cartão já existe.", mensagem.toString()));
			System.out.println("Cartão já existe <" + mensagem + ">");
		} catch (CartaoInvalidoException e) {
			logFilaRepository.save(new LogFila("Cartão cartão inválido.", mensagem.toString()));
			System.out.println("Cartão inválido <" + mensagem + ">");
		}


	}
	
}
