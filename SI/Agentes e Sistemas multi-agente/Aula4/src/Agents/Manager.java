package Agents;


import jade.core.AID;
import jade.core.Agent;

public class Manager extends Agent {
    private AID[] taxis;


    @Override
    protected void setup(){
        super.setup();
        System.out.println(this.getLocalName() + " a iniciar...");
    }

    @Override
    protected void takeDown(){
        super.takeDown();
        System.out.println(this.getLocalName() + " a morrer...");

    }

}
