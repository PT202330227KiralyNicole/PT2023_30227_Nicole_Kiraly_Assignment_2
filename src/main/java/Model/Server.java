
package Model;

import BusinessLogic.Scheduler;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.sleep;

public class Server implements Runnable {
    public static volatile boolean runnable = true;
    private int nb = 0;
    private AtomicInteger waitingPeriod;
    private BlockingQueue<Task> tasks;
    private static double avgWaitingPeriod = 0.0;
    private Scheduler scheduler;

    public Server(int nb) {
        this.nb = nb;
        this.waitingPeriod = new AtomicInteger(0);
        this.tasks = new ArrayBlockingQueue<Task>(scheduler.maxTasksPerServer);
    }

    public AtomicInteger getWaitingPeriod() {
        return waitingPeriod;
    }

    public void setWaitingPeriod(AtomicInteger waitingPeriod) {
        this.waitingPeriod = waitingPeriod;
    }

    public BlockingQueue<Task> getTasks() {
        return tasks;
    }

    public void setTasks(BlockingQueue<Task> tasks) {
        this.tasks = tasks;
    }

    public static double getAvgWaitingPeriod() {
        return avgWaitingPeriod;
    }

    public void setAvgWaitingPeriod(double avgWaitingPeriod) {
        this.avgWaitingPeriod = avgWaitingPeriod;
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }
    public int getNb() {
        return nb;
    }
    public void setNb(int nb) {
        this.nb = nb;
    }

    public void addTask(Task newTask){
        this.tasks.add(newTask);
        avgWaitingPeriod += getWaitingPeriod().intValue()+ newTask.getServiceTime();
        this.waitingPeriod.set(this.waitingPeriod.intValue() + newTask.getServiceTime());

    }
    public void run(){
        Task task=null;

        while(runnable){
        try{
            task = tasks.peek();

            if(task != null){
                sleep(1000 * task.getServiceTime());
                this.waitingPeriod.set(this.waitingPeriod.intValue() - task.getServiceTime());
                tasks.poll();
            }
        }catch(InterruptedException ex){
            ex.printStackTrace();
        }
        }
    }

    public String toString(){
        String res = "";
        if(this.tasks.size() == 0){
            res+="Queue "+ this.nb + ": closed\n";
        }else{
            res+="Queue "+ this.nb + ": ";
        }
        for(Task t : this.tasks){
            if(t.getServiceTime() != 0)
            res+=(t.toString());
            res+="\n";

        }
        return res;
    }
}
