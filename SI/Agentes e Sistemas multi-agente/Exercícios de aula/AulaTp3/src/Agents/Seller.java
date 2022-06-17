package Agents;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;


import java.util.*;

public class Seller extends Agent {

    public Map productsAvailable;
    public float totalProfit;


    @Override
    protected void setup(){
        super.setup();


        this.productsAvailable = new HashMap<String,Float>();



        // Register the service in the yellow pages
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType("product-selling");
        sd.setName("JADE-product-selling");
        dfd.addServices(sd);

        try {
            DFService.register(this, dfd);
        }
        catch (FIPAException fe) {
            fe.printStackTrace();
        }


        addBehaviour(new SellerBehaviourTick(this,10000));
        // Add the behaviour serving queries from buyer agents
        addBehaviour(new OfferRequestsServer());
        System.out.println(this.getLocalName() + " a começar...");

    }


    @Override
    protected void takeDown(){
        super.takeDown();
        System.out.println(this.getLocalName() + " a morrer...");
    }



    private void initialList(){
        this.productsAvailable.put("Laranja", 3.5F);
        this.productsAvailable.put("Kiwi", 2.5F);
        this.productsAvailable.put("Pera", 1.5F);
        this.productsAvailable.put("Maca", 3.6F);
        this.productsAvailable.put("Amora",7.8F);
        this.totalProfit = 0.0F;
    }



    public void updateListOfProducts(String title, Float price) {
        addBehaviour(new OneShotBehaviour() {
            public void action() {
                productsAvailable.put(title,price);
                System.out.println(title+" inserted into catalogue. Price = "+price);
            }
        } );
    }


   private class OfferRequestsServer extends CyclicBehaviour{

       @Override
       public void action() {
           MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.CFP);
           ACLMessage msg = myAgent.receive(mt);
           if (msg!=null){
               // CFP Message received. Process it

               String[] splited = msg.getContent().split("\\s+");
               ACLMessage reply = msg.createReply();

               Float numberOfProductsRequested = Float.valueOf(splited[1]);
               Float price = (Float)productsAvailable.get(splited[0]);

               if (price != null){
                   // The requested product is available for sale. Reply with the price
                   totalProfit += price * numberOfProductsRequested;
                   reply.setPerformative(ACLMessage.CONFIRM);
                   reply.setContent("Purchase completed");
               }

               else {
                   // The requested product is NOT available for sale.
                   reply.setPerformative(ACLMessage.REFUSE);
                   reply.setContent("Product not available");
               }

               myAgent.send(reply);
           }

           else {
               block();
           }
       }
   }

   private class SellerBehaviourTick extends TickerBehaviour{

       public SellerBehaviourTick(Agent a, long period) {
           super(a, period);
       }

       @Override
       protected void onTick() {
          System.out.println("Seller's profit so far: "+ totalProfit);

       }
   }

}
