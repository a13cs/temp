package akka;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.SecureRandom;

public class WorkerBehavior extends AbstractBehavior<WorkerBehavior.Command> {

    public static class Command implements Serializable {

        @Serial
        private static final long serialVersionUID = 1L;

        private final String message;
        private final ActorRef<ManagerBehavior.Command> sender;

        public Command(String message, ActorRef<ManagerBehavior.Command> sender) {
            this.message = message;
            this.sender = sender;
        }

        public String getMessage() {
            return message;
        }

        public ActorRef<ManagerBehavior.Command> getSender() {
            return sender;
        }
    }

    private WorkerBehavior(ActorContext<Command> context) {
        super(context);
    }

    public static Behavior<Command> create() {
        return Behaviors.setup(WorkerBehavior::new);
    }

    private BigInteger prime;

    @Override
    public Receive<WorkerBehavior.Command> createReceive() {
        return this.newReceiveBuilder()
                .onAnyMessage( command -> {
                    if ("start".equalsIgnoreCase(command.getMessage())) {
                        if (prime == null) {
                            prime = new BigInteger(2000, new SecureRandom()).nextProbablePrime();
                            System.out.println(this.getContext().getSelf().path() + "\t" + prime);
                        }
                        command.getSender().tell(new ManagerBehavior.ResultCommand(prime, getContext().getSelf()));
                    }

                    return this;
                })
                .build();
    }
}
