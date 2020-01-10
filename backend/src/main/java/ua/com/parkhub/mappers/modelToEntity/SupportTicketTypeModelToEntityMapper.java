package ua.com.parkhub.mappers.modelToEntity;

import org.springframework.stereotype.Component;
import ua.com.parkhub.mappers.Mapper;
import ua.com.parkhub.model.SupportTicketTypeModel;
import ua.com.parkhub.persistence.entities.SupportTicketType;

@Component
public class SupportTicketTypeModelToEntityMapper implements Mapper<SupportTicketTypeModel, SupportTicketType> {
    @Override
    public SupportTicketType transform(SupportTicketTypeModel from) {
        if (from == null) {
            return null;
        }
        SupportTicketType supportTicketType = new SupportTicketType();
        supportTicketType.setId(from.getId());
        supportTicketType.setType(from.getType());
        supportTicketType.setActive(from.isActive());
        return supportTicketType;
    }
}
