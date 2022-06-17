package Behaviours;

import Agents.Player;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.introspection.ACLMessage;




public class Attacker extends OneShotBehaviour {

    Player player = new Player();

    public Attacker(Agent a) {
        super(a);
        this.player = (Player) a;
    }
    
	@Override
	public void action() {
		//Pesquisar o serviço
            DFAgentDescription d = new DFAgentDescription();
            ServiceDescription s = new ServiceDescription();
            s.setType(player.getTeamId());
            d.addServices(s);
            
            try {
				DFAgentDescription[] r = DFService.search(this.myAgent, d);
                //player.g
                



			} catch (FIPAException e) {
				e.printStackTrace();
			}
      
	}

  
}

