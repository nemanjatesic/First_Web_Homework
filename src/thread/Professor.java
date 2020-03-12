package thread;

import color.Color;
import paket.Helper;
import paket.Student;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Professor implements Runnable{

    private CyclicBarrier barrier;
    private Student student;

    public Professor(CyclicBarrier barrier, Student student) {
        this.barrier = barrier;
        this.student = student;
    }

    @Override
    public void run() {
        try {
            barrier.await();
            {
                System.out.println(Color.YELLOW_BOLD_BRIGHT + "Professor" + Color.RESET + " - Student: " + Color.MAGENTA
                        + student.getName() + Color.RESET + ", with id : " + student.getId()
                        + " , has entered exam. Time: " + System.currentTimeMillis()
                        + ". Time needed to defend : " + student.getDefendTime());
                Thread.sleep(student.getDefendTime());

                Random random = new Random();
                student.setScore(random.nextInt(6) + 5);
                Helper.getInstance().gradeStudent(student.getScore());

                System.out.println(Color.YELLOW_BOLD_BRIGHT + "Professor" + Color.RESET + " - Student : " + Color.MAGENTA
                        + student.getName() + Color.RESET + ", with id : " + student.getId()
                        + ", has finished with exam, with score of : "
                        + student.getScore() + ". Time: " + System.currentTimeMillis());
                Helper.getInstance().removeStudentFromProfessor();
            }
        } catch (InterruptedException e) {
            if (student.getScore() == -1){
                Random random = new Random();
                student.setScore(random.nextInt(6) + 5);
                Helper.getInstance().gradeStudent(student.getScore());
            }
            if (student.getScore() == -1)
                System.err.println("Professor - Student : " + student.getName()
                    + ", with id : " + student.getId() + ", has stopped before he could finish, but he scored : "
                    + student.getScore() + ". Time: " + System.currentTimeMillis());
            else
                System.out.println(Color.YELLOW_BOLD_BRIGHT + "Professor" + Color.RESET + " - Student : " + Color.MAGENTA
                        + student.getName() + Color.RESET + ", with id : " + student.getId()
                        + ", has stopped before he could finish, but he scored : " + student.getScore() + ". Time: " + System.currentTimeMillis());
        } catch (BrokenBarrierException e){
            e.printStackTrace();
        }
    }

}
