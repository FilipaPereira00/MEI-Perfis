package Agents;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;



public class HelloAgent extends Agent {

    @Override
    protected void setup(){
        super.setup();
        System.out.println(this.getLocalName() + " a começar...");
    }

    @Override
    protected void takeDown(){
        super.takeDown();
        System.out.println(this.getLocalName() + " a morrer...");
    }







}
