package BusinessLogic;

import Model.Server;
import Model.Task;

import java.util.ArrayList;
import java.util.List;

public class Scheduler {
    private List<Server> servers;
    private int maxNoServers;
    public static int maxTasksPerServer;
    private Strategy strategy;

    public Scheduler(int maxNoServers, int maxTasksPerServer){
        this.servers = new ArrayList<Server>();
        this.maxNoServers = maxNoServers;
       Scheduler.maxTasksPerServer = maxTasksPerServer;
       //System.out.println("aici: " + maxTasksPerServer);

        for(int i = 0; i < maxNoServers; i++) {
            Server server = new Server(i+1);
            this.servers.add(server);

            Thread thread = new Thread(server);
            thread.start();
            //System.out.println("sch");
        }
    }

    public void changeStrategy(SelectionPolicy policy){
        if(policy == SelectionPolicy.SHORTEST_QUEUE){
            this.strategy = new ConcreteStrategyQueue();
        }
        if(policy == SelectionPolicy.SHORTEST_TIME){
            this.strategy = new ConcreteStrategyTime();
        }
    }

    public void dispatchTask(Task task,Strategy s) throws Exception{
      s.addTask(servers,task);
    }

    public List<Server> getServers(){
        return servers;
    }


    public void setServers(List<Server> servers) {
        this.servers = servers;
    }

    public int getMaxNoServers() {
        return maxNoServers;
    }

    public void setMaxNoServers(int maxNoServers) {
        this.maxNoServers = maxNoServers;
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }
}
