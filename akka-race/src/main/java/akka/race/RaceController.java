package akka.race;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

import java.io.Serial;
import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class RaceController extends AbstractBehavior<RaceController.Command> {

    public interface Command extends Serializable { }

    public static class StartCommand implements Command {
        @Serial
        public static final long serialVersionUID = 3845697405788723232L;
    }
    public static class RacerUpdateCommand implements Command {
        @Serial
        public static final long serialVersionUID = 4846384021993731920L;
        private ActorRef<Racer.Command> racer;
        private int position;
        private long finishTime;

        public RacerUpdateCommand(ActorRef<Racer.Command> racer, int position, long finishTime) {
            this.racer = racer;
            this.position = position;
            this.finishTime = finishTime;
        }
    }

    private class GetPositionsCommand implements Command {
        @Serial
        public static final long serialVersionUID = 8363260728279864368L;
    }


    private RaceController(ActorContext<RaceController.Command> context) {
        super(context);
    }

    public static Behavior<Command> create() {
        return Behaviors.setup(RaceController::new);
    }

    private Map<ActorRef<Racer.Command>, Integer> currentPositions;
    private Map<ActorRef<Racer.Command>, Long> finishingTimes;
    private long start;  // time
    private final int raceLength = 100;
    private final int racers = 10;

    private static final Object TIMER_KEY = new Object();

    private final  int displayLength = 100;

    private void displayRace(final Map<ActorRef<Racer.Command>, Integer> currentPositions) {
        for (int i = 0; i < 50; i++) {
            //  clear
            System.out.println();
        }
        System.out.println("Time running: " + (Instant.now().getEpochSecond() - start) + " seconds");
        for (ActorRef<Racer.Command> racer : currentPositions.keySet()) {
            System.out.println(racer.path() + " : " + new String(new char[currentPositions.get(racer) * displayLength / 100]).replace('\0', '*'));
        }
    }

    @Override
    public Receive<RaceController.Command> createReceive() {
        return newReceiveBuilder()
                .onMessage(StartCommand.class, message -> {
                    start = Instant.now().getEpochSecond();
                    currentPositions = new HashMap<>();
                    finishingTimes = new LinkedHashMap<>();

                    for (int i = 0; i < racers; i++) {
                        ActorRef<Racer.Command> racer = getContext().spawn(Racer.create(),"racer" + i);
                        currentPositions.put(racer, 0);
                        racer.tell(new Racer.StartCommand(raceLength));
                    }
                    return Behaviors.withTimers(timer -> {
                        timer.startTimerAtFixedRate(TIMER_KEY, new GetPositionsCommand(),Duration.ofMillis(100));
                        return this;
                    });
                })
                .onMessage(RacerUpdateCommand.class, message -> {
                    currentPositions.put(message.racer, message.position);
                    if (message.finishTime != 0) finishingTimes.put(message.racer, message.finishTime);
                    return this;
                })
                .onMessage(GetPositionsCommand.class, message -> {
                    for (ActorRef<Racer.Command> racer : currentPositions.keySet()) {
                        racer.tell(new Racer.PositionCommand(getContext().getSelf()));
                    }
                    displayRace(currentPositions);

                    if (finishingTimes.size() == racers) {
                        return finished();
                    }
                    return this;
                })
                .build();
    }

    public Receive<Command> finished() {
        return newReceiveBuilder()
                .onAnyMessage( message -> {
                    getContext().getLog().info("Results:");
                    System.out.println();
                    finishingTimes.forEach( (k,v) -> System.out.println(k.path() + " finished in " + (v - start) + "s") );

                    return Behaviors.withTimers(timers -> {
                       timers.cancelAll();
                       return Behaviors.stopped();
                    });
                })
                .build();

    }

}
