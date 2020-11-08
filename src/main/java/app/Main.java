package app;

import app.exception.AppException;
import app.jms.ActiveMQ;
import app.model.Bid;
import app.parser.ParserUtils;

import javax.jms.JMSException;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

	public static void main(String[] args) {

		ActiveMQ activeMQ = new ActiveMQ();
		activeMQ.start();

		Executors.newScheduledThreadPool(1).scheduleAtFixedRate(() -> {

			List<Bid> bids = ParserUtils.parse();

			bids.forEach(bid -> {
				thread(() -> {
					try {
						activeMQ.queueBid(bid);
					} catch (JMSException e) {
						throw new AppException("Exception occurred for " + bid.getId(), e);
					}
				});
			});

		}, 0, 5, TimeUnit.SECONDS);
	}

	public static void thread(Runnable runnable) {

		Thread brokerThread = new Thread(runnable);
		brokerThread.setDaemon(false);
		brokerThread.start();
	}
}
