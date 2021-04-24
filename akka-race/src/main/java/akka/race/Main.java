package akka.race;

import akka.actor.typed.ActorSystem;

public class Main {

    public static void main(String[] args) {
        System.out.println("start");

        ActorSystem<RaceController.Command> controller = ActorSystem.create(RaceController.create(), "race-controller");
        controller.tell(new RaceController.StartCommand());

    }

}
