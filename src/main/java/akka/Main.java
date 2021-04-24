package akka;

import akka.actor.typed.ActorSystem;

public class Main {

    public static void main(String[] args) {
        System.out.println("start");

//        ActorSystem<String> firstActorSystem = ActorSystem.create(FirstSimpleBehavior.create(), "FirstActorSystem");
//        firstActorSystem.tell("hey");
//        firstActorSystem.tell("who are you");
//        firstActorSystem.tell("say hello");
//        firstActorSystem.tell("create a child");

        ActorSystem<ManagerBehavior.Command> primes = ActorSystem.create(ManagerBehavior.create(), "primes");
        primes.tell(new ManagerBehavior.InstructionCommand("start"));

    }
}
