package ua.com.parkhub.model;


import java.util.List;

public class ParkingModel {

    private long id;
    private String parkingName;
    private int slotsNumber;
    private int tariff;
    private AddressModel addressModel;
    private boolean isActive;
    private UserModel owner;
    private ParkingInfoModel info;
    private List<SlotModel> slots;

    public UserModel getOwner() {
        return owner;
    }

    public void setOwner(UserModel owner) {
        this.owner = owner;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getParkingName() {
        return parkingName;
    }

    public void setParkingName(String parkingName) {
        this.parkingName = parkingName;
    }

    public int getSlotsNumber() {
        return slotsNumber;
    }

    public void setSlotsNumber(int slotsNumber) {
        this.slotsNumber = slotsNumber;
    }

    public ParkingInfoModel getInfo() {
        return info;
    }

    public void setInfo(ParkingInfoModel info) {
        this.info = info;
    }

    public List<SlotModel> getSlots() {
        return slots;
    }

    public void setSlots(List<SlotModel> slots) {
        this.slots = slots;
    }


    //SOME BUSINESS LOGIC
}
