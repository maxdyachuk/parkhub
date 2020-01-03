package ua.com.parkhub.persistence.entities;

import javax.persistence.Entity;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "user", schema = "park_hub")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    @NotNull
    private String firstName;

    @Column(name = "last_name")
    @NotNull
    private String lastName;

    @Column(unique = true)
    @NotNull
    private String email;

    @Column(name = "password")
    @NotNull
    @Size(min = 6, max = 60)
    private String password;

    @Column(name = "number_of_faild_pass_entering")
    @NotNull
    private int numberOfFaildPassEntering;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private UserRole role;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    @ManyToMany(mappedBy = "solvers")
    private List<SupportTicket> tickets;

    public int getNumberOfFaildPassEntering() {
        return numberOfFaildPassEntering;
    }

    public void setNumberOfFaildPassEntering(int numberOfFaildPassEntering) {
        this.numberOfFaildPassEntering = numberOfFaildPassEntering;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<SupportTicket> getTickets() {
        return tickets;
    }

    public void setTickets(List<SupportTicket> tickets) {
        this.tickets = tickets;
    }


}
