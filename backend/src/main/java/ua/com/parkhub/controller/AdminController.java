package ua.com.parkhub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ua.com.parkhub.dto.AdminDTO;
import ua.com.parkhub.service.AdminService;

@RestController
public class AdminController {
    public AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    /*public AdminDTO adminDTO;
    @Autowired
    public AdminController(AdminDTO adminDTO) {
        this.adminDTO = adminDTO;
    }*/


    /*@GetMapping("/admin/{id}")
    public User getUserById(@PathVariable("id") long id){
        User targetUser = adminService.findUserById(id);
        return targetUser;
    }*/

    /*@GetMapping("/admin/{id}")
    public AdminDTO getUserById(@PathVariable("id") long id){
        return adminDTO.findUserByIdDTO(id);
    }*/

    @GetMapping("/admin/{id}")
    public AdminDTO getUserByID(@PathVariable("id")long id ){
        AdminDTO targetUserDTO = new AdminDTO();
        targetUserDTO.setUserRole(adminService.getRole(id));
        targetUserDTO.setFirstName(adminService.getFirstName(id));
        targetUserDTO.setId(adminService.getId(id));
        return targetUserDTO;
    }

    @PostMapping("/admin/{id}")
    public ResponseEntity setRole(@RequestBody AdminDTO adminDTO){
        adminService.setRole(adminDTO.getId(), adminDTO.getUserRole());
        return ResponseEntity.ok().build();
    }

    /*@PostMapping("/admin")
    public void setUserRole(long id, UserRole role){
        adminService.setRole(id, role);
    }*/



    /*@GetMapping("/test")
    public TestDTO test() {
//        User user = adminService.findUserById(id);
        TestDTO test = new TestDTO();
        test.setName("Vasya");
//        test.setName(user.getFirstName());
//        return test;
        return test;
    }*/
}
