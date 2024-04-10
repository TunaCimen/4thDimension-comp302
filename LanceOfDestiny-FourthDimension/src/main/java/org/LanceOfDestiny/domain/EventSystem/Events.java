package org.LanceOfDestiny.domain.EventSystem;


import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/***
 * Events is an enum that contains all the EventTypes
 * Events are defined in the enum structure as follows-->EventName(InvocationType) bkz. types
 * Listeners can be added via addListener(parameter listeners) or addRunnable(no parameter listeners)
 */
public enum Events {

    LogEvent(String.class),
    LogIntegerEvent(Integer.class),
    ;

    private List<Consumer<Object>> listeners = new ArrayList<>(); //List that listeners subscribe to.
    final Class<?> paramType; //It is the Class that the particular event wants the invocation.
    Events(Class<?> stringClass) {
        paramType = stringClass;
    }

    public void invoke(Object l) throws IllegalEventInvocationException{
            if(!paramType.isAssignableFrom(l.getClass())){
                throw new IllegalEventInvocationException(name() + " expected " + paramType.getName() + "\n"
                        + "Received: "+l.getClass().getName());
            }
            for(Consumer<Object> consumer : listeners){
                try{
                    consumer.accept(l);
                }
                catch(Exception e){
                    System.out.println("Wrong Argument to Invoke for:" + this.name());
                }
            }
    }
    public void addListener(Consumer<Object> subscriber){
        listeners.add(subscriber);
    }
    public void addRunnableListener(Runnable r){
        listeners.add(e-> r.run());
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
