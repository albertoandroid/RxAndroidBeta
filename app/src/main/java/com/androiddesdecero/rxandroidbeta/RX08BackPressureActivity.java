package com.androiddesdecero.rxandroidbeta;

import android.os.Bundle;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

public class RX08BackPressureActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_back_pressure);

        /*
        Cuando utilizamos RX no es dificil que nos encontremos en la situación
        en la que un observable está emitiendo items más rapidamente
        de los que el operador los puedes consumir.

        El problema que nos encontramos es el siguiente, que hacer
        con la acumulación creciente de items consumidos.
        Esto es lo que se conoce como BackPresuper.
        En el primer ejemplo veremos una forma manual de crear
        backpresure y en los siguientes capitulos como gestionarlo.
         */

        /*
        En este ejemplo el el Hilo principal produce 1 millon de itens
        que son consumidos en el hilo de backgorun.
        El método operacion consume tiempo.
        Por lo tanto se emiten más rapido los items, que este puede
        controlarlos.
        Como el bucle for que produce los items no lo sabe, sigue con
        su ritmo de producción normal.
        Por norma general hay un buffer que puede aguantar los
        elementos hasta que se consuman todos, pero no podemos
        tenerlo eternamente ocupando recursos.

         */

        //generandoBackPresure();
        backPresureBuffer();
        //backPresureDrop();

    }



    private void generandoBackPresure(){
        PublishSubject<Integer> source = PublishSubject.create();
        source
                .observeOn(Schedulers.computation())
                .subscribe(
                        e-> operacionLarga(e),
                        e-> Log.d("TAG1", "onError" + e),
                        ()-> Log.d("TAG1", "onComplete"));


        for (int i = 0; i < 10; i++) {
            source.onNext(i);
            Log.d("TAG1", "creando Observable: " + i);
        }
        source.onComplete();

    }



    private void operacionLarga(int entero){
        try {
            //Hacemos que consumir un item sea de por lo menos un segundo.
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("TAG1", "Consumiendo Observable: " + entero);
    }

    /*
    Hay una gran variedad de estrategis con las que podemos controlar
    el control de flujo de RX para evitar el problema de que el
    observable produzca items de una manera más rapida de la que el observador
    los puede consumir.
    Los dos más importantes son Buffer y Drop y será los que vamos a ver.
     */

    /*
    Usamos la estrategia Buffer, y la fuente almacenará todos los eventos
    hasta que el subcriptor pueda consumirlos.
    Es decir tenenos un buffer sin limites. Eso si, el limite lo marcara
    la Java Virtual Machine si se queda sin memmoria.
    Es decir una vez el buffer esta lleno se descartan los items que excedan
    del tamaño del buffer.
     */
    private void backPresureBuffer(){
        Flowable<Long> source = Flowable.interval(1, TimeUnit.MILLISECONDS);
        source
                .onBackpressureBuffer(1000)
                .observeOn(Schedulers.computation())
                .subscribe(e -> {
                            try {
                                Log.d("TAG1", "Consumiendo Observable: " + e);
                                Thread.sleep(100);
                            } catch (Exception ex) {
                            }
                        },
                        x->Log.d("TAG1", x.toString()));

    }
    //https://github.com/Froussios/Intro-To-RxJava/blob/master/Part%204%20-%20Concurrency/4.%20Backpressure.md
    /*
    Usamos drop para descartar los eventos que no se pueden
    consumir en lugar de almacenarlos en el buffer
    Ejemplo generamos item cada 100ms, pero se tarda 1000ms en procesarlos
     */
    private void backPresureDrop() {

        Flowable<Long> source = Flowable.interval(1, TimeUnit.MILLISECONDS);
        source
                .onBackpressureDrop()
                .observeOn(Schedulers.computation())
                .subscribe(e -> {
                            try {
                                Log.d("TAG1", "Consumiendo Observable: " + e);
                                Thread.sleep(100);
                            } catch (Exception ex) {
                            }
                        },
                        Throwable::printStackTrace);
    }



}
