package com.guiPalma.apivotacao.endpoint.mensageria;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.guiPalma.apivotacao.ApiVotacaoApplication;
import com.guiPalma.apivotacao.dto.ResultadoDto;

@Component
public class NotificadorResultado {
	
	private static final Logger log = LoggerFactory.getLogger(NotificadorResultado.class);
	
	@Autowired
    private RabbitTemplate template;

    public void send(ResultadoDto resultado){
    	 final var message = new  MensagemResultado(resultado.getSessao().getDescricao(),
    			 																						resultado.getPauta().getDescricao(),
    			 																						resultado.getSim(),
    			 																						resultado.getNao(),
    			 																						resultado.getResultado()); 
    			 
    			 
         log.info("Sending message...");
         template.convertAndSend(ApiVotacaoApplication.EXCHANGE_NAME, ApiVotacaoApplication.ROUTING_KEY,message);
    }

}
