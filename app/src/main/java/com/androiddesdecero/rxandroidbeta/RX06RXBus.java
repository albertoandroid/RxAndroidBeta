package com.androiddesdecero.rxandroidbeta;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class RX06RXBus {

    private static RX06RXBus instance;

    private PublishSubject<Object> bus = PublishSubject.create();

    public static RX06RXBus getInstance(){
        if(instance == null){
            instance = new RX06RXBus();
        }
        return instance;
    }

    /**
     * Pass any event down to event listeners.
     */
    public void setEvents(Object message){
        bus.onNext(message);
    }

    /**
     * Subscribe to this Observable. On event, do something
     * e.g. replace a fragment
     */
    public Observable<Object> getEvents(){
        return bus;
    }
}
