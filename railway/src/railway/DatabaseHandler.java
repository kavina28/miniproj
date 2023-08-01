package railway;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;



public class DatabaseHandler {
    private String dbURL;
    private String dbUser;
    private String dbPassword;

    public DatabaseHandler(String dbURL, String dbUser, String dbPassword) {
        this.dbURL = dbURL;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dbURL, dbUser, dbPassword);
    }
    
    public int addPassenger(Passenger passenger) {
        String query = "INSERT INTO passengers (name, contact_number) VALUES (?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, passenger.getName());
            statement.setString(2, passenger.getContactNumber());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int passengerId = generatedKeys.getInt(1);
                    passenger.setPassengerId(passengerId);
                    return passengerId;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }
    

    public int bookTicket(Ticket ticket) {
        String query = "INSERT INTO tickets (passenger_id, source, destination, fare) VALUES (?, ?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, ticket.getPassengerId());
            statement.setString(2, ticket.getSource());
            statement.setString(3, ticket.getDestination());
            statement.setDouble(4, ticket.getFare());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1); // Return the generated ticket ID
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1; // Return -1 to indicate failure
    }


    public List<Ticket> getAllTickets() {
        List<Ticket> tickets = new ArrayList<>();
        String query = "SELECT * FROM tickets";

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int ticketId = resultSet.getInt("ticket_id");
                int passengerId = resultSet.getInt("passenger_id");
                String source = resultSet.getString("source");
                String destination = resultSet.getString("destination");
                double fare = resultSet.getDouble("fare"); 

                Ticket ticket = new Ticket(ticketId, passengerId, source, destination,fare);
                tickets.add(ticket);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tickets;
    }


    public boolean cancelTicket(int ticketId) {
        String query = "DELETE FROM tickets WHERE ticket_id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, ticketId);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<Ticket> viewTickets() {
        List<Ticket> tickets = new ArrayList<>();

        String query = "SELECT * FROM tickets";

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int ticketId = resultSet.getInt("ticket_id");
                int passengerId = resultSet.getInt("passenger_id");
                String source = resultSet.getString("source");
                String destination = resultSet.getString("destination");
                double fare = resultSet.getDouble("fare"); 

                Ticket ticket = new Ticket(ticketId, passengerId, source, destination,fare);
                tickets.add(ticket);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tickets;
    }

    public Passenger searchPassenger(String contactNumber) {
        String query = "SELECT * FROM passengers WHERE contact_number = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, contactNumber);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int passengerId = resultSet.getInt("passenger_id");
                    String name = resultSet.getString("name");
                    return new Passenger(passengerId, name, contactNumber);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
    
    public List<Ticket> getTicketByPassengerNumber(String passengerNumber) {
        List<Ticket> tickets = new ArrayList<>();
        String query = "SELECT * FROM tickets WHERE passenger_id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, passengerNumber);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int ticketId = resultSet.getInt("ticket_id");
                int passengerId = resultSet.getInt("passenger_id");
                String source = resultSet.getString("source");
                String destination = resultSet.getString("destination");
                double fare = resultSet.getDouble("fare"); 

                Ticket ticket = new Ticket(ticketId, passengerId, source, destination,fare);
                tickets.add(ticket);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tickets;
    }
    public List<Integer> getReservedSeats(String trainNumber, String journeyDate) {
        List<Integer> reservedSeats = new ArrayList<>();
        String query = "SELECT seat_number FROM tickets WHERE train_number = ? AND journey_date = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, trainNumber);
            statement.setString(2, journeyDate);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int seatNumber = resultSet.getInt("seat_number");
                    reservedSeats.add(seatNumber);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reservedSeats;
    }

    public Ticket checkTicketStatus(int ticketId){
        String query = "SELECT * FROM tickets WHERE ticket_id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, ticketId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                	 int passengerId = resultSet.getInt("passenger_id");
                     String source = resultSet.getString("source");
                     String destination = resultSet.getString("destination");
                     double fare = resultSet.getDouble("fare"); // Include "fare" attribute

                     return new Ticket(ticketId, passengerId, source, destination, fare);
                 }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}