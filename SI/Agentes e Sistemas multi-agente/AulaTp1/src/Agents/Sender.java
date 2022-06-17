package Agents;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class Sender extends Agent {

    @Override
    protected void setup(){
        super.setup();
        System.out.println(this.getLocalName() + " a começar...");
        //addBehaviour(new SendBehaviour());
        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
        msg.addReceiver(new AID("albert", AID.ISLOCALNAME));
        msg.setContent("Hello Friend!");
        send(msg);
    }

    @Override
    protected void takeDown(){
        super.takeDown();
        System.out.println(this.getLocalName() + " a morrer...");
    }

    /*
    private class SendBehaviour extends OneShotBehaviour {

        @Override
        public void action() {
            ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
            msg.addReceiver(new AID("albert", AID.ISLOCALNAME));
            msg.setContent("Hello Friend!");
            send(msg);

        }
    }

     */
}


