package railway;

class Ticket {
    private int ticketId;
    private int passengerId;
    private String source;
    private String destination;
    private double fare; // Added "fare" attribute

    public Ticket(int ticketId, int passengerId, String source, String destination, double fare) {
        this.ticketId = ticketId;
        this.passengerId = passengerId;
        this.source = source;
        this.destination = destination;
        this.fare = fare;
    }

    public int getTicketId() {
        return ticketId;
    }

    public int getPassengerId() {
        return passengerId;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public double getFare() {
        return fare;
    }

	public void setTicketId(int ticketId) {
		this.ticketId = ticketId;
	}

	public void setPassengerId(int passengerId) {
		this.passengerId = passengerId;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public void setFare(double fare) {
		this.fare = fare;
	}

	@Override
	public String toString() {
		return "Ticket [ticketId=" + ticketId + ", passengerId=" + passengerId + ", source=" + source + ", destination="
				+ destination + ", fare=" + fare + "]";
	}
	
    
}