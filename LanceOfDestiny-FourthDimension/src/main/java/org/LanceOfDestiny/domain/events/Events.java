package org.LanceOfDestiny.domain.events;

import org.LanceOfDestiny.domain.physics.Collision;
import org.LanceOfDestiny.domain.spells.SpellType;

import javax.swing.*;
import java.awt.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/***
 * Events is an enum that contains all the EventTypes
 * Events are defined in the enum structure as follows-->EventName(InvocationType) bkz. types
 * Listeners can be added via addListener(parameter listeners) or addRunnable(no parameter listeners)
 *
 * Timed Events:
 * You can define a timed event by specifying an Events enum with the parameters:
 * @Param -> Class with which the event will be invoked.
 * @Param -> Duration of the event.
 * @Param -> Follow-up event you want to invoke.//TODO:It can be optional(?).
 */
public enum Events {
    LogEvent(String.class),
    LogIntegerEvent(Integer.class),
    CollisionEvent(Collision.class),
    //MAGICAL STAFF EVENTS
    MoveStaff(Integer.class),
    RotateStaff(Double.class),
    ResetStaff(Object.class),
    //FIREBALL EVENTS
    ShootBall(Object.class),
    ResetFireBall(Object.class),
    // Events after opponent is notified
    ReceiveChanceUpdate(Integer.class),
    ReceiveScoreUpdate(Integer.class),
    ReceiveBarrierCountUpdate(Integer.class),
    // Events to notify the opponent
    SendChanceUpdate(Object.class),
    SendScoreUpdate(Object.class),
    SendBarrierCountUpdate(Object.class),
    // GAME STAT. EVENTS
    // The second args are events that are invoked after all the listeners of the actual event are invoked
    UpdateChance(Integer.class,SendChanceUpdate),
    UpdateBarrierCount(Object.class, SendBarrierCountUpdate),
    UpdateScore(Object.class, SendScoreUpdate),
    EndGame(String.class),
    //SPELL EVENTS
    GainSpell(SpellType.class),
    TryUsingSpell(SpellType.class),
    ActivateSpellUI(SpellType.class),
    //GOOD SPELLS
    ActivateCanons(Boolean.class),
    ActivateOverwhelming(Boolean.class),
    ActivateExpansion(Boolean.class),
    //BAD SPELLS
    TryUsingCurse(SpellType.class),
    ActivateHollowPurple(Object.class),
    ActivateInfiniteVoid(Boolean.class),
    ActivateDoubleAccel(Boolean.class),
    // EVENTS RELATED TO OTHER GAME FEATURES
    PauseGame(Object.class),
    ResumeGame(Object.class),
    StartGame(Object.class),
    SaveGame(Object.class),
    LoadGame(Object.class),
    WaitEvent(Object.class),
    ResetColorEvent(Object.class),
    TimedTestEvent(Color.class, ResetColorEvent),
    CanvasUpdateEvent(Object.class),
    BuildDoneEvent(Object.class),
    Reset(Object.class),
    Load(Object.class),
    ResetSpells(Object.class),
    ReturnStartScreen(Object.class),
    SingleplayerSelected(Object.class),
    MultiplayerSelected(Object.class);

    //It is the Class that the particular event wants the invocation.
    final Class<?> paramType;
    Timer timer = null;
    boolean isActive = false;
    //List that listeners subscribe to.
    private List<Consumer<Object>> listeners = new ArrayList<>();
    Events onFinishEvent;
    Events(Class<?> stringClass) {
        paramType = stringClass;
    }

    Events(Class<?> stringClass, Events onFinish) {
        paramType = stringClass;
        onFinishEvent = onFinish;
    }

    public static void clearAllListeners() {
        for (Events e : values()) {
            e.clearListeners();
        }
    }

    public void invoke(Object l) {
        if (timer == null) {
            invokeUntimed(l);
        } else {
            if (isActive) {
                System.out.println("Already invoked wait!!!!!");
                return;
            }
            System.out.println("Started Time Event " + LocalTime.now().getSecond());
            isActive = true;
            timer.start();
            for (Consumer<Object> consumer : listeners) {
                consumer.accept(l);
            }
        }
    }

    private void invokeUntimed(Object l) throws IllegalEventInvocationException {
        if (!paramType.isAssignableFrom(l.getClass())) {
            throw new IllegalEventInvocationException(name() + " expected " + paramType.getName() + "\n" + "Received: " + l.getClass().getName());
        }
        for (Consumer<Object> consumer : listeners) {
            try {
                consumer.accept(l);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Wrong Argument to Invoke for:" + this.name() + "\n" + "Check your casting for listeners.Note:Tunic");
            }
        }
        if(onFinishEvent != null){
            onFinishEvent.invoke();
        }
    }

    public void invoke() {
        for (Consumer<Object> consumer : listeners) {
            try {
                consumer.accept(new Object());
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Wrong Argument to Invoke for:" + this.name() + "\n" + "Check your casting for listeners.Note:Tunic");
            }
        }
    }

    public void addListener(Consumer<Object> subscriber) {
        listeners.add(subscriber);
    }

    public void addRunnableListener(Runnable r) {
        listeners.add(e -> r.run());
    }

    public void removeListener(Consumer<Object> subscriber) {
        listeners.remove(subscriber);
    }

    public void clearListeners() {
        listeners.clear();
    }

}
