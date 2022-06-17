package MainContainer;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.StaleProxyException;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.core.Runtime;



public class MainContainer {
    Runtime rt;
    ContainerController container;

    public void initMainContainerInPlatform(String host,String port,String containerName){
        // Get the Jade runtime interface(singleton)
        this.rt = Runtime.instance();

        //Create a Profile, where the launch arguments are stored
        Profile prof = new ProfileImpl();
        prof.setParameter(Profile.MAIN_HOST,host);
        prof.setParameter(Profile.MAIN_PORT,port);
        prof.setParameter(Profile.CONTAINER_NAME,containerName);
        prof.setParameter(Profile.MAIN,"true");
        prof.setParameter(Profile.GUI,"true");

        //create a main agent container
        this.container = rt.createMainContainer(prof);
        rt.setCloseVM(true);

    }

    public ContainerController initContainerInPlatform(String host, String port,String containerName){
        //Get the JADE runtime interface (singleton)
        this.rt = Runtime.instance();

        //Create a PRofile, where the launch arguments are stored
        Profile profile = new ProfileImpl();
        profile.setParameter(Profile.CONTAINER_NAME,containerName);
        profile.setParameter(Profile.MAIN_HOST,host);
        profile.setParameter(Profile.MAIN_PORT,port);

        //Create a non-main agent container
        ContainerController container = rt.createAgentContainer(profile);
        return container;
    }

    //cria agente no container main
    public void startAgentInPlatform(String name,String classpath, Object[] args){
        try {
            AgentController ac = container.createNewAgent(name,classpath,args);
            ac.start();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    //cria agente num non-main container
    public void startAgentInPlatformContainer(ContainerController input_container,String name, String classpath, Object[] args){
        try {
            AgentController ac = input_container.createNewAgent(name,classpath,args);
            ac.start();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    public  static void main(String[] args){
        MainContainer a = new MainContainer();
        //Main container creation
        a.initContainerInPlatform("localhost","9885","MainContainer");

        //Containers Creation - Create 3 different container (separated environments) inside the Main Container
        Object[] args_input = new Object[] {"Container1","Container2","Container3"};
        ContainerController newContainer0 = a.initContainerInPlatform("localhost","9886","Container0");
        ContainerController newContainer1 = a.initContainerInPlatform("localhost","9887",args_input[0].toString());
        ContainerController newContainer2 = a.initContainerInPlatform("localhost","9888",args_input[1].toString());
        ContainerController newContainer4 = a.initContainerInPlatform("localhost","9889",args_input[2].toString());

        //Start seller1 and Customer1 Agents in Container1
        //Start seller2 and Customer2 Agents in Container2
        //Start seller3 and Customer3 Agents in Container3



    }
}
