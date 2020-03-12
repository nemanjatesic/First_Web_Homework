package paket;

import java.util.Random;

public class Student implements Comparable{

    private String name;
    private int id;
    private long arriveTime;
    private long defendTime;
    private int score = -1;

    public Student(int id, long arriveTime, long defendTime) {
        this.name = Helper.getStudentName();
        this.id = id;
        this.arriveTime = arriveTime;
        this.defendTime = defendTime;
    }

    public Student(int id) {
        this.name = Helper.getStudentName();
        this.id = id;
        Random random = new Random();
        this.arriveTime = random.nextInt(1001);
        this.defendTime = random.nextInt(501) + 500;
    }

    @Override
    public int compareTo(Object o) {
        return (int) (this.arriveTime - ((Student)o).arriveTime);
    }

    @Override
    public String toString() {
        return "Student - " +
                "name= '" + name + " \'" +
                ", id: " + id +
                ", arriveTime=" + arriveTime +
                ", defendTime=" + defendTime +
                ", score=" + score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(long arriveTime) {
        this.arriveTime = arriveTime;
    }

    public long getDefendTime() {
        return defendTime;
    }

    public void setDefendTime(long defendTime) {
        this.defendTime = defendTime;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
