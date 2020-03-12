package thread;

import color.Color;
import paket.Helper;
import paket.Student;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Assistant implements Runnable {

    private Semaphore semaphore;
    private Student student;
    private boolean inside;

    public Assistant(Semaphore semaphore, Student student) {
        this.semaphore = semaphore;
        this.student = student;
        this.inside = false;
    }

    @Override
    public void run() {
        try {
            semaphore.acquire();
            {
                inside = true;
                System.out.println(Color.CYAN_BOLD_BRIGHT + "Assistant" + Color.RESET + " - Student: " + Color.MAGENTA
                        + student.getName() + Color.RESET + ", with id : " + student.getId()
                        + " , has entered exam. Time: " + System.currentTimeMillis()
                        + ". Time needed to defend : " + student.getDefendTime());

                Thread.sleep(student.getDefendTime());

                Random random = new Random();
                student.setScore(random.nextInt(6) + 5);
                Helper.getInstance().gradeStudent(student.getScore());

                System.out.println(Color.CYAN_BOLD_BRIGHT + "Assistant" + Color.RESET + " - Student : " + Color.MAGENTA
                        + student.getName() + Color.RESET + ", with id : " + student.getId()
                        + ", has finished with exam, with score of : "
                        + student.getScore() + ". Time: " + System.currentTimeMillis());
            }
            semaphore.release();
        } catch (InterruptedException e) {
            if (inside && student.getScore() == -1){
                Random random = new Random();
                student.setScore(random.nextInt(6) + 5);
                Helper.getInstance().gradeStudent(student.getScore());
            }
            if (student.getScore() != -1)
                System.out.println(Color.CYAN_BOLD_BRIGHT + "Assistant" + Color.RESET + " - Student : " + Color.MAGENTA
                        + student.getName() + Color.RESET + ", with id : " + student.getId()
                        + ", has stopped before he could finish, but he scored : " + student.getScore() + ". Time: " + System.currentTimeMillis());
            else
                System.err.println("Assistant - Student : " + student.getName()
                        + ", with id : " + student.getId() + ", has stopped before he could finish. Time: " + System.currentTimeMillis());
        }
    }


}
