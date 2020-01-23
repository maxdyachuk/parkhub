package ua.com.parkhub.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.com.parkhub.exceptions.EmailException;
import ua.com.parkhub.exceptions.NotFoundInDataBaseException;
import ua.com.parkhub.exceptions.PhoneNumberException;
import ua.com.parkhub.model.enums.RoleModel;
import ua.com.parkhub.model.enums.TicketTypeModel;
import ua.com.parkhub.persistence.impl.*;
import ua.com.parkhub.model.*;
import ua.com.parkhub.security.JwtUtil;
import ua.com.parkhub.service.ISignUpService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class SignUpService implements ISignUpService {

    private static final Logger logger = LoggerFactory.getLogger(SignUpService.class);

    private final CustomerDAO customerDAO;
    private final UserDAO userDAO;
    private final UserRoleDAO userRoleDAO;
    private final SupportTicketDAO supportTicketDAO;
    private final SupportTicketTypeDAO supportTicketTypeDAO;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Autowired
    public SignUpService(CustomerDAO customerDAO, UserDAO userDAO, UserRoleDAO userRoleDAO,
                         SupportTicketDAO supportTicketDAO, SupportTicketTypeDAO supportTicketTypeDAO,
                         PasswordEncoder passwordEncoder,
                         JwtUtil jwtUtil) {
        this.customerDAO = customerDAO;
        this.userDAO = userDAO;
        this.userRoleDAO = userRoleDAO;
        this.supportTicketDAO = supportTicketDAO;
        this.supportTicketTypeDAO = supportTicketTypeDAO;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Transactional
    @Override
    public void registerManager(ManagerRegistrationDataModel manager) {
        CustomerModel customer = createCustomer(manager.getUser().getCustomer());
        UserModel user = createUser(manager.getUser(), customer);
        user = userDAO.addElement(user).orElseThrow(() ->
                new NotFoundInDataBaseException("Customer not found"));
        String description = generateDescription(user.getId(), manager.getCompanyName(),
                manager.getUsreouCode(), manager.getComment());
        SupportTicketModel ticket = createTicket(description, user.getCustomer());
        supportTicketDAO.addElement(ticket);
    }

    @Transactional
    @Override
    public CustomerModel createCustomer(CustomerModel customer) {
        return customerDAO.findCustomerByPhoneNumber(customer.getPhoneNumber()).map(existingCustomer -> {
            logger.info("Customer with phone number={} was found", customer.getPhoneNumber());
            Optional<UserModel> optionalUser = userDAO.findUserByCustomerId(existingCustomer.getId());
            if (optionalUser.isPresent()) {
                throw new PhoneNumberException("Account with this phone number already exists!");
            }
            logger.info("Existing customer was assigned");
            return existingCustomer;
        }).orElseGet(() -> {
            customer.setActive(true);
            logger.info("New customer was created");
            return customer;
        });
    }

    @Transactional
    @Override
    public UserModel createUser(UserModel user, CustomerModel customer) {
        Optional<UserModel> optionalUser = userDAO.findUserByEmail(user.getEmail());
        if (optionalUser.isPresent()) {
            throw new EmailException("Account with this email already exists!");
        }
        user.setCustomer(customer);
        user.setRole(findUserRole(RoleModel.PENDING.getRoleName()));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        logger.info("New user was created");
        return user;
    }

    @Transactional
    @Override
    public SupportTicketModel createTicket(String description, CustomerModel customer) {
        SupportTicketModel ticket = new SupportTicketModel();
        ticket.setDescription(description);
        ticket.setCustomer(customer);
        ticket.setType(findSupportTicketType(TicketTypeModel.MANAGER_REGISTRATION_REQUEST.getValue()));
        ticket.setSolvers(findSolvers(RoleModel.ADMIN.getRoleName()));
        logger.info("New support ticket was created");
        return ticket;
    }

    @Override
    public String generateDescription(long id, String companyName, String usreouCode, String comment) {
        return String.format("ID: %d Company: \"%s\" USREOU code: %s Comment: \"%s\"", id, companyName,
                usreouCode, comment);
    }

    @Override
    public RoleModel findUserRole(String name) {
        return userRoleDAO.findUserRoleByRoleName(name).orElseThrow(() ->
                new NotFoundInDataBaseException("Role was not found by name=" + name));
    }

    @Override
    public TicketTypeModel findSupportTicketType(String type) {
        return supportTicketTypeDAO.findSupportTicketTypeByType(type).orElseThrow(() ->
                new NotFoundInDataBaseException("Support ticket type was not found by type=" + type));
    }

    @Override
    public List<UserModel> findSolvers(String role) {
        List<UserModel> solvers = userDAO.findUsersByRoleId(findUserRole(role).getId());
        if (solvers.isEmpty()) {
            throw new NotFoundInDataBaseException("Solvers were not found by role=" + role);
        }
        return solvers;
    }

    @Override
    public boolean isUserPresentByEmail(String email) {
        return userDAO.findUserByEmail(email).isPresent();
    }//mine

    @Override
    public void setPhoneNumberForAuthUser(PhoneEmailModel phoneEmailModel) {
        logger.info("Setting phone number for Oauth2 user");
        UserModel userModel = userDAO.findUserByEmail(phoneEmailModel.getEmail()).orElseThrow(() ->
                new NotFoundInDataBaseException("User was not found" ));
        CustomerModel customerModel = userModel.getCustomer();
        customerModel.setPhoneNumber(phoneEmailModel.getPhoneNumber());
        customerDAO.updateElement(customerModel);
        logger.info("Phone number for Oauth2 user is set successfully");

    }

    @Override
    public void createUserAfterSocialAuth(AuthUserModel userModel){
            logger.info("Creating new user that was authorized via Google ");
            UserModel user = new UserModel();
            CustomerModel customer = new CustomerModel();
            customer.setPhoneNumber("Empty");
            user.setEmail(userModel.getEmail());
            user.setCustomer(customer);
            user.setPassword(passwordEncoder.encode("oauth2user"));
            user.setLastName(userModel.getLastName());
            user.setFirstName(userModel.getFirstName());
            RoleModel userRole =  findUserRole("USER");
            user.setRole(userRole);
            userDAO.addElement(user);
            logger.info("User created successfully ");
    }//mine

    @Override
    public boolean isCustomerNumberEmpty(String email) {
        UserModel userModel = userDAO.findUserByEmail(email).orElseThrow(() ->
                new NotFoundInDataBaseException("User was not found" ));
        CustomerModel customer = userModel.getCustomer();
        return customer.getPhoneNumber().equals("Empty");
    }//mine

    @Override
    public boolean signUpUser(UserModel userModel){
        try {
            userDAO.addElement( createUser( userModel, createCustomer(userModel.getCustomer())));
            return true;
        } catch (Exception e){
            logger.error(""+e);
        }
        return false;
    }

    @Override
    public boolean isNumberUnique(String phoneNumber) {
        return customerDAO.findManyByFieldEqual("phoneNumber",phoneNumber).isEmpty();
    }

    @Override
    public String generateTokenForOauthUser(String email){
        UserModel userModel = userDAO.findUserByEmail(email).orElseThrow(() ->
                new NotFoundInDataBaseException("User was not found" ));
           String token = jwtUtil.generateToken(userModel.getEmail(),userModel.getRole().getRoleName(), userModel.getId());
           return token;
    }//mine
}
