package ua.com.parkhub.dto;

public class SlotDTO {

    private Long id;
    private String slotNumber;
    private boolean isReserved;
    private boolean isActive;

    public boolean isReserved() {
        return isReserved;
    }

    public void setReserved(boolean reserved) {
        isReserved = reserved;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSlotNumber() {
        return slotNumber;
    }

    public void setSlotNumber(String slotNumber) {
        this.slotNumber = slotNumber;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("SlotDTO" + ", id: ").append(id);
        sb.append(", slotNumber: ").append(slotNumber);
        return sb.toString();
    }
}
