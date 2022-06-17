package Agents;

import jade.core.Agent;

public class Taxi extends Agent {
    private Position actualPosition ;
    private boolean available;

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
