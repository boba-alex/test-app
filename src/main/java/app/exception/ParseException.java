package app.exception;

public class ParseException extends AppException{

	public ParseException(String message) {

		super(message);
	}

	public ParseException(String message, Throwable cause) {

		super(message, cause);
	}
}
