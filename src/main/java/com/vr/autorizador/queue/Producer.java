package com.vr.autorizador.queue;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.vr.autorizador.config.RabbitmqConfig;
import com.vr.autorizador.domain.log.LogFila;
import com.vr.autorizador.dto.cartao.CartaoNovo;
import com.vr.autorizador.repository.log.LogFilaRepository;

@Component
public class Producer {
	
	@Autowired
	private LogFilaRepository logFilaRepository;

	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	public void send(CartaoNovo mensagem) throws Exception {
		System.out.println("Enviando via fila <" + mensagem + ">");
		
		logFilaRepository.save(new LogFila("Enviando...", mensagem.toString()));
		
		rabbitTemplate.convertAndSend(RabbitmqConfig.QUEUE_NAME, mensagem);
	}
	
}
