package com.Spring.RegiLog;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Scanner;



public class UserBmi {
    Gym gym = new Gym();
    Exercise exercise = new Exercise();

    private double weight;
    private double height;

    public UserBmi(double weight, double height) {
        this.weight = weight;
        this.height = height;
    }

    public double calculateBMI() {
        return weight / (height * height);
    }

    public void displayBMIResult(User loggedInUser) {
        Gym gym = new Gym();
        double bmi = calculateBMI();
            loggedInUser.setBmi(bmi); // Update the BMI in the loggedInUser object
        System.out.printf("Your bmi is %.2f\n", bmi);

        if (bmi <= 18.5) {
            System.out.println("You got the Underweight plan\n");
            loggedInUser.setTrainingPlan("Underweight");
        } else if (bmi <= 24.9) {
            System.out.println("You got the Normal Weight plan\n");
            loggedInUser.setTrainingPlan("Normal Weight");
        } else if (bmi < 35) {
            System.out.println("You got the Overweight plan\n");
            loggedInUser.setTrainingPlan("Overweight");
        }

        loggedInUser.setBmi(bmi); // Update the BMI in the loggedInUser object
        UserService userService = new UserService();
        userService.updateUserBMI(loggedInUser); // Update the BMI in the database
        userService.updateUserTrainingPlan(loggedInUser);

    }

    public static void calculateAndDisplayBMI(User loggedInUser) throws ParseException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your weight (in kg):");
        double weight = scanner.nextDouble();
        System.out.println("Enter your height (in meters, use dot or comma as decimal separator):");
        String heightInput = scanner.next();
        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        decimalFormat.setParseBigDecimal(true);
        double height = decimalFormat.parse(heightInput).doubleValue();
        UserBmi bmiCalculator = new UserBmi(weight, height);
        bmiCalculator.displayBMIResult(loggedInUser);
    }
}
