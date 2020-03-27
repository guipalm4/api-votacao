package com.guiPalma.apivotacao;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import static java.lang.System.getenv;
import com.guiPalma.apivotacao.endpoint.service.MonitoramentoService;
import com.guiPalma.apivotacao.thread.MonitoramentoVotacaoThread;

@SpringBootApplication
@EnableAsync
public class ApiVotacaoApplication  implements ApplicationRunner {
	
	public static final String EXCHANGE_NAME = "appExchange";
    public static final String QUEUE_GENERIC_NAME = "appGenericQueue";
    public static final String QUEUE_SPECIFIC_NAME = "appResultadoQueue";
    public static final String ROUTING_KEY = "messages.key";
    public static final String CLOUDAMQP_URL = "amqp://ydvmaaah:yyZL5QU8DjBb8nV_QLAuZo3ih6EtyiF3@orangutan.rmq.cloudamqp.com/ydvmaaah";	
	
    @Autowired
	MonitoramentoService monitoramentoService;
	
	public static void main(String[] args) {		
		SpringApplication.run(ApiVotacaoApplication.class, args);
	}
	@Bean
	 public ConnectionFactory connectionFactory() {
	     final URI rabbitMqUrl;
	     try {
	         rabbitMqUrl = new URI(CLOUDAMQP_URL);
	     } catch (URISyntaxException e) {
	         throw new RuntimeException(e);
	     }

	     final CachingConnectionFactory factory = new CachingConnectionFactory();
	     factory.setUsername(rabbitMqUrl.getUserInfo().split(":")[0]);
	     factory.setPassword(rabbitMqUrl.getUserInfo().split(":")[1]);
	     factory.setHost(rabbitMqUrl.getHost());
	     factory.setPort(rabbitMqUrl.getPort());
	     factory.setVirtualHost(rabbitMqUrl.getPath().substring(1));

	     return factory;
	 }
	
	@Bean
    public TopicExchange appExchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue appQueueGeneric() {
        return new Queue(QUEUE_GENERIC_NAME);
    }

    @Bean
    public Queue appQueueSpecific() {
        return new Queue(QUEUE_SPECIFIC_NAME);
    }

    @Bean
    public Binding declareBindingGeneric() {
        return BindingBuilder.bind(appQueueGeneric()).to(appExchange()).with(ROUTING_KEY);
    }

    @Bean
    public Binding declareBindingSpecific() {
        return BindingBuilder.bind(appQueueSpecific()).to(appExchange()).with(ROUTING_KEY);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final var rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
    
    private static String getEnvOrThrow(String name) {
        final String env = getenv(name);
        if (env == null) {
            throw new IllegalStateException("Environment variable [" + name + "] is not set.");
        }
        return env;
    }

	@Override
	public void run(ApplicationArguments args) throws Exception {		
		ExecutorService executor = Executors.newFixedThreadPool(1);
        executor.submit(new MonitoramentoVotacaoThread(monitoramentoService));
		
	}		

}
