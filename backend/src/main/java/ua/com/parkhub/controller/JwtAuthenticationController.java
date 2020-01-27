package ua.com.parkhub.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.com.parkhub.dto.LoginDTO;
import ua.com.parkhub.dto.UserResponseDTO;
import ua.com.parkhub.exceptions.PermissionException;
import ua.com.parkhub.exceptions.StatusCode;
import ua.com.parkhub.mappers.dtoToModel.LoginDtoToUserModelMapper;
import ua.com.parkhub.mappers.modelToDTO.UserModelToUserResponseDtoMapper;
import ua.com.parkhub.security.JwtUtil;
import ua.com.parkhub.service.IAuthorizationService;

import javax.validation.Valid;


@RestController
@RequestMapping(value = "")
public class JwtAuthenticationController {

    private IAuthorizationService authenticationService;
    private static Logger logger = LoggerFactory.getLogger(JwtAuthenticationController.class.getSimpleName());
    private JwtUtil jwtUtil;
    private UserModelToUserResponseDtoMapper userModelToUserDtoMapper;
    private LoginDtoToUserModelMapper loginDtoToUserModelMapper;

    @Autowired
    public JwtAuthenticationController(IAuthorizationService authenticationService, JwtUtil jwtUtil,UserModelToUserResponseDtoMapper userModelToUserDtoMapper, LoginDtoToUserModelMapper loginDtoToUserModelMapper) {
        this.authenticationService = authenticationService;
        this.jwtUtil = jwtUtil;
        this.userModelToUserDtoMapper = userModelToUserDtoMapper;
        this.loginDtoToUserModelMapper = loginDtoToUserModelMapper;
    }

    @PostMapping(value = "/api/login")
    public ResponseEntity<UserResponseDTO> loginUser(@Valid @RequestBody LoginDTO login) {
        if (login == null) {
            throw new PermissionException(StatusCode.NO_ACCOUNT_FOUND);
        }
        UserResponseDTO response = userModelToUserDtoMapper.transform(authenticationService.loginUser(loginDtoToUserModelMapper.transform(login)));
        response.setToken(jwtUtil.generateToken(response.getEmail(), response.getRole(), response.getId()));
        return ResponseEntity.ok(response);
    }

}
