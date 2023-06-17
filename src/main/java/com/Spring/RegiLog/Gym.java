package com.Spring.RegiLog;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;

import java.text.ParseException;
import java.util.Scanner;

@SpringBootApplication
public class Gym {
	public static void main(String[] args) throws ParseException {
		SpringApplication.run(Gym.class, args);
		UserService userService = new UserService();

		Scanner scanner = new Scanner(System.in);

		System.out.println("Welcome, register or login to continue");

		boolean isLoggedIn = false;
		User loggedInUser = null;

		do {
			System.out.println("1. Register\n" +
					"2. Login");
			if (isLoggedIn) {
				System.out.println("3. Set BMI and training plan\n" +
						"4. Training plan\n" +
						"5. Check BMI\n" +
						"7.Quit ");

			} else {
				System.out.println("7. Exit");
			}
			System.out.println("Choice:");
			int choice = scanner.nextInt();
			scanner.nextLine();
			switch (choice) {
				case 1:
					User user = new User();
					System.out.println("Enter your username:");
					String username = scanner.nextLine();
					System.out.println("Enter your password:");
					String password = scanner.nextLine();
					user.setName(username);
					user.setPassword(password);
					userService.userRegister(user);
					if (!"User successfully registered".equals(userService.getStatus())) {
						System.out.println(userService.getStatus());
						break;
					}
					break;
				case 2:
					User loginUser = new User();
					System.out.println("Enter your username:");
					String loginUsername = scanner.nextLine();
					System.out.println("Enter your password:");
					String loginPassword = scanner.nextLine();
					loginUser.setName(loginUsername);
					loginUser.setPassword(loginPassword);
					if (userService.userLogin(loginUser)) {
						isLoggedIn = true;
						loggedInUser = loginUser;
						System.out.println("Login successful");
					} else {
						System.out.println("Invalid username or password, please try again");
					}
					break;
				case 3:
					if (isLoggedIn) {
						UserBmi.calculateAndDisplayBMI(loggedInUser);
					} else {
						System.out.println("Invalid choice. Please select a valid option.");
					}
					break;
				case 4:
					if (isLoggedIn) {
						String trainingPlan = loggedInUser.getTrainingPlan();
						if (trainingPlan != null && !trainingPlan.isEmpty()) {
							switch (trainingPlan) {
								case "Underweight":
									new Exercise().UnderWeight();
									break;
								case "Normal Weight":
									new Exercise().NormalWeight();
									break;
								case "Overweight":
									new Exercise().OverWeight();
									break;
							}
						} else {
							System.out.println("No training plan set. Please set your BMI and training plan first.");
						}
					} else {
						System.out.println("Invalid choice. Please select a valid option.");
					}
					break;
				case 5:
					if (isLoggedIn) {
						double bmi = loggedInUser.getBmi();
						if (bmi != 0.0) {
							System.out.printf("Your current BMI: %.2f\n", bmi);
						} else {
							System.out.println("No BMI data available. Please set your BMI first.");
						}
					} else {
						System.out.println("Invalid choice. Please select a valid option.");
					}
					break;
				case 7:
					System.exit(0);
				default:
					System.out.println("Invalid choice. Please select a valid option.");
					break;
			}
		} while (true);
	}
}
