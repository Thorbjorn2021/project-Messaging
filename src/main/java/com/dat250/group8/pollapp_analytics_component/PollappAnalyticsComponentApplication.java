package com.dat250.group8.pollapp_analytics_component;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;

@SpringBootApplication
public class PollappAnalyticsComponentApplication {

	public static void main(String[] args) {
		SpringApplication.run(PollappAnalyticsComponentApplication.class, args)//.close();
		;
	}

	static final String topicExchangeName = "vote-events-exchange";

	static final String queueName = "pollapp-analytics-queue";

	static final String routingKey = "vote.event";

	@Bean
	Queue queue() {
		return new Queue(queueName, false);
	}

	@Bean
	TopicExchange exchange() {
		return new TopicExchange(topicExchangeName);
	}

	@Bean
	public MessageConverter messageConverter() {
		return new SimpleMessageConverter();
	}

	@Bean
	public Jackson2JsonMessageConverter jsonMessageConverter() {
		
		return new Jackson2JsonMessageConverter();
	}

	@Bean
	Binding binding(Queue queue, TopicExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(routingKey);
	}

	@Bean
	SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
											 MessageListenerAdapter listenerAdapter) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(queueName);
		container.setMessageListener(listenerAdapter);
		return container;
	}

	@Bean
	MessageListenerAdapter listenerAdapter(Receiver receiver, Jackson2JsonMessageConverter messageConverter) {
		MessageListenerAdapter adapter = new MessageListenerAdapter(receiver, "receiveMessage");
		adapter.setMessageConverter(messageConverter);
		return adapter;

	}



}
