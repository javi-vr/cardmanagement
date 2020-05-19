package com.cardmanagement.jsm.hr;

import com.model.Card;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.JMSContext;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class CardApp {

	public static void main(String[] args) throws NamingException {
		InitialContext initialContext = new InitialContext();

		Topic topic = (Topic) initialContext.lookup("topic/cardTopic");

		try(ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();

			JMSContext jmsContext = cf.createContext()) {

			Card card = new Card();
			card.setCardNumber(1);
			card.setFirstName("");
			card.setLastName("");
			card.setPhone("");
			card.setEmail("");

			for (int i=1; i <= 10; i++) {
				jmsContext.createProducer().send(topic, card);
			}

			System.out.println("Message Sent.");
		}
	}
}
