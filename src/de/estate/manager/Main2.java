package de.estate.manager;

import de.estate.manager.model.Agent;
import de.estate.manager.service.AgentService;

import java.util.List;

public class Main2 {

    public static void main(String[] args) {
        AgentService agentService = new AgentService();
        List<Agent> agents = agentService.getAll();
        System.out.println(agents.size());
    }
}
