package com.androiddesdecero.rxandroidbeta;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class RX02CompositeDispsableActivity extends AppCompatActivity {

    /*
    A la hora de trabajar con RX lo normal es tener varios Observadores
    y observables. Y hay que tener en cuenta que sino queremos generar
    memory leak deberemos deshacer la relación entre ellos.
    Hacerlo uno por una en el metodo onDestroy puede ser bastante
    tedioso y nos podriamos dejar alguno. Por ello tenemos
    CompositeDisposible
    Compositedispossable -> Nos permite mantener una lista de subscripciones
    en un grupo y podemos borrarlas todas a la vez.
    El metodo que permite borrar es compositeDisposble.clear().
     */

    /*
    En este ejemplo vamos a liberar todos los observadores a través de
    Composite Disposable.
     */


    DisposableObserver<String> numerosObserverPar;
    DisposableObserver<String> numerosObserverParPor10;

    private CompositeDisposable compositeDisposable;
    private Observable<String> numerosObservable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx02_composite_dispsable);

        numerosObservable = getNumerosObservable();
        numerosObserverPar = getNumerosObserverPar();
        numerosObserverParPor10 = getNumerosObserverParPor10();
        compositeDisposable = new CompositeDisposable();

        //Añadimos 1 Composite Disposable
        /*
        Operadores en RX
        Una de las ventajas de RX es que nos permite transformar los datos
        emitidos por los observables. Esto es una gran ventaja, puesto que a la vez que los
        datos son recibidos son transformados.
                Muy parecido a los Streamns que nos encontramos en Java8
        Al final podmeos modificar, fusionar, filtrar o agrupar los flujos de datos.
        RX nos ofrece una infinidad de operadores que nos permiten hacer
        casi cualquier cosa.

        1,. El Operador Filter filtra datos aplicacando una
        declaración condicional. Es decir el dato que cumpla con dicha
        condición se emite y el resto no.
                Vamos a filtrar los número pares.
                */
        compositeDisposable.add(
                numerosObservable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .filter(new Predicate<String>() {
                            @Override
                            public boolean test(String s) {
                                return Integer.parseInt(s) % 2 == 0;
                            }
                        })
                        .subscribeWith(numerosObserverPar));


        compositeDisposable.add(
                numerosObservable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .filter(new Predicate<String>() {
                            @Override
                            public boolean test(String s) throws Exception {
                                return Integer.parseInt(s) % 2 == 0;
                            }
                        })
                        .map(new Function<String, String>() {
                            @Override
                            public String apply(String s) throws Exception {
                                return String.valueOf(Integer.parseInt(s) * 10);
                            }
                        })
                        .subscribeWith(numerosObserverParPor10));
    }

    private Observable<String> getNumerosObservable() {
        return Observable.just("1", "2", "3", "4", "5",
                "6", "7", "8", "9", "10");
    }

    private DisposableObserver<String> getNumerosObserverPar() {
        return new DisposableObserver<String>() {

            @Override
            public void onNext(String s) {
                Log.d("TAG1", "on Next: Numero Par: " + s + " Hilo: " + Thread.currentThread().getName());

            }

            @Override
            public void onError(Throwable e) {
                Log.e("TAG1", "onError: " + e.getMessage() + " Hilo: " + Thread.currentThread().getName());

            }

            @Override
            public void onComplete() {
                Log.d("TAG1", "onComplete Par: Sen han emitidos todos los datos!" + " Hilo: " + Thread.currentThread().getName());
            }
        };
    }

    private DisposableObserver<String> getNumerosObserverParPor10() {
        return new DisposableObserver<String>() {

            @Override
            public void onNext(String s) {
                Log.d("TAG1", "on Next: Numero Par Por 10: " + s + " Hilo: " + Thread.currentThread().getName());

            }

            @Override
            public void onError(Throwable e) {
                Log.e("TAG1", "onError: " + e.getMessage() + " Hilo: " + Thread.currentThread().getName());

            }

            @Override
            public void onComplete() {
                Log.d("TAG1", "onComplete Par Por 10: Sen han emitidos todos los datos!" + " Hilo: " + Thread.currentThread().getName());
            }
        };
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        // don't send events once the activity is destroyed
        compositeDisposable.clear();
    }
}
