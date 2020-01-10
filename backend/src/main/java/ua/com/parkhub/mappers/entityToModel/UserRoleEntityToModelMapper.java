package ua.com.parkhub.mappers.entityToModel;

import org.springframework.stereotype.Component;
import ua.com.parkhub.mappers.Mapper;
import ua.com.parkhub.model.UserRoleModel;
import ua.com.parkhub.persistence.entities.UserRole;

@Component
public class UserRoleEntityToModelMapper implements Mapper<UserRole, UserRoleModel> {
    @Override
    public UserRoleModel transform(UserRole from) {
        if(from == null) {
            return null;
        }
        UserRoleModel userRoleModel = new UserRoleModel();
        userRoleModel.setId(from.getId());
        userRoleModel.setActive(from.isActive());
        userRoleModel.setRoleName(from.getRoleName());
        return userRoleModel;
    }
}
