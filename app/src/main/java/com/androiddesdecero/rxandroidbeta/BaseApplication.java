package com.androiddesdecero.rxandroidbeta;

import android.app.Application;

public class BaseApplication extends Application {

    private RX06RXBus bus;


    @Override
    public void onCreate() {
        super.onCreate();
        bus = new RX06RXBus();
    }

    public RX06RXBus bus(){
        return bus;
    }
}
