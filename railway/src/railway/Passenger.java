package railway;


public class Passenger extends User implements railInfo {
    private int passengerId;
    private String contactNumber;

    public Passenger(int passengerId, String name, String contactNumber) {
        super(name);
        this.passengerId = passengerId;
        this.contactNumber = contactNumber;
    }
    public int getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(int passengerId) {
        this.passengerId = passengerId;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    @Override
    public String toString() {
        return "Passenger ID: " + passengerId +
                ", Contact Number: " + contactNumber;
    }
    
}