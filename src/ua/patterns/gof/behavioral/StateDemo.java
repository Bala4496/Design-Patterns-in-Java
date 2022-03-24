package ua.patterns.gof.behavioral;

import org.javatuples.Pair;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.statemachine.transition.Transition;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StateDemo {
    //Handmade Statemachine
    private static Map<State, List<Pair<Trigger, State>>> rules = new HashMap<>();

    static {
        rules.put(State.OFF_HOOK, List.of(
                new Pair<>(Trigger.CALL_DIALED, State.CONNECTING),
                new Pair<>(Trigger.STOP_USING_PHONE, State.ON_HOOK)
        ));
        rules.put(State.CONNECTING, List.of(
                new Pair<>(Trigger.HUNG_UP, State.OFF_HOOK),
                new Pair<>(Trigger.CALL_CONNECTED, State.CONNECTED)
        ));
        rules.put(State.CONNECTED, List.of(
                new Pair<>(Trigger.LEFT_MESSAGE, State.OFF_HOOK),
                new Pair<>(Trigger.HUNG_UP, State.OFF_HOOK),
                new Pair<>(Trigger.PLACED_ON_HOLD, State.ON_HOOK)
        ));
        rules.put(State.ON_HOLD, List.of(
                new Pair<>(Trigger.TAKEN_OFF_HOLD, State.CONNECTED),
                new Pair<>(Trigger.HUNG_UP, State.OFF_HOOK)
        ));
    }

    private static State currentState = State.ON_HOOK;
    private static State exitState = State.ON_HOOK;

    //Spring Statemachine
    public static StateMachine<States, Events> buildMachine() throws Exception {
        StateMachineBuilder.Builder<States, Events> builder = StateMachineBuilder.builder();

        builder.configureTransitions()
                .withExternal()
                .source(States.OFF_HOOK)
                .event(Events.CALL_DIALED)
                .target(States.CONNECTING)
                .and()
                .withExternal()
                .source(States.OFF_HOOK)
                .event(Events.STOP_USING_PHONE)
                .target(States.ON_HOOK)
                .and()
                .withExternal()
                .source(States.CONNECTING)
                .event(Events.HUNG_UP)
                .target(States.OFF_HOOK)
                .and()
                .withExternal()
                .source(States.CONNECTING)
                .event(Events.CALL_CONNECTED)
                .target(States.CONNECTED)
                .and()
                .withExternal()
                .source(States.CONNECTED)
                .event(Events.LEFT_MESSAGE)
                .target(States.OFF_HOOK)
                .and()
                .withExternal()
                .source(States.CONNECTED)
                .event(Events.HUNG_UP)
                .target(States.OFF_HOOK)
                .and()
                .withExternal()
                .source(States.CONNECTED)
                .event(Events.PLACED_ON_HOLD)
                .target(States.OFF_HOOK)
                .and()
                .withExternal()
                .source(States.ON_HOLD)
                .event(Events.TAKEN_OFF_HOLD)
                .target(States.CONNECTED)
                .and()
                .withExternal()
                .source(States.ON_HOLD)
                .event(Events.HUNG_UP)
                .target(States.OFF_HOOK);

        return builder.build();

    }

    public static void main(String[] args) throws Exception {
        //Classic
        LightSwitch lightSwitch = new LightSwitch();
        lightSwitch.on();
        lightSwitch.off();
        lightSwitch.off();

        //Handmade Statemachine
//        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
//
//        while (true) {
//            System.out.println("The phone is currently " + currentState);
//            System.out.println("Select a trigger:");
//
//            for (int i = 0; i < rules.get(currentState).size(); i++)
//            {
//                Trigger trigger = rules.get(currentState).get(i).getValue0();
//                System.out.println("" + i + ". " + trigger);
//            }
//
//            boolean parseOK;
//            int choice = 0;
//            do {
//                try {
//                    System.out.println("Please entry your choice:");
//                    choice = Integer.parseInt(console.readLine());
//                    parseOK = choice >= 0 && choice < rules.get(currentState).size();
//                } catch (IOException e) {
//                    parseOK = false;
//                }
//            } while (!parseOK);
//
//            currentState = rules.get(currentState).get(choice).getValue1();
//            if (currentState == exitState) break;
//        }
//        System.out.println("We are done!");

        //Spring Statemachine
        StateMachine<States, Events> machine = buildMachine();
        machine.start();

        States exitState = States.ON_HOOK;

        BufferedReader console = new BufferedReader(
                new InputStreamReader(System.in)
        );

        while (true)
        {
            org.springframework.statemachine.state.State<States, Events> state = machine.getState();

            System.out.println("The phone is currently " + state.getId());
            System.out.println("Select a trigger:");

            List<Transition<States, Events>> ts = machine.getTransitions()
                    .stream()
                    .filter(t -> t.getSource() == state).toList();
            for (int i = 0; i < ts.size(); ++i)
            {
                System.out.println("" + i + ". " +
                        ts.get(i).getTrigger().getEvent());
            }

            boolean parseOK;
            int choice = 0;
            do
            {
                try
                {
                    System.out.println("Please enter your choice:");

                    choice = Integer.parseInt(console.readLine());
                    parseOK = choice >= 0 && choice < ts.size();
                }
                catch (Exception e)
                {
                    parseOK = false;
                }
            } while (!parseOK);

            // perform the transition
            //machine.sendEvent(ts.get(choice).getTrigger().getEvent());

            if (machine.getState().getId() == exitState)
                break;
        }
        System.out.println("And we are done!");
    }
}

//Classic Implementation
class ClassicState {
    void on(LightSwitch ls) {
        System.out.println("Light is already on");
    }

    void off(LightSwitch ls) {
        System.out.println("Light is already off");
    }
}

class OnState extends ClassicState {
    public OnState() {
        System.out.println("Light turned on");
    }

    @Override
    void off(LightSwitch ls) {
        System.out.println("Switching light off...");
        ls.setState(new OffState());
    }
}

class OffState extends ClassicState {
    public OffState() {
        System.out.println("Light turned off");
    }

    @Override
    void on(LightSwitch ls) {
        System.out.println("Switching light on...");
        ls.setState(new OnState());
    }
}

class LightSwitch {
    private ClassicState state;

    public LightSwitch() {
        state = new OffState();
    }

    void on(){
        state.on(this);
    }

    void off(){
        state.off(this);
    }

    public void setState(ClassicState state) {
        this.state = state;
    }
}

//Handmade state machine
enum State {
    OFF_HOOK,
    ON_HOOK,
    CONNECTING,
    CONNECTED,
    ON_HOLD
}

enum Trigger { //Events
    CALL_DIALED,
    HUNG_UP,
    CALL_CONNECTED,
    PLACED_ON_HOLD,
    TAKEN_OFF_HOLD,
    LEFT_MESSAGE,
    STOP_USING_PHONE
}

//Spring Statemachine
enum States {
    OFF_HOOK,
    ON_HOOK,
    CONNECTING,
    CONNECTED,
    ON_HOLD
}

enum Events {
    CALL_DIALED,
    HUNG_UP,
    CALL_CONNECTED,
    PLACED_ON_HOLD,
    TAKEN_OFF_HOLD,
    LEFT_MESSAGE,
    STOP_USING_PHONE
}

