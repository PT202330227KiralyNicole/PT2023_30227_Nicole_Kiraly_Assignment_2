
package Model;

import BusinessLogic.Scheduler;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.System.exit;
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
                sleep(100 * task.getServiceTime());
                this.waitingPeriod.set(this.waitingPeriod.intValue() - task.getServiceTime());
                tasks.poll();
            }
        }catch(InterruptedException ex){
            ex.printStackTrace();
        }
        }
    }

//    public void run(){
//       // Task task=null;
//        while(runnable){
//            try {
//                sleep(1000);
//            }catch(InterruptedException ex){
//                ex.printStackTrace();
//            }
//
//                if(tasks.size() > 0){
//                    tasks.element().setServiceTime(tasks.element().getServiceTime()-1);
//                    this.waitingPeriod.set(this.waitingPeriod.intValue() - 1);
//                    if(tasks.element().getServiceTime() == 0){
//                        try{
//                            tasks.take();
//                        } catch (InterruptedException e) {
//                            return;
//                        }
//                    }
//
//                }
//
//        }
//    }


//    public void run(){
//        Task task=null;
//
//        while(runnable){
//            if(!tasks.isEmpty()){
//                try{
//                    sleep(1000);
//                }catch(InterruptedException e){
//                    exit(-1);
//                }
//                if(tasks.size() >0){
//                    for(Task t:tasks){
//                        t.setServiceTime(t.getServiceTime()-1);
//                        if(t.getServiceTime() == 0){
//                            try{
//                                tasks.take();
//                            } catch (InterruptedException e) {
//                                return;
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
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
