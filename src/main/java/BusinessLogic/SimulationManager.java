package BusinessLogic;

import GUI.Events;
import GUI.SimulationFrame;
import Model.Server;
import Model.Task;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import static java.lang.Thread.sleep;

public class SimulationManager implements Runnable {
    private Scheduler scheduler;
    private static double avgServiceTime = 0.0;
    private static SimulationFrame frame;
    private static Events events;
    private static List<Task> generatedTasks = new ArrayList<Task>();
    private static SelectionPolicy selectionPolicy;
    public static int timeLimit;
    public static int maxProcessingTime;
    public static int minProcessingTime;
    public static int minArrivalTime;
    public static int maxArrivalTime;
    public static int numberofServers;
    public static int numberofClients;

    public static void controller(){
        frame.addStartListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeLimit = Integer.parseInt(frame.getSimulationTime());
                maxProcessingTime = Integer.parseInt(frame.getMaxServTimeTextField());
                minProcessingTime = Integer.parseInt(frame.getMinServTimeTextField());
                minArrivalTime = Integer.parseInt(frame.getMinArrTimeTextField());
                maxArrivalTime = Integer.parseInt(frame.getMaxArrTimeTextField());
                numberofServers = Integer.parseInt(frame.getQueuesTextField());
                numberofClients = Integer.parseInt(frame.getClientsTextField());
                selectionPolicy = frame.getComboBox();
                Scheduler.maxTasksPerServer = numberofClients;
                System.out.println(" aici: " + Scheduler.maxTasksPerServer);

                events.setVisible(true);

                try {
                    generateNRandomTasks();
                }catch(Exception exc){
                    System.out.println(exc.getMessage());
                }
                if(generatedTasks != null){
                    SimulationManager gen = new SimulationManager();
                    Thread t = new Thread(gen);
                    t.start();
                }
            }
        });
    }

    public SimulationManager(){
        this.scheduler = new Scheduler(numberofServers, numberofClients);
        }

    public int getMaxArrivalTime() {
        return maxArrivalTime;
    }

    public void setMaxArrivalTime(int maxArrivalTime) {
        this.maxArrivalTime = maxArrivalTime;
    }

    public int getMinArrivalTime() {
        return minArrivalTime;
    }

    public void setMinArrivalTime(int minArrivalTime) {
        this.minArrivalTime = minArrivalTime;
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public List<Task> getGeneratedTasks() {
        return generatedTasks;
    }

    public void setGeneratedTasks(List<Task> generatedTasks) {
        this.generatedTasks = (ArrayList<Task>) generatedTasks;
    }

    public SelectionPolicy getSelectionPolicy() {
        return selectionPolicy;
    }

    public void setSelectionPolicy(SelectionPolicy selectionPolicy) {
        this.selectionPolicy = selectionPolicy;
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }

    public int getMaxProcessingTime() {
        return maxProcessingTime;
    }

    public void setMaxProcessingTime(int maxProcessingTime) {
        this.maxProcessingTime = maxProcessingTime;
    }

    public int getMinProcessingTime() {
        return minProcessingTime;
    }

    public void setMinProcessingTime(int minProcessingTime) {
        this.minProcessingTime = minProcessingTime;
    }

    public int getNumberofServers() {
        return numberofServers;
    }

    public void setNumberofServers(int numberofServers) {
        this.numberofServers = numberofServers;
    }

    public int getNumberofClients() {
        return numberofClients;
    }

    public void setNumberofClients(int numberofClients) {
        this.numberofClients = numberofClients;
    }
    public int sumClients(Scheduler sch){
        //sum of clients for current time
        int sum = 0;
        for(Server s:sch.getServers()){
           sum+=s.getTasks().size();
        }
        return sum;
    }

    public static void generateNRandomTasks()throws Exception{
        Random rand1 = new Random();
        Random rand2 = new Random();
        if(maxProcessingTime < minProcessingTime){
            throw new Exception("Error: maxProcessingTime < minProcessingTime");
        }
        if(maxArrivalTime < minArrivalTime){
            throw new Exception("Error: maxArrivalTime < minArrivalTime");
        }

        for(int i = 0; i < numberofClients; i++){
            int arrivalTime = rand1.nextInt(maxArrivalTime - minArrivalTime + 1 + minArrivalTime );
          int processingTime = rand2.nextInt(maxProcessingTime - minProcessingTime + 1 + minProcessingTime);
          avgServiceTime += processingTime;
            generatedTasks.add(new Task(i+1,arrivalTime, processingTime));
        }
        avgServiceTime /= (double) numberofClients;
        for(Task t:generatedTasks){
            System.out.println(t.toString());
        }
        Collections.sort(generatedTasks);
}

    @Override
    public void run() {
        String res = "";
        int max = -1;
        int peakHour = -1;
    int currentTime = 0;

    while(currentTime < timeLimit){
        res = res + "Time " + currentTime + "\n";

        if(generatedTasks.size() != 0) {
            res+= "Waiting clients:\n";
        }

        for(Task t:generatedTasks){
            if(t.getArrivalTime() != currentTime) {
                res = res + t.toString();
            }
        }
        if(generatedTasks.size() != 0) {
            res = res + "\n";
        }
        Iterator<Task> iterator = generatedTasks.iterator();

        while(iterator.hasNext()){
            Task t = iterator.next();
            if(t.getArrivalTime() == currentTime) {
                try {
                    scheduler.changeStrategy(selectionPolicy);
                    Strategy s = scheduler.getStrategy();
                    scheduler.dispatchTask(t, s);
                    iterator.remove();
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }
            }
        }

        for(Server s: scheduler.getServers()){
            res+=s.toString();
        }

        if(sumClients(scheduler) > max){
            peakHour = currentTime;
            max = sumClients(scheduler);
        }
        events.setEvents(res);

        try {
            Thread.sleep(1000);
        }catch(InterruptedException e){
            System.out.println("Main thread interrupted");
        }
        currentTime++;
    }

    Server.runnable = false;
    res+="\nAverage waiting time: " + Server.getAvgWaitingPeriod()/(double)numberofClients +"\n";
    res+="\nAverage service time: " + avgServiceTime +"\n";
    res+="\nFirst peak hour: " + peakHour +"\n";

        try {
                FileWriter file = new FileWriter("log_of_events.txt");
                 BufferedWriter buff = new BufferedWriter(file);
                buff.write(res);
                buff.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    public static void main(String[] args){
        frame = new SimulationFrame();
        events = new Events();
        events.setVisible(false);
        SimulationManager.controller();
        frame.setVisible(true);

    }
}