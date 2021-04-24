package akka;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.SortedSet;
import java.util.TreeSet;

public class ManagerBehavior extends AbstractBehavior<ManagerBehavior.Command> {

    public interface Command extends Serializable {
        long serialVersionUID = 2L;
    }

    public static class InstructionCommand implements Command {
        private final String message;

        public InstructionCommand(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

    public static class ResultCommand implements Command {
        private final BigInteger result;
        private final ActorRef<WorkerBehavior.Command> sender;

        public ResultCommand(BigInteger result, ActorRef<WorkerBehavior.Command> sender) {
            this.result = result;
            this.sender = sender;
        }

        public BigInteger getResult() {
            return result;
        }

        public ActorRef<WorkerBehavior.Command> getSender() {
            return sender;
        }
    }

    private ManagerBehavior(ActorContext<Command> context) {
        super(context);
    }

    public static Behavior<Command> create() {
        return Behaviors.setup(ManagerBehavior::new);
    }

    private final SortedSet<BigInteger> primes = new TreeSet<>();

    @Override
    public Receive<Command> createReceive() {
        return this.newReceiveBuilder()
                .onMessage(InstructionCommand.class, command -> {

                    if ("start".equalsIgnoreCase(command.message)) {
                        for(int i = 0; i < 20; i++) {
                            ActorRef<WorkerBehavior.Command> spawn = this.getContext().spawn(WorkerBehavior.create(), "worker" + i);
                            spawn.tell(new WorkerBehavior.Command("start", this.getContext().getSelf()));
                            spawn.tell(new WorkerBehavior.Command("start", this.getContext().getSelf()));
                        }
                    }
                    return this;
                })
                .onMessage(ResultCommand.class, command -> {
                    primes.add(command.result);

                    System.out.println("Received: " + primes.size() + " numbers");
//                    System.out.println("Worker: " + command.getSender().path());

                    if (primes.size() == 20 ) primes.forEach(System.out::println);

                    return this;
                })
                .build();
    }
}
