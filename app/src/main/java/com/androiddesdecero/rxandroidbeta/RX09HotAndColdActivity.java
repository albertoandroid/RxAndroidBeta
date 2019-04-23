package com.androiddesdecero.rxandroidbeta;

import android.os.Bundle;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.Observable;
import io.reactivex.observables.ConnectableObservable;

public class RX09HotAndColdActivity extends AppCompatActivity {

    /*
    Cold and Hot Observable
    Los observables se clasifican en términos generales
    como Hot and Cold dependiendo de su comportamiento.
    Un observable frio es uno que comienza a emitir bajo
    pedido de un Observer. Es decir cuando hay subscripcion.
    Un observable Hot es alque que emite independientemente
    de tenga subscriptores o no.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx09_hot_and_cold);
        //coldObservable();
        hotObservable();
    }

    /*
    Aunque el subscritor numero 2 empieza un segundo más tarde,
    es decir cuando ya se han emitido tres valores, recibe los
    valores desde el principio.
    Es decir que multiples solicitudes inician multiples pipelines.
    Los elementos son emitidos bajo solicitud.
     */
    private void coldObservable(){
        Observable<Long> cold = Observable.interval(500, TimeUnit.MILLISECONDS);
        cold.subscribe(e-> Log.d("TAG1", "Subcriber Numero 1: " + e));
        try {
            Thread.sleep(1000); // interval between the two subscribes
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        cold.subscribe(e-> Log.d("TAG1", "Subcriber Numero 2: " + e));
    }

    /*
    Los observables hot emiten valores independiente de las subscripciones
    individuales. Ellos tienen su propia linea de tiempo y eventos ocurren
    igualmente si alguien esta escuchando o no.
    Un Cold Observable pude pasar a ser Hot con Publish.
    Publish devuelve un ConnectableObservable que agrega
    las funcionalidades para conectarse y desconectarse del observable.
     */
    private void hotObservable(){
        Observable.interval(500, TimeUnit.MILLISECONDS)
                .publish(); // publish converts cold to hot


        ConnectableObservable<Long> hot = Observable
                .interval(500, TimeUnit.MILLISECONDS)
                .publish(); // returns ConnectableObservable
        hot.connect(); // connect to subscribe
        hot.subscribe(e-> Log.d("TAG1", "Subcriber Numero 1: " + e));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        hot.subscribe(e-> Log.d("TAG1", "Subcriber Numero 2: " + e));
    }
}
