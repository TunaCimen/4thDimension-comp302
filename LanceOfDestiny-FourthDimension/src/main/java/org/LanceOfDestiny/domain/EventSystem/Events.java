package org.LanceOfDestiny.domain.EventSystem;


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

    MoveStaff(Integer.class), // Right Now I dont care Integer or Not I might create different.

    RotateStaff(Double.class),
    ResetStaff(Object.class),

    UpdateChance(Integer.class), // invoked with parameter -> change: change in chances
    // ui will be subscriber of this event to show the chances at hand but it is also fine to just use player.getChances()

    PauseGame(Object.class),
    ResumeGame(Object.class),

    UpdateScore(Integer.class), //invoked with parameter -> change: change in score

    GainSpell(SpellType.class), // ui will be subscriber of this event to show the spells at hand
    TryUsingSpell(SpellType.class), // invoked when the player inputs spell related keys, does not concern ui.

    // These three spell activation events concern ui:
    // invoked with true when player actually activates the spell and false when deactivated
    ActivateCanons(Boolean.class),
    ActivateOverwhelming(Boolean.class),
    ActivateExpansion(Boolean.class),

    LoseGame(Object.class),
    WinGame(Object.class),

    SaveGame(Object.class),
    LoadGame(Object.class),

    ResetFireBall(Object.class),
    WaitEvent(Object.class),
    ResetColorEvent(Object.class),
    TimedTestEvent(Color.class, 2000,ResetColorEvent);

    private List<Consumer<Object>> listeners = new ArrayList<>(); //List that listeners subscribe to.
    private List<Consumer<Object>> followerList = new ArrayList<>();


    Timer timer = null;
    boolean isActive = false;
    final Class<?> paramType; //It is the Class that the particular event wants the invocation.
    Events(Class<?> stringClass) {
        paramType = stringClass;
    }
    Events(Class<?> stringClass,int duration,Events onFinish){
            paramType = stringClass;
            timer = new Timer(duration,e->{
                onFinish.invoke();
                System.out.println(LocalTime.now().getSecond());
                isActive = false;
                ((Timer)e.getSource()).stop();

            });
    }

    public void invoke(Object l){
        if(timer == null){
            invokeUntimed(l);
        }
        else{
            if(isActive){
                System.out.println("Already invoked wait!!!!!");
                return;
            }
            System.out.println("Started Time Event " + LocalTime.now().getSecond());
            isActive = true;
            timer.start();
            for(Consumer<Object> consumer : listeners){
                consumer.accept(l);
            }
        }


    }
    private void invokeUntimed(Object l) throws IllegalEventInvocationException{
            if(!paramType.isAssignableFrom(l.getClass())){
                throw new IllegalEventInvocationException(name() + " expected " + paramType.getName() + "\n"
                        + "Received: "+l.getClass().getName());
            }
            for(Consumer<Object> consumer : listeners){
                try{
                    consumer.accept(l);
                }
                catch(Exception e){
                    System.out.println("Wrong Argument to Invoke for:" + this.name() + "\n" + "Check your casting for listeners.Note:Tunic");
                }
            }
    }

    public void invoke(){
        for(Consumer<Object> consumer : listeners){
            try{
                consumer.accept(new Object());
            }
            catch(Exception e){
                System.out.println("Wrong Argument to Invoke for:" + this.name() + "\n" + "Check your casting for listeners.Note:Tunic");
            }
        }
    }
    public void addListener(Consumer<Object> subscriber){
        listeners.add(subscriber);
    }
    public void addRunnableListener(Runnable r){
        listeners.add(e-> r.run());
    }

    public void addFollowerListener(){

    }

    public void removeListener(Consumer<Object> subscriber){
        listeners.remove(subscriber);}

    public void clearListeners(){
        listeners.clear();
    }

    public static void clearAllListeners(){
        for(Events e: values()){
            e.clearListeners();
        }
    }

}
