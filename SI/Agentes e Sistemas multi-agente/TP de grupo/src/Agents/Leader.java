package Agents;


import java.util.List;
import java.util.Map;

import Classes.Position;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.domain.FIPAException;

public class Leader extends Agent {

    // Variaveis
    private String id;

    private int teamId;
    // IdPlayer -> ListaPosições
    private Map<String,List<Position>> visionFields;
    //private Map<String,Player> playersInGame;
    // Posição Atual dos players
    private Map<String,Position> origPos;

    private int pontuacao;
    private Position target;
    
    
    @Override
    protected void setup() {
        super.setup();
        id = getAID().getLocalName();


        
        if(id.substring(0,1).equals("R"))
            teamId = 2;
        else if(id.substring(0,1).equals("B"))
            teamId = 1;

        // Register Agent in Directory Facilitator (Yellow Pages)
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setName(getLocalName());
        sd.setType( id );
        dfd.addServices(sd);

        try {
            DFService.register(this, dfd);
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }

        // Prepare Variables
        // products_sold.put("A", 0);

        /*
        addBehaviour(new Explorer(this,100, this));
        addBehaviour(new Attacker());
        addBehaviour(new Defender());
        addBehaviour(new Baiter());
        */
    }

    public class ReceiveMessages extends CyclicBehaviour {
        public void action() {

            ACLMessage msg = myAgent.receive();
            if (msg != null && msg.getPerformative() == ACLMessage.REQUEST) { // receber pedidos de requisição

                String playerID = msg.getSender().getLocalName();

                String content = msg.getContent();
                System.out.println("Sender diz " + content);

                ACLMessage resp = msg.createReply();
                resp.setContent("Resposta com Decisão Target");
                resp.setPerformative(ACLMessage.CONFIRM);

                //String[] content = msg.getContent().split("-")
                myAgent.send(resp);
            }
        }
    }

    @Override
    protected void takeDown() {
        super.takeDown();
        //System.out.println(this.getLocalName() + " a morrer...");
        // De-register Agent from DF before killing it
        try {
            DFService.deregister(this);
            System.out.println(this.getLocalName() + " a morrer...");
        }
        catch (Exception e) {
        }
    }
}


