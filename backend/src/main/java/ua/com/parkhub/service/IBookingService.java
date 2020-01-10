package ua.com.parkhub.service;

import ua.com.parkhub.model.BookingModel;
import ua.com.parkhub.model.PaymentModel;
import ua.com.parkhub.model.CustomerModel;

import java.util.Optional;

public interface IBookingService {

    PaymentModel addBooking(String phoneNumber, String carNumber, long slotId, long checkIn, long checkOut, int price);

    Optional<BookingModel> findBookingByIdAndDateTimeRange(long id, long checkIn, long checkOut);

    int findPrice(String phoneNumber);
    Optional<BookingModel> findPrepaidBooking(CustomerModel customerModel);
    BookingModel addBooking(String phoneNumber, String carNumber, long slotId);
}
