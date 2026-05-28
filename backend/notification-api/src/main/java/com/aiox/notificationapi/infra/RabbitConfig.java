package com.aiox.notificationapi.infra;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
  @Bean
  TopicExchange userExchange(@Value("${app.events.user-exchange}") String exchange) {
    return new TopicExchange(exchange, true, false);
  }

  @Bean
  Queue userCreatedQueue(@Value("${app.events.user-created-queue}") String queue) {
    return new Queue(queue, true);
  }

  @Bean
  Binding userCreatedBinding(Queue userCreatedQueue, TopicExchange userExchange,
      @Value("${app.events.user-created-routing-key}") String routingKey) {
    return BindingBuilder.bind(userCreatedQueue).to(userExchange).with(routingKey);
  }

  @Bean
  Jackson2JsonMessageConverter jsonMessageConverter() {
    return new Jackson2JsonMessageConverter();
  }

  @Bean
  SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory, Jackson2JsonMessageConverter converter) {
    SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
    factory.setConnectionFactory(connectionFactory);
    factory.setMessageConverter(converter);
    return factory;
  }
}
