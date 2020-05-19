package com.subscribers.jms.alerts;

import com.model.Card;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class AlertApp {

	public static void main(String[] args) throws NamingException, JMSException, InterruptedException {
		InitialContext initialContext = new InitialContext();

		Topic topic = (Topic) initialContext.lookup("topic/cardTopic");

		try(ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
			JMSContext jmsContext = cf.createContext()) {

			jmsContext.setClientID("securityApp");
			JMSConsumer durableConsumer = jmsContext.createDurableConsumer(topic, "subscription1");

			durableConsumer.close();

			Thread.sleep(5000);

			durableConsumer = jmsContext.createDurableConsumer(topic, "subscription1");
			Message message = durableConsumer.receive();

			Card card = message.getBody(Card.class);
			System.out.println("AlertsApp CardId: " + card.getCardNumber());

			durableConsumer.close();
			jmsContext.unsubscribe("subscription1");

		}
	}
}