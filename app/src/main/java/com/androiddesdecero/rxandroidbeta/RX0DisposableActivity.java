package com.androiddesdecero.rxandroidbeta;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RX0DisposableActivity extends AppCompatActivity {

    /*
        --------------------------------------------------------------------------
                Disposable
        --------------------------------------------------------------------------
        Disposable -> Desechable es basicamente para desechar la subscripción cuando
         un observador ya no quiere escuchar al Observable.
         En Android los Disposables nos van a permitir gestionar los memory leak
         fugas de memoria de una manera sencilla como veremos más adelante.
         Ejemplo de Fuga de memoria-> Estas en una activity de tu aplicación, haces una
         llamada a un servicio web. Antes de que los datos te llegen a la aplicación
         ya has cambiado de activity, así que tienes el observer todavía escuchando
         aunque ya no le valen para nada los datos. Con Disposble podemos dejar de
         escuchar al observable en cualquier momento, por ejemplo cuando se llame al
         método onDestroy de nuestra activity.
        */

    private Observer<String> numerosObserver;
    private Disposable disposable;
    private Observable<String> numerosObservable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx0_disposable);

        numerosObservable = getNumerosObservable();
        numerosObserver = getNumerosObserver();


        numerosObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(numerosObserver);
    }

    private Observable<String> getNumerosObservable(){
        return Observable.just("1", "2", "3", "4", "5",
                "6", "7", "8", "9", "10");
    }

    private Observer<String> getNumerosObserver(){
        return  new Observer<String>() {
           // onSubscribe()-> Se llamará al método onSubscribe cuando un observador
            //se suscriba a un Observable.
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
                Log.d("TAG1", "-------------------Disposable----------------------");
                Log.d("TAG1", "onSubscribe" + " Hilo: " + Thread.currentThread().getName());
            }

            @Override
            public void onNext(String s) {
                Log.d("TAG1", "on Next: Numero: " + s + " Hilo: " + Thread.currentThread().getName());

            }

            @Override
            public void onError(Throwable e) {
                Log.e("TAG1", "onError: " + e.getMessage() + " Hilo: " + Thread.currentThread().getName());

            }

            @Override
            public void onComplete() {
                Log.d("TAG1", "onComplete: Sen han emitidos todos los datos!" + " Hilo: " + Thread.currentThread().getName());
            }
        };
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Una vez la activity
        disposable.dispose();
        Log.d("TAG1", "onDestroy. Desechamos la conexion Onbservable Orserver" + " Hilo: " + Thread.currentThread().getName());

    }
}
