import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import main.aima.core.environment.wumpusworld.HybridWumpusAgent;
import main.aima.core.environment.wumpusworld.WumpusAction;
import main.aima.core.environment.wumpusworld.WumpusCave;
import main.aima.core.environment.wumpusworld.WumpusEnvironment;

public class EnvironmentAgent extends jade.core.Agent {

    WumpusEnvironment environment;
    HybridWumpusAgent agent = null;

    private static WumpusCave create4x4Cave() {
        return new WumpusCave(4, 4, ""
                + ". . . P "
                + "W G P . "
                + ". . . . "
                + "S . P . ");
    }

    protected void setup() {
        environment = new WumpusEnvironment(create4x4Cave());

        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType("dungeon");
        sd.setName("JADE-wumpus-world");
        dfd.addServices(sd);
        try {
            DFService.register(this, dfd);
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }

        addBehaviour(new EnvironmentAgent.FindGamer());
    }

    protected void takeDown() {
        super.takeDown();
    }

    private class FindGamer extends Behaviour {
        private boolean done;

        public void action() {
            MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL);
            ACLMessage msg = myAgent.receive(mt);
            if (msg != null) {
                ACLMessage reply = msg.createReply();
                if (agent == null) {
                    agent = NavigatorAgent.getHybridAgent();
                    environment.addAgent(agent);
                    reply.setPerformative(ACLMessage.INFORM);
                    System.out.println("Environment was successfully created");
                    done = true;
                    addBehaviour(new EnvironmentAgent.EmailServer());
                } else {
                    reply.setPerformative(ACLMessage.FAILURE);
                    reply.setContent("sorry, event is postponed");
                }
                myAgent.send(reply);
            } else {
                block();
            }
        }

        public boolean done() {
            return done;
        }
    }

    private class EmailServer extends CyclicBehaviour {
        public void action() {
            ACLMessage msg = myAgent.receive();
            if (msg != null) {
                ACLMessage reply = msg.createReply();
                if (msg.getPerformative() == ACLMessage.REQUEST) {
                    reply.setPerformative(ACLMessage.INFORM);
                    reply.setContent(environment.getPerceptSeenBy(agent).toString());
                    System.out.println("Current position: " + environment.getAgentPosition(agent));
                } else if (msg.getPerformative() == ACLMessage.CFP) {
                    String action = msg.getContent();
                    WumpusAction act = WumpusAction.valueOf(action);
                    environment.execute(agent, act);
                    System.out.println("game logs: " + action);
                    reply.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
                    reply.setContent("OK");
                } else if (msg.getPerformative() == ACLMessage.CANCEL) {
                    doDelete();
                }
                myAgent.send(reply);
            } else {
                block();
            }
        }
    }
}