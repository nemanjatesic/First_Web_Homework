package thread;

import paket.Helper;
import paket.Student;
import thread.Assistant;
import thread.Professor;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

public class Arrange implements Runnable{

    private PriorityQueue<Student> assistantStudents = new PriorityQueue<>();
    private PriorityQueue<Student> professorStudents = new PriorityQueue<>();

    private CyclicBarrier barrier = new CyclicBarrier(2);
    private Semaphore semaphore = new Semaphore(1);

    @Override
    public void run() {
        int numberOfStudents = Helper.numberOfStudents;

        ArrayList<Student> students = new ArrayList<>();
        Random random = new Random();

        System.out.println("Creating students : ");
        for (int i = 0 ; i < numberOfStudents ; i++){
            Student student = new Student(i);
            students.add(student);
            System.out.println(student);
        }
        System.out.println("Students created successfully");

        try {
            Thread.sleep(1000);
        } catch (Exception e){
            e.printStackTrace();
        }

        System.out.println("Arranging students in groups : ");
        try {
            Thread.sleep(1000);
        } catch (Exception e){
            e.printStackTrace();
        }

        boolean professor = false;
        for (int i = 0 ; i < students.size() ; i++) {
            if (!professor) {
                assistantStudents.add(students.get(i));
                professor = true;
            }else {
                professor = false;
                if (i + 2 > students.size()){
                    assistantStudents.add(students.get(i));
                }else {
                    professorStudents.add(students.get(i));
                    professorStudents.add(students.get(i + 1));
                    i++;
                }
            }
        }
        System.out.println("Professors group : ");
        for (Student student : professorStudents) {
            System.out.println(student);
        }
        System.out.println("Assistants group : ");
        for (Student student : assistantStudents) {
            System.out.println(student);
        }
        System.out.println("Students arranged");

        try {
            Thread.sleep(1000);
        } catch (Exception e){
            e.printStackTrace();
        }


        long startProgram = System.currentTimeMillis();
        long endProgram = startProgram + Helper.examDuration;

        ArrayList<Thread> allThreads = new ArrayList<>();

        System.err.println("Exam starting now, current Time : " + System.currentTimeMillis());
        while (System.currentTimeMillis() <= endProgram && Helper.studentCounter.get() != Helper.numberOfStudents) {
            if (!assistantStudents.isEmpty()) {
                if (startProgram + assistantStudents.peek().getArriveTime() < System.currentTimeMillis()) {
                    Student student = assistantStudents.poll();

                    Thread assistant = new Thread(new Assistant(semaphore, student));
                    allThreads.add(assistant);
                    assistant.start();
                }
            }

            if (!professorStudents.isEmpty()) {
                if (startProgram + professorStudents.peek().getArriveTime() < System.currentTimeMillis()
                        && Helper.getInstance().available) {
                    Student student = professorStudents.poll();

                    Helper.getInstance().addStudentToProfessor();
                    Thread professorThread = new Thread(new Professor(barrier, student));
                    allThreads.add(professorThread);
                    professorThread.start();
                }
            }
        }

        System.err.println("Exam finished, current Time : " + System.currentTimeMillis());

        for (Thread thread : allThreads) {
            thread.interrupt();
        }

        try {
            Thread.sleep(200);
        } catch (Exception e){
            e.printStackTrace();
        }

        double average = ((double) Helper.gradeCounter.get()) / Helper.studentCounter.get();
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        average = Double.parseDouble(decimalFormat.format(average));

        System.out.println(Helper.studentCounter.get() + " students finished the exam, with an average score of : "
                        + average);
    }
}
