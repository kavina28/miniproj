package railway;

import java.util.List;
import java.util.Scanner;

public class RailwayTicketApp {
    private static Scanner scanner = new Scanner(System.in);
    private static DatabaseHandler dbHandler;

    public static void main(String[] args) {
        String dbURL = "jdbc:mysql://localhost:3306/rail";
        String dbUser = "root";
        String dbPassword = "tiger"; // Replace with your database password

        dbHandler = new DatabaseHandler(dbURL, dbUser, dbPassword);

        System.out.println("Welcome to the Railway Ticket Reservation System!");

        boolean exit = false;
        while (!exit) {
            System.out.println("\n1. Book Ticket");
            System.out.println("2. Cancel Ticket");
            System.out.println("3. View All Tickets");
            System.out.println("4. Search Ticket by Passenger Number");
            System.out.println("5. Check Status of Reserved Tickets");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    bookTicket();
                    break;
                case 2:
                    cancelTicket();
                    break;
                case 3:
                    viewAllTickets();
                    break;
                case 4:
                    searchTicketByPassengerNumber();
                    break;
                case 5:
                    checkReservedTicketStatus();
                    break;
                case 6:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
                    break;
            }
        }

        System.out.println("Goodbye!");
    }

    private static void bookTicket() {
        System.out.println("Enter Passenger Name: ");
        String name = scanner.nextLine();
        System.out.println("Enter Contact Number: ");
        String contactNumber = scanner.nextLine();
        System.out.println("Enter Source Station: ");
        String source = scanner.nextLine();
        System.out.println("Enter Destination Station: ");
        String destination = scanner.nextLine();
        System.out.println("Enter Fare: ");
        double fare = scanner.nextDouble();
        scanner.nextLine(); // Consume the newline character

        Passenger passenger = new Passenger(0, name, contactNumber);
        int passengerId = dbHandler.addPassenger(passenger);
        passenger.displayInfo();
        if (passengerId != -1) {
            Ticket ticket = new Ticket(0, passengerId, source, destination, fare);
            int ticketId = dbHandler.bookTicket(ticket);

            if (ticketId != -1) {
                System.out.println("Ticket booked successfully! Ticket ID: " + ticketId);
            } else {
                System.out.println("Failed to book the ticket. Please try again.");
            }
        } else {
            System.out.println("Failed to add passenger. Please try again.");
        }
    }

    private static void cancelTicket() {
        System.out.println("Enter Ticket ID to cancel: ");
        int ticketId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        boolean canceled = dbHandler.cancelTicket(ticketId);
        if (canceled) {
            System.out.println("Ticket with ID " + ticketId + " has been canceled successfully.");
        } else {
            System.out.println("Ticket cancellation failed. Please check the Ticket ID and try again.");
        }
    }

    private static void viewAllTickets() {
        System.out.println("All Tickets:");

        for (Ticket ticket : dbHandler.getAllTickets()) {
            System.out.println(ticket);
        }
    }

    private static void searchTicketByPassengerNumber() {
    	 System.out.print("Enter passenger number to search for tickets: ");
         String passengerNumber = scanner.nextLine();

         List<Ticket> passengerTickets = dbHandler.getTicketByPassengerNumber(passengerNumber);
         if (!passengerTickets.isEmpty()) {
             System.out.println("Tickets found for passenger " + passengerNumber + ":");
             for (Ticket ticket : passengerTickets) {
                 System.out.println(ticket);
             }
         } else {
             System.out.println("No tickets found for passenger " + passengerNumber);
         }
    }
    
    

    private static void checkReservedTicketStatus() {
        System.out.println("Enter Source Station: ");
        String source = scanner.nextLine();
        System.out.println("Enter Destination Station: ");
        String destination = scanner.nextLine();

        List<Integer> reservedSeatsList = dbHandler.getReservedSeats(source, destination);
        int reservedSeats = reservedSeatsList.size();
        System.out.println("Number of reserved seats for the train: " + reservedSeats);

    }
}