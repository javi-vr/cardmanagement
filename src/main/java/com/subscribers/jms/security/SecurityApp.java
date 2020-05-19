package com.subscribers.jms.security;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.model.Card;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class SecurityApp {

	public static void main(String[] args) throws NamingException, JMSException, InterruptedException {
		InitialContext initialContext = new InitialContext();

		Topic topic = (Topic) initialContext.lookup("topic/cardTopic");

		try(ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
			JMSContext jmsContext = cf.createContext()) {
			
			JMSConsumer consumer1 = jmsContext.createSharedConsumer(topic, "sharedConsumer"); 
			JMSConsumer consumer2 = jmsContext.createSharedConsumer(topic, "sharedConsumer"); 
			
			for (int i=1; i <= 10; i += 2) {

				Message message1 = consumer1.receive();
				Card card1 = message1.getBody(Card.class);
				System.out.println("SecurityApp: Consumer1: CardNumber: " +  card1.getCardNumber());

				Message message2 = consumer2.receive();
				Card card2 = message2.getBody(Card.class);
				System.out.println("SecurityApp: Consumer2: CardNumber: " +  card2.getCardNumber());
			}
		}
	}
}