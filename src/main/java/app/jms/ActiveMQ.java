package app.jms;

import app.exception.AppException;
import app.logger.LogUtils;
import app.model.Bid;
import app.parser.ParserUtils;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerFactory;
import org.apache.activemq.broker.BrokerService;

import javax.jms.*;
import java.net.URI;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

public class ActiveMQ {

	private final Logger logger = Logger.getLogger(ActiveMQ.class.getName());

	public static final String BROKER_URI = "broker:(tcp://localhost:61616)";
	public static final String CONNECTION_URI = "tcp://localhost:61616";
	private Map<String, Queue> queues;
	private Map<String, MessageProducer> producers;
	private Map<String, MessageConsumer> consumers;
	private Connection connection;
	private Session session;

	private BrokerService broker;

	public ActiveMQ() {

	}

	public void start() {

		queues = new ConcurrentHashMap<>();
		producers = new ConcurrentHashMap<>();
		consumers = new ConcurrentHashMap<>();

		try {
			BrokerService broker = BrokerFactory.createBroker(new URI(BROKER_URI));
			broker.start();
			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(CONNECTION_URI);
			connection = connectionFactory.createConnection();
			connection.start();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		} catch (Exception e) {
			throw new AppException("Can't start ActiveMQ", e);
		}
	}

	public void queueBid(Bid bid) throws JMSException {

		String queueName = bid.getName();
		Queue queue = queues.get(queueName);
		MessageProducer producer = producers.get(queueName);
		MessageConsumer consumer = consumers.get(queueName);
		if (Objects.isNull(queue)) {
			queue = session.createQueue(queueName);
			producer = session.createProducer(queue);
			consumer = session.createConsumer(queue);
			queues.put(queueName, queue);
			producers.put(queueName, producer);
			consumers.put(queueName, consumer);
		}

		Message message = session.createTextMessage(ParserUtils.convertToJSONString(bid));
		producer.send(message);

		TextMessage textMessage = (TextMessage) consumer.receive();
		LogUtils.log(logger, ParserUtils.convertFromJSONString(textMessage.getText(), Bid.class));
	}

	public void stop() {

		try {
			if (session != null) {
				session.close();
			}
			if (connection != null) {
				connection.close();
			}
			broker.stop();
		} catch (Exception e) {
			throw new AppException("Can't stop", e);
		}

	}
}
