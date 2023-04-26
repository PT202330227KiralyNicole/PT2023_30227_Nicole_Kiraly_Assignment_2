package BusinessLogic;

import Model.Server;
import Model.Task;

import java.util.List;

public class ConcreteStrategyQueue implements Strategy {
    @Override
    public void addTask(List<Server> servers, Task t) throws Exception{
        Server minQueue = null;
        int min = Integer.MAX_VALUE;

        for(Server s : servers){
            if((s.getTasks().size() < Scheduler.maxTasksPerServer) && (s.getTasks().size()<min)){
                min = s.getTasks().size();
                minQueue=s;
            }
        }
        if(minQueue != null){
            minQueue.addTask(t);
        }else{
            throw new Exception("Queues are full!");
        }

    }
}
