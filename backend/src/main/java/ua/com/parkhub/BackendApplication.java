package ua.com.parkhub;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ua.com.parkhub.model.CustomerModel;
import ua.com.parkhub.model.ParkingModel;
import ua.com.parkhub.service.impl.BookingService;
import ua.com.parkhub.service.impl.CustomerService;
import ua.com.parkhub.service.impl.ParkingService;

@SpringBootApplication
public class BackendApplication {
    /*@Bean
    CommandLineRunner init(CustomerService customerService) {
        return args -> {
            CustomerModel customerModel = customerService.findCustomerByPhoneNumberOrAdd("+380672202222");
            System.out.println(customerModel);
        };
    }*/

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }
}
