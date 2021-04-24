package akka.race;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.PostStop;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.Random;

public class Racer extends AbstractBehavior<Racer.Command> {

    public interface Command extends Serializable { }

    public static class StartCommand implements Command {
        @Serial
        private static final long serialVersionUID = -290240790268582059L;
        private final int raceLength;

        public StartCommand(int raceLength) {
            this.raceLength = raceLength;
        }

    }

    public static class PositionCommand implements Command {
        @Serial
        private static final long serialVersionUID = 1666450922957188927L;
        private final ActorRef<RaceController.Command> controller;

        public PositionCommand(ActorRef<RaceController.Command> controller) {
            this.controller = controller;
        }
    }

    private Racer(ActorContext<Command> context) {
        super(context);
    }

    public static Behavior<Command> create() {
        return Behaviors.setup(Racer::new);
    }


    private final double defaultAverageSpeed = 48.2;
    private int averageSpeedAdjustmentFactor;
    private Random random;

    private double currentSpeed = 0;
//    private double currentPosition = 0;

    private int raceLength;

    private double getMaxSpeed() {
        return defaultAverageSpeed * (1+ ((double)averageSpeedAdjustmentFactor / 100));
    }

    private double getDistanceMovedPerSecond() {
        return currentSpeed * 1000 / 3600;
    }

    private void determineNextSpeed(int currentPosition, int raceLength) {
        if (currentPosition < (raceLength /4)) {
            currentSpeed += ((getMaxSpeed() - currentSpeed) / 10 ) * random.nextDouble();
        } else {
            currentSpeed = currentSpeed * (0.5 + random.nextDouble());
        }

        if (currentSpeed > getMaxSpeed()) currentSpeed = getMaxSpeed();
        if (currentSpeed < 5) currentSpeed = 5;

        if (currentSpeed > raceLength/2 && currentSpeed < getMaxSpeed()/2) currentSpeed = getMaxSpeed()/2;
    }

    @Override
    public Receive<Command> createReceive() {
        return notStarted();
    }

    public Receive<Command> notStarted() {
        return newReceiveBuilder()
                .onMessage(StartCommand.class, message -> {
                    random = new Random();
                    averageSpeedAdjustmentFactor = random.nextInt(30) - 10;  // -10 , 20

                    System.out.println("started " + getContext().getSelf().path());
                    return running(message.raceLength, 0);
                })
                .onMessage(PositionCommand.class, message -> {
                    //  tell controller  position 0
                    message.controller.tell(new RaceController.RacerUpdateCommand(getContext().getSelf(), 0, 0L));

                    return running(raceLength, 0);
                })
                .build();
    }

    public Receive<Command> running(int raceLength, int currentPosition) {
        return newReceiveBuilder()
                .onMessage(PositionCommand.class, message -> {
                    int newPosition = currentPosition + (int)getDistanceMovedPerSecond();
                    determineNextSpeed(currentPosition, raceLength);
                    //  tell controller current position
                    message.controller.tell(new RaceController.RacerUpdateCommand(getContext().getSelf(), newPosition, 0L));
                    if (currentPosition >= raceLength) {
                        return completed(raceLength, Instant.now().getEpochSecond());
                    } else
                        return running(raceLength, newPosition);
                })
                .build();
    }

    public Receive<Command> completed(final int raceLength, long finishTime) {
        return newReceiveBuilder()
                .onMessage(PositionCommand.class, message -> {
                    message.controller.tell(new RaceController.RacerUpdateCommand(getContext().getSelf(), raceLength, finishTime));

//                    return Behaviors.ignore();
                    return waitingToStop();
                })
                .build();
    }

    public Receive<Command> waitingToStop() {
        return newReceiveBuilder()
                .onAnyMessage( message -> Behaviors.same() )
                .onSignal(PostStop.class, signal -> {
                    getContext().getLog().info("finishing..");

                    return Behaviors.same();
                })
                .build();
    }


}
