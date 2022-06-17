package Agents;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class Receiver extends Agent {

    @Override
    protected void setup(){
        super.setup();
        System.out.println(this.getLocalName() + " a começar...");
        addBehaviour(new ReceiveBehaviour());
    }

    @Override
    protected void takeDown(){
        super.takeDown();
        System.out.println(this.getLocalName() + " a morrer...");
    }


    private class ReceiveBehaviour extends CyclicBehaviour {

        @Override
        public void action() {
            ACLMessage msg = myAgent.receive();
            if(msg!=null){
                System.out.println("Recebi uma mensagem de "+msg.getSender()+". Conteudo: "+msg.getContent() );
            }
            block();

        }
    }
}
