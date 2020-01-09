package ua.com.parkhub.mappers.EntityToModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.com.parkhub.exceptions.ParkHubException;
import ua.com.parkhub.mappers.Mapper;
import ua.com.parkhub.model.ParkingModel;
import ua.com.parkhub.persistence.entities.Parking;

import java.util.stream.Collectors;

@Component
public class ParkingEntityToModelMapper implements Mapper<Parking, ParkingModel> {

    SlotEntityToModelMapper slotEntityToModelMapper;
    AddressEntityToModelMapper addressEntityToModelMapper;
    UserEntityToModelMapper userEntityToModelMapper;

    @Autowired
    public ParkingEntityToModelMapper(SlotEntityToModelMapper slotEntityToModelMapper, AddressEntityToModelMapper addressEntityToModelMapper, UserEntityToModelMapper userEntityToModelMapper) {
        this.slotEntityToModelMapper = slotEntityToModelMapper;
        this.addressEntityToModelMapper = addressEntityToModelMapper;
        this.userEntityToModelMapper = userEntityToModelMapper;
    }

    @Override
    public ParkingModel transform(Parking from) {
        if(from == null) {
           return null;
        }
        ParkingModel parkingModel = new ParkingModel();
        parkingModel.setId(from.getId());
        parkingModel.setParkingName(from.getParkingName());
        parkingModel.setAddressModel(addressEntityToModelMapper.transform(from.getAddress()));
        parkingModel.setSlotsNumber(from.getSlotsNumber());
        parkingModel.setTariff(from.getTariff());
        parkingModel.setActive(from.isActive());
        if(from.getSlots() != null){
            parkingModel.setSlots(from.getSlots().stream().map(slotEntityToModelMapper::transform).collect(Collectors.toSet()));
        }
        parkingModel.setOwner(userEntityToModelMapper.transform(from.getOwner()));
        return parkingModel;
    }
}
