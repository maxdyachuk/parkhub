package ua.com.parkhub.exceptions;

import org.springframework.validation.ObjectError;

import java.util.List;

public class BookingException extends ParkHubException {
    List<ObjectError> errors;

    public static BookingException createWith(List<ObjectError> errors) {
        return new BookingException(errors);
    }

    private BookingException(List<ObjectError> errors) {
        this.errors = errors;
    }

    public List<ObjectError> getErrors() {
        return errors;
public class BookingException extends ParkHubException {

    private StatusCode statusCode;

    public BookingException(){ super();}

    public BookingException(String message) {
        super(message);
    }

    public BookingException(StatusCode statusCode){
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode.getCode();
    }
}
