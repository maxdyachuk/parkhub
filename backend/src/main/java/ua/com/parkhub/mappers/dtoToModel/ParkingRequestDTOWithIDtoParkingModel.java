package ua.com.parkhub.mappers.dtoToModel;

import org.springframework.stereotype.Component;
import ua.com.parkhub.dto.ParkingRequestDTO;
import ua.com.parkhub.mappers.Mapper;
import ua.com.parkhub.model.AddressModel;
import ua.com.parkhub.model.ParkingModel;

@Component
public class ParkingRequestDTOWithIDtoParkingModel implements Mapper<ParkingRequestDTO, ParkingModel> {
    @Override
    public ParkingModel transform(ParkingRequestDTO from) {
        if(from == null) {
            return null;
        }
        ParkingModel parkingModel = new ParkingModel();
        parkingModel.setParkingName(from.getParkingName());
        parkingModel.setSlotsNumber(from.getSlotsNumber());
        parkingModel.setTariff(from.getTariff());
        AddressModel addressModel = new AddressModel();
        addressModel.setCity(from.getCity());
        addressModel.setStreet(from.getStreet());
        addressModel.setBuilding(from.getBuilding());
        parkingModel.setAddressModel(addressModel);
        return parkingModel;
    }

}