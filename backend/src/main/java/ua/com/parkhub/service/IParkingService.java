package ua.com.parkhub.service;

import ua.com.parkhub.model.ParkingModel;

import java.util.List;
import java.util.Optional;

public interface IParkingService {

    List<ParkingModel> findAllParking();
    ParkingModel findParkingByIdWithSlotList(long id);
    ParkingModel findParkingByIdWithSlotListAndDateRange(long id, long checkIn, long checkOut);

}
