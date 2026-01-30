package com.bank.BankSimulator;

import java.util.Scanner;

import com.bank.BankSimulator.model.User;
import com.bank.BankSimulator.service.AuthService;

public class AuthTest {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        AuthService service = new AuthService();

        while (true) {
            System.out.println("\n--- Banking Auth System ---");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose option: ");

            int choice = sc.nextInt();

            if (choice == 1) {
                User u = new User();
                System.out.print("Enter name: ");
                u.setName(sc.next());
                System.out.print("Enter email: ");
                u.setEmail(sc.next());
                System.out.print("Enter password: ");
                u.setPassword(sc.next());
                service.register(u);
            }

            else if (choice == 2) {
                System.out.print("Enter email: ");
                String email = sc.next();
                System.out.print("Enter password: ");
                String pass = sc.next();
                User user = service.login(email, pass);

                if (user != null) {
                   // System.out.println("Welcome " + user.getName());
                    showBankingMenu(user, sc);
                    
                }
            }

            else if (choice == 3) {
                System.out.println("Thank you!");
                break;
            }
        }
        sc.close();
    }
    
    
    private static void showBankingMenu(User user, Scanner sc) {
        while (true) {
            System.out.println("\n--- Welcome " + user.getName() + " ---");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. Logout");
            System.out.print("Choose option: ");

            int choice = sc.nextInt();

            if (choice == 5) {
                System.out.println("Logged out successfully!");
                break;
            }

            System.out.println("Feature will be connected later...");
        }
    }
}