package ua.com.parkhub.controller;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.com.parkhub.dto.ParkingRequestDTO;
import ua.com.parkhub.mapper.ParkingRequestMapper;
import ua.com.parkhub.model.ParkingModel;
import ua.com.parkhub.service.impl.ParkingService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cabinet/addParking")
public class ParkingController {

    private final ParkingService parkingService;
    private final ParkingRequestMapper parkingRequestMapper;

    @Autowired
    public ParkingController(ParkingService parkingService) {
        this.parkingService = parkingService;
        parkingRequestMapper = Mappers.getMapper( ParkingRequestMapper.class);
    }

    @PostMapping
    public ResponseEntity addParking(@Valid@RequestBody ParkingRequestDTO parkingRequestDTO, BindingResult result) {
        long id = 1;
        if (result.hasErrors()) {
            List<String> errors = result.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return new ResponseEntity(errors, HttpStatus.BAD_REQUEST);
        }
        ParkingModel parkingModel = parkingRequestMapper.parkingRequestDTOToParkingModel(parkingRequestDTO);
        if (!parkingService.isParkingNameUnique(parkingModel)){
            return new ResponseEntity("This parking name already exists", HttpStatus.BAD_REQUEST);
        }

        if (!parkingService.checkIfAddressIsUnique(parkingModel)){
            return new ResponseEntity("Parking with that address already exists!", HttpStatus.BAD_REQUEST);
        }
        parkingService.createParkingByOwnerID(parkingModel,id);
        return ResponseEntity.ok().build();
    }




}