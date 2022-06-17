package Agents;

import jade.core.Agent;


public class Client extends Agent{

    private Position actualPosition ;
    private  Position destPosition;

    @Override
    protected void setup(){
        super.setup();
    }

    @Override
    protected void takeDown(){
        super.takeDown();
        System.out.println(this.getLocalName() + " a morrer...");
    }


}
