package app.logger;

import app.model.Bid;

import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.logging.Logger;

public class LogUtils {

	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");

	public static void log(Logger logger, Bid bid) {

		String log = "id=" + bid.getId() + ", timestamp=" + DATE_FORMAT.format(bid.getTimestamp()) + ", name=" + bid.getName() + ", payload=" + new String(
				Base64.getDecoder().decode(bid.getPayload()));
		logger.info(log);
	}
}
