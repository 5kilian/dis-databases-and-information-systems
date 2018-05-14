package de.estate.manager;

import de.estate.manager.model.Agent;
import de.estate.manager.service.AgentService;

import java.util.List;

public class Main2 {

    public static void main(String[] args) {
        AgentService agentService = new AgentService();


        Agent agent = new Agent();
        agent.setLogin("hallo");
        agent.setPassword("welt");
        agent.setName("Test");
        agent.setAddress("City");

        Integer id = agentService.addAgent(agent);

        Agent agent1 = agentService.get(id);
        List agents = agentService.getAll();
        System.out.println(agents.size());
        System.out.println(agent1.getName());
    }
}
