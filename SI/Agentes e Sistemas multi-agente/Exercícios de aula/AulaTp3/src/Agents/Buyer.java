package Agents;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.Random;

public class Buyer extends Agent {

   public String product;
   public Float quantity;
   // The list of known seller agents
    private AID[] sellerAgents;
    private Random r;

    @Override
    protected void setup() {
        super.setup();
        System.out.println(this.getLocalName() + " a começar...");

        String[] list = {"Pera", "Maçã", "Framboesa", "Laranja"};
        Random r = new Random();
        product = list[r.nextInt(list.length)];
        quantity = (float) ((Math.random() * (10 - 1)) + 1);


            System.out.println("Target product is " + product);

        // Add a TickerBehaviour that schedules a request to seller agents every second
        addBehaviour(new tickerBehaviour(this,1000));



    }

    @Override
    protected void takeDown(){
        super.takeDown();
        System.out.println(this.getLocalName() + " a morrer...");
    }


    private class tickerBehaviour extends TickerBehaviour{

        public tickerBehaviour(Agent a, long period) {
            super(a, period);
        }

        @Override
        protected void onTick() {
            System.out.println("Trying to buy '" + product + "'");

            // Update the list of seller agents
            DFAgentDescription template = new DFAgentDescription();
            ServiceDescription sd = new ServiceDescription();
            sd.setType("product-selling");
            template.addServices(sd);
            try {
                DFAgentDescription[] result = DFService.search(myAgent, template);
                System.out.println("Found the following " + result.length + " seller agents:");
                sellerAgents = new AID[result.length];
                //Print all sellers found
                for (int i = 0; i < result.length; ++i) {
                    sellerAgents[i] = result[i].getName();
                    System.out.println(sellerAgents[i].getName());
                }
                // Perform the request
                myAgent.addBehaviour(new RequestProduct());


            }
            catch (FIPAException fe) {
                fe.printStackTrace();
            }
        }
    }

    private class RequestProduct extends Behaviour {
        private int step = 0;
        private MessageTemplate mt;

        @Override
        public void action() {
            switch (step){
                case 0 :
                    // Send the cfp to all sellers
                    ACLMessage cfp = new ACLMessage(ACLMessage.CFP);

                    for (int i = 0; i < sellerAgents.length; ++i) {
                        cfp.addReceiver(sellerAgents[i]);
                    }
                    cfp.setContent(product);
                    cfp.setConversationId("product-selling");
                    cfp.setReplyWith("cfp"+System.currentTimeMillis()); // Unique value
                    myAgent.send(cfp);
                    // Prepare the template to get proposals
                    mt = MessageTemplate.and(MessageTemplate.MatchConversationId("product-selling"),
                            MessageTemplate.MatchInReplyTo(cfp.getReplyWith()));
                    step = 1;
                    break;

                case 1:
                    // Receive all confirmations/refusals from seller agents
                    ACLMessage reply = myAgent.receive(mt);
                    if (reply != null){
                        // Reply received
                        if (reply.getPerformative() == ACLMessage.CONFIRM) {
                            //Do something

                        }
                        else{
                            System.out.println("Request Denied. Product does not exist!");
                        }
                    }
                    break;
            }
        }

        @Override
        public boolean done() {
            return false;
        }
    }



}
