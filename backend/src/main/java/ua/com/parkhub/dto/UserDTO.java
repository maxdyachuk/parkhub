package ua.com.parkhub.dto;


public class UserDTO {

    private long id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private CustomerDTO customerDTO;
    private RoleDTO role;
    private String token;
    private int numberOfFaildPassEntering;

    public int getNumberOfFaildPassEntering() {
        return numberOfFaildPassEntering;
    }

    public void setNumberOfFaildPassEntering(int numberOfFaildPassEntering) {
        this.numberOfFaildPassEntering = numberOfFaildPassEntering;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public long getId() {
        return id;
    }

    public int getIdInt(){
        return ((int) id);
    }

    public void setId(long id) {
        this.id = id;
    }

    public RoleDTO getRole() {
        return role;
    }

    public void setRole(RoleDTO role) {
        this.role = role;
    }

    public CustomerDTO getCustomerDTO() {
        return customerDTO;
    }

    public void setCustomerDTO(CustomerDTO customerDTO) {
        this.customerDTO = customerDTO;
    }
}
