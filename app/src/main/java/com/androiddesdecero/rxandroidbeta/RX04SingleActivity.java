package com.androiddesdecero.rxandroidbeta;

import android.os.Bundle;
import android.util.Log;

import java.util.Date;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.MaybeEmitter;
import io.reactivex.MaybeObserver;
import io.reactivex.MaybeOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleObserver;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;

public class RX04SingleActivity extends AppCompatActivity {

    /*
    Recapitulando: Un Observable es una fuente de datos.
    Un observador puede recibir estos datos si se suscribe.
    Hay muchas formas de crear un Observable y mucchos tipos de
    Obdrervable.
    */

    /*
    Tipos de Observables:
    Dependieno de la forma en la que se producen los datos y el número
    de emisiones que produce el observable serán de un tipo u otro.
    Hay que escoger el mejor observable para cada caso de use.

    Tipos de Observable     -----       Tipos de Observers      ----    Número de Emisiones
    Observable              -----       Observer                ----    Múltiple o Ninguno
    Single                  -----       SingleObserver          ----    Uno
    Maybe                   -----       MaybeObserver           ----    Uno o Ninguno
    Flowable                -----       Observer                ----    Multiple o Ninguno
    Completable             -----       CompletableObserver     ----    Ninguno
     */

    /*
    Observable:
    Emite 0 o n items y termina con exito o con un evento de error.
     */

    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx04_single);
        //observableYObserver();
        //singleAndSingleObserver();
        //maybeAndeMaybeObserver();

        flowableAndObserver();
    }

    /*
    Observable y Observer
    Esta es la convinación más utilizada. Como hemos indicado
    el Observable puede emitir o, uno o muchos items.
    En el ejemplo vamos a ver un Observable que emite empleados.
    /*
    Observable:
    Emite 0 o n items y termina con exito o con un evento de error.

     */

    private void observableYObserver(){
        List<Empleado> empleados = Empleado.setUpEmpleados();
        Observable<Empleado> empleadoObservable = Observable.create(
                new ObservableOnSubscribe<Empleado>() {
                    @Override
                    public void subscribe(ObservableEmitter<Empleado> emitter) throws Exception {
                        for(Empleado empleado : empleados){
                            if(!emitter.isDisposed()){
                                emitter.onNext(empleado);
                            }
                        }
                        //Todos los empleados han sido emitidos
                        if(!emitter.isDisposed()){
                            emitter.onComplete();
                        }
                    }
                }
        );
        Observable<Empleado> empleadoObservable1 = Observable.create(
                emitter->{
                    for(Empleado empleado : empleados){
                        if(!emitter.isDisposed()){
                            emitter.onNext(empleado);
                        }
                    }
                    if(!emitter.isDisposed()){
                        emitter.onComplete();
                    }
                }
        );

        Observer<Empleado> empleadoObserver = new Observer<Empleado>(){
            @Override
            public void onSubscribe(Disposable d) {
                Log.d("TAG1", "onSubscribe");
                disposable = d;
            }

            @Override
            public void onNext(Empleado empleado) {
                Log.d("TAG1", "onNext: " + empleado.getNombre());
            }

            @Override
            public void onError(Throwable e) {
                Log.e("TAG1", "onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d("TAG1", "onComplete");
            }
        };

        empleadoObservable1.observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(empleadoObserver);

    }

    /*
    Single solo emite un valor u error. Se podría hacer con un
    observable normal, pero con este siempre nos aseguramos que se emita algo.
    El ejemplo tipio es una llamada a un web service.
    Single no tiene el método onNext
     */
    private void singleAndSingleObserver(){
        Single<Empleado> empleadoSingle = Single.create(new SingleOnSubscribe<Empleado>() {
            @Override
            public void subscribe(SingleEmitter<Empleado> emitter) throws Exception {
                Empleado empleado = new Empleado(1, "Alberto", "Android", new Date(), 1000.0, 100.0);
                emitter.onSuccess(empleado);
            }
        });

        SingleObserver<Empleado> singleObserver= new SingleObserver<Empleado>(){

            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onSuccess(Empleado empleado) {
                Log.d("TAG1", "onSuccess: " + empleado.getNombre());

            }

            @Override
            public void onError(Throwable e) {
                Log.e("TAG1", "onError: " + e.getMessage());

            }
        };

        empleadoSingle.subscribe(singleObserver);
    }

    /*
    Maybe
    MaybeObservable puede o no puede emitir un valor. Es decir que se puede
    utilizar cuando se espera que un elemente sea emitido opcionalmente.
    Ejemplo de Uso cuando solicitamos un usuario a una base de datos
    y este puede existir o no.
     */

    private void maybeAndeMaybeObserver(){

        /*
        puede emitir un dato o ninguno.
         */
        Maybe<Empleado> empleadoMaybe = Maybe.create(new MaybeOnSubscribe<Empleado>() {
            @Override
            public void subscribe(MaybeEmitter<Empleado> emitter) throws Exception {
                Empleado empleado = new Empleado(1, "Alberto", "Android", new Date(), 1000.0, 100.0);
                if(!emitter.isDisposed()){
                    emitter.onSuccess(empleado);
                }

            }
        });

        //Probamos cuando Maybe esta vacio
        Maybe<Empleado> empleadoMaybeEmpty = Maybe.empty();


        MaybeObserver<Empleado> empleadoMaybeObserver = new MaybeObserver<Empleado>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;

            }

            @Override
            public void onSuccess(Empleado empleado) {
                Log.d("TAG1", "onSuccess: " + empleado.getNombre());

            }

            @Override
            public void onError(Throwable e) {
                Log.e("TAG1", "onError: " + e.getMessage());

            }

            @Override
            public void onComplete() {
                Log.d("TAG1", "onComplete: ");

            }
        };

        //empleadoMaybe.subscribe(empleadoMaybeObserver);
        empleadoMaybeEmpty.subscribe(empleadoMaybeObserver);
    }

    /*
    Completable y CompletabeObserver
    El ObservableCompletable  no emitirá ningun dato, en cambio, notifica el estado
    de la tarea, tanto si fue con éxito o error.
    Cuando se desea realizar una tarea en la que no esperemos ningún valor se puede usar.
    Por ejemplo cuando usamos un PUT a la hora de actualizar datos en Servidor.
     */

    private void CompletableAndCompletableObserver(){
        Completable completable = Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(CompletableEmitter emitter) throws Exception {
                if(!emitter.isDisposed()){
                    emitter.onComplete();
                }
            }
        });

        CompletableObserver completableObserver = new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onComplete() {
                Log.d("TAG1", "onComplete: actualizado servidor correctamente!");
            }

            @Override
            public void onError(Throwable e) {
                Log.e("TAG1", "onError: " + e.getMessage());
            }
        };

    }

    /*
    El Flowable Observable debe usarse cuando un observable
    está generando una gran cantidad de datos o evetnos.
    Según la información oficial, esta destinado para jenerar
    más de 10k eventos y por lo tanto el observable no los puede
    consumir. Se emite más rápido de lo que se consume.

     */
    private void flowableAndObserver(){

        Flowable<Integer> flowable = Flowable.range(1,10000);
        SingleObserver<Integer> singleObserver = new SingleObserver<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onSuccess(Integer integer) {
                Log.d("TAG1", "onSuccess: " + integer);
            }

            @Override
            public void onError(Throwable e) {
                Log.e("TAG1", "onError: " + e.getMessage());
            }
        };
        flowable.reduce(0, new BiFunction<Integer, Integer, Integer>() {
            @Override
            public Integer apply(Integer integer, Integer integer2) throws Exception {
                return integer + integer2;
            }
        }).subscribe(singleObserver);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}
