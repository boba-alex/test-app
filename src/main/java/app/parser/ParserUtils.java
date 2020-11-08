package app.parser;

import app.exception.ConvertException;
import app.exception.ParseException;
import app.model.Bid;
import app.model.BidObject;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ParserUtils {

	public static final String FILENAME = "bids.json";

	private static final ObjectMapper objectMapper = new ObjectMapper();

	public static List<Bid> parse() {

		try {
			List<BidObject> bidObjects = Arrays.asList(objectMapper.readValue(new File(FILENAME), BidObject[].class));
			return bidObjects.stream().map(BidObject::getBid).collect(Collectors.toList());
		} catch (IOException e) {
			throw new ParseException(e.getMessage());
		}
	}

	public static <T> T convertFromJSONString(String jsonString, Class<T> toClazz) {

		try {
			return objectMapper.readValue(jsonString, toClazz);
		} catch (IOException e) {
			throw new ConvertException(String.format("Failed to convert '%s' to '%s'", jsonString, toClazz), e);
		}
	}

	public static String convertToJSONString(Object obj) {

		try {
			return objectMapper.writeValueAsString(obj);
		} catch (IOException e) {
			throw new ConvertException(String.format("Failed to convert '%s' to '%s'", obj.toString(), String.class), e);
		}
	}

}
