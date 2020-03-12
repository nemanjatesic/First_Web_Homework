package paket;

import thread.Arrange;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("Enter number of students you want : ");
        Scanner scanner = new Scanner(System.in);
        int numberOfStudents = scanner.nextInt();
        System.out.println("How long do you want exam to last in milliseconds (1 sec = 1000 milliseconds) : ");
        int examDuration = scanner.nextInt();
        Helper.numberOfStudents = numberOfStudents;
        Helper.examDuration = examDuration;
        (new Thread(new Arrange())).start();
    }
}
