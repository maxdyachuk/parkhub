package ua.com.parkhub.mappers.fromEntityToModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.com.parkhub.mappers.Mapper;
import ua.com.parkhub.model.UserModel;
import ua.com.parkhub.persistence.entities.User;

import java.util.stream.Collectors;

@Component
public class UserEntityToModelMapper implements Mapper<User, UserModel> {

    CustomerEntityToModelMapper customerEntityToModelMapper;
    RoleEntityToModelMapper roleEntityToModelMapper;
    SupportTicketEntityToModelMapper supportTicketEntityToModelMapper;

    @Autowired
    public UserEntityToModelMapper(CustomerEntityToModelMapper customerEntityToModelMapper, RoleEntityToModelMapper roleEntityToModelMapper, SupportTicketEntityToModelMapper supportTicketEntityToModelMapper) {
        this.customerEntityToModelMapper = customerEntityToModelMapper;
        this.roleEntityToModelMapper = roleEntityToModelMapper;
        this.supportTicketEntityToModelMapper = supportTicketEntityToModelMapper;
    }

    @Override
    public UserModel transform(User from) {
        UserModel userModel = new UserModel();
        userModel.setId(from.getId());
        userModel.setCustomer(customerEntityToModelMapper.transform(from.getCustomer()));
        userModel.setEmail(from.getEmail());
        userModel.setFirstName(from.getFirstName());
        userModel.setLastName(from.getLastName());
        userModel.setPassword(from.getPassword());
        userModel.setRole(roleEntityToModelMapper.transform(from.getRole()));
        userModel.setTickets(from.getTickets().stream().map(supportTicketEntityToModelMapper::transform).collect(Collectors.toSet()));
        return userModel;
    }
}
