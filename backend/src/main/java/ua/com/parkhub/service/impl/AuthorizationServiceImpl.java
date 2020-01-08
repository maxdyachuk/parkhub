package ua.com.parkhub.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.com.parkhub.exceptions.PermissionException;
import ua.com.parkhub.exceptions.StatusCode;
import ua.com.parkhub.model.UserModel;
import ua.com.parkhub.persistence.impl.BlockedUserDAO;
import ua.com.parkhub.persistence.impl.UserDAO;
import ua.com.parkhub.service.AuthorizationService;

@Service
public class AuthorizationServiceImpl implements AuthorizationService {

    private UserDAO userDAO;
    private BlockedUserDAO blockedUserDAO;
    private PasswordEncoder passwordEncoder;

    private final int THREE_TRIES_TO_ENTER = 3;
    private final int ONE_FAILED_TRIE_TO_ENTER = 1;

    public AuthorizationServiceImpl() {
    }

    @Autowired
    public AuthorizationServiceImpl(UserDAO userDAO, BlockedUserDAO blockedUserDAO, PasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
        this.blockedUserDAO = blockedUserDAO;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserModel loginUser(UserModel loginUser) {
            UserModel user = userDAO.findUserByEmail(loginUser.getEmail());
                activateIfPossible(user);
                if (user.getNumberOfFailedPassEntering() >= THREE_TRIES_TO_ENTER) {
                    blockUser(user);
                }
                return checkCredentials(loginUser, user);
    }

    private void blockUser(UserModel user) {
        if (!(blockedUserDAO.isBlocked(user))) {
            blockedUserDAO.blockUser(user);
            throw new PermissionException(StatusCode.ACCOUNT_BLOCKED);
        }
    }

    private void activateIfPossible(UserModel user) {
        if ((blockedUserDAO.isBlocked(user)) && (blockedUserDAO.canActivate(user))) {
            blockedUserDAO.activateUser(user);
            user.setNumberOfFailedPassEntering(0);
            userDAO.updateElement(user);
        }
    }

    private UserModel checkCredentials(UserModel loginUser, UserModel userModel) {
        if (passwordEncoder.matches(loginUser.getPassword(), userModel.getPassword())) {
            if (!(blockedUserDAO.isBlocked(userModel))) {
                return userModel;
            } else {
                throw new PermissionException(StatusCode.CANNOT_ACTIVATE);
            }
        } else {
            userModel.setNumberOfFailedPassEntering(userModel.getNumberOfFailedPassEntering() + ONE_FAILED_TRIE_TO_ENTER);
            userDAO.updateElement(userModel);
            if (blockedUserDAO.isBlocked(userModel)) {
                throw new PermissionException(StatusCode.ACCOUNT_BLOCKED);
            }
            throw new PermissionException(StatusCode.INVALID_CREDENTIALS);
        }
    }

}
