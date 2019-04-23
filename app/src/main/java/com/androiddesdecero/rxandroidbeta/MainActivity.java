package com.androiddesdecero.rxandroidbeta;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
/*
https://www.androidhive.info/RxJava/tutorials/

¿Qué es la programación reactiva?
1.- La programación reactiva es básicamente una programación asíncrona basada en eventos.
2.- La programación reactiva se vasa en un flujo de datos asíncrono(streams de data),
por lo tanto puede ser observado y cuando se emitan esos datos se llevará a cabo una
acción con ellos.
3.- Podemos crear un flujo de datos de cualquier cosa en programación
    Flujo de datos  de cambios de variables
    Flujo de datos de llamadadas a un web service
    Flujo de datos de una base de datos
    Flujo de datos de eventos de clic.
4.- Gracias a la programación reactiva, podemos combiar, crear, filtrar esos flujos
de datos y esto le da un gran pontencial a la programción reactiva.
5.- Lo importante es saber como funciona la programación asíncrona y esta se
basa en que cada módulo de código se ejecuta en su propio hilo, por lo tanto nos permite
ejecutar múltiples modulos de código simultaneamente.
6.- Una de las ventajas principales de la programación reactiva en la programación
para aplicaciones móviles Android es que podemos ejecutar todo el código que no
afecta a la interfaz de usuario en un hilo distinto al principal, esto significa que el
hilo principal solo lo vamos a utilizar para pintar la pantalla y por lo tanto, cualquier
tarea larga o corta se ejecutará en otro hilo y daremos una experiencia de usuario
perfecta. La aplicación siempre lista para el usuario.
--------------------------------------------------------------------------
                                    RXJava
--------------------------------------------------------------------------
RxJava es la implementación en Java de la programación reactiva.
RxJava es una biblioteca que compone eventos asíncronos siguiendo el
patrón observador.
Básicamente nos puede crear un flujo de datos asíncrono en cualquier subproceso,
tambien nos permite transformar los datos y ser consumidos por un observador.


La biblioteca de RXAndroid nos ofrece una serie de operadores, como son los maps,
filtros, y mucho más que se puede aplicar a un flujo de datos.
--------------------------------------------------------------------------
                                    RXAndroid
--------------------------------------------------------------------------
RXAndroid es una implementación para la plataforma Android de la programación Reactiva.
Es un añadido a RXJava. Pero como hemos dicho con cosas especificas para android.
Aunque lo veremos más adelante, RXAndroid nos ofrece los Schedulers
(AndroidSchedulers.mainThread ()) que serán fundamentales en la programación
multihilo de nuestras aplicaciones Android.
Con los Schedulers le podemos decir a la aplicación que parte del código se hara
en hilo principal y que parte del código se hará en hilos de background.
--------------------------------------------------------------------------
                        Lista de Schedulers para Android
--------------------------------------------------------------------------
Los sheculer son uno de los principales componentes de RX. Son responsables
de realizar las operaciones del Observable en diferentes Hilos.
Schedulers.io()-> Este Sheduler se utiliza para operaciones que no requieren
un uso intesivo de la CPU. Ejemplo, llamadas a un web service, leer datos
de una base de datos local o leer archivos.

AndroidSchedulers.mainThread()-> Este Scheduler se ejecuta en el hilo principal.
Es ideal para operaciones que actualizan la interfaz de usuario y las
interaciones del usuario con la app ocurren en este hilo.
Nunca se deben realizar operaciones intesivas o largas en este hilo,
ya que bloquearemos la interfaz de usuario y el propio Android nos pueden lanzar
un Android No Responde dialogo.

Schedulers.newThread()-> Este scheduler nos permite crear un nuevo hilo. Es ideal
utilizar este sheduler para operacinoes largas, y recordar que no se reutiliza el
hilo creado una vez la tarea finaliza.

Schedulers.computation()-> Este Scheduler se usa para operaciones de uso
intensivo de la CPU. Ejemplo el procesamiento de un flujo de datos muy grande.

Schedulers.single()-> Este Scheduler ejecuta todas las tareas en orden secuencial
a medida que le llega. Ideal para tareas que requiren una ejecución secuencia.

Schedulers.immediate()-> Este Scheduler ejecuta la tarea inmediatamente de forma
sincróna bloqueando el hilo principal. Es decir una tarea que va a durar poco tiempo
y se tiene que realizar de manera inmediata.

Schedulers.trampoline()-> Este Scheduler ejecuta las tareas de manera FiFo, es decir
primera en entrar, es la primera en salir.

Schedulers.from()-> Esto nos permite crear un Sheduler desde un ejecutor, puesto
que se limitan el número de hilos que se crean. Si los hilos creados están ocupados
 las tareas se ponen en cola.

--------------------------------------------------------------------------
                        Componentes Fundamentales de RXJava
--------------------------------------------------------------------------
1.- Observable: Los observables representa una fuente de datos que emite datos.
2.- Observer: El observador es la parte que complementa al Observable.
Recibe los datos que son emitidos por el observable.
3.- Subscription: Los Subscriptores nos permiten vincular un Observable con un Observer.
Una de las peculiaridades de RX es que permite que un observable tenga multiples
Observer suscritos.
4.- Operator: Los operadores modifican los datos que son emitidos por los observables
antes de que el observador los reciba. Esto nos ofrece una gran versatilidad.
5.- Schedulers: Los Schedulers deciden el hilo en el que el Observable debe emitir
los datos y tambien decide en que hilo el Observador recibe esos datos.

*/


public class MainActivity extends AppCompatActivity {

    private Button RX0Disposable;
    private Button RX1Operator;
    private Button RX02CompositeDisposable;
    private Button RX03EmpleadoActivity;
    private Button RX04SingleObservable;
    private Button RX05PublishSubject;
    private Button RX06RXBus;
    private Button RX07RXBinding;
    private Button RX08RXBackPresure;
    private Button RX09RXHotAndCold;

    private Button RX10BackGroundActivity;
    private Button RX11RetrofitActivity;

    private Disposable disposable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpView();

        /*
        --------------------------------------------------------------------------
                Primera Prueba RX
        --------------------------------------------------------------------------
        Como hemos dicho los Observables emiten datos.
        Vamos a crear un observable que emite una lista de numeros de 1 a 10.
        Para ello tenemos el operador just.

        1. Crea un observable que emita datos. A continuación hemos creado un observable
        que emite una lista de nombres de animales. Aquí solo el operador () se usa para
        emitir pocos nombres de animales.
         */
        Observable<String> numerosObservables =
                Observable.just("1", "2", "3", "4", "5",
                        "6", "7", "8", "9", "10");
        /*
        2. Necesitamos al menos un observador que consuma esos datos.
        Es decir necesitamos un observador que escuche el observable.
        El Observer tiene los siguientes metodos de interfaz para
        conocer el estado del Observable.
        onSubscribe()-> Se llamará al método onSubscribe cuando un observador
        se suscriba a un Observable.
        onNext()-> Se llamará al método onNext() cuando el Observable comience a
        emitir datos.
        onError()-> Se llamará al método onError() se llamará al metodo onError()
        onComplete()-> Se llamará al método onComplete cuando un observable haya
        completado la emisión de datos.
         */

        Observer<String> numerosObserver = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
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
        /*
        3.- Ahora tenemos que subcribir el Observer al Observable para que pueda
        comenzar a recibir los datos emitidos por el observable.
        Tenemos dos métodos adicionales:
        subscribeOn (Schedulers.io ()) -> SubscribeOn le dice al observable
        en que hilo se tiene que ejecutar la tarea. Por lo general un hilo de
        Background
        observeOn (AndroidSchedulers.mainThread ()) -> ObserveOn le dice al
        observador que recibe los datos del observable en que hilo lo debe hacer.
        Por lo general estods datos van a modificar nuestra interfaz de usuario,
        por ello escogeremos el de mainThread.
        */
        numerosObservables
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(numerosObserver);

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

        //Disposable disposable;
        Observable<String> numerosObservableDis = Observable.just("1", "2", "3", "4", "5",
                "6", "7", "8", "9", "10");
        Observer<String> numerosObserverDis = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
                //hhhhhh
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


        numerosObservables
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(numerosObserver);


















        //--------------------------Ejercicio--------------------------------
        /*

        Observable<String> source1 = Observable.interval(100,
                TimeUnit.MILLISECONDS)
                .map(i -> (i + 1) * 100) // map to elapsed time
                .map(i -> "SOURCE 1: " + i)
                .take(10);

        Observable<String> source2 = Observable.interval(300,
                TimeUnit.MILLISECONDS)
                .map(i -> (i + 1) * 300) // map to elapsed time
                .map(i -> "SOURCE 2: " + i)
                .take(10);

        Observable<String> source3 = Observable.interval(2000,
                TimeUnit.MILLISECONDS)
                .map(i -> (i + 1) * 2000) // map to elapsed time
                .map(i -> "SOURCE 3: " + i)
                .take(2);

        Observable.concat(source1, source2, source3)
                .throttleWithTimeout(1000, TimeUnit.MILLISECONDS)
                .subscribe(e->Log.d("TAG1", e));


        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
*/
    }


    private void setUpView(){
        RX0Disposable = findViewById(R.id.RX0Disposable);
        RX0Disposable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RX0DisposableActivity.class));
            }
        });

        RX1Operator = findViewById(R.id.RX1Operator);
        RX1Operator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RX01OperatorActivity.class));
            }
        });

        RX02CompositeDisposable = findViewById(R.id.RX02CompositeDisposable);
        RX02CompositeDisposable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RX02CompositeDispsableActivity.class));

            }
        });

        RX03EmpleadoActivity = findViewById(R.id.RX03EmpleadoActivity);
        RX03EmpleadoActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RX03EmpleadoActivity.class));
            }
        });


        RX10BackGroundActivity = findViewById(R.id.RX10BackGroundActivity);
        RX10BackGroundActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RX10BackGroudnActivity.class));
            }
        });

        RX04SingleObservable = findViewById(R.id.RX04SingleObservable);
        RX04SingleObservable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RX04SingleActivity.class));
            }
        });

        RX05PublishSubject = findViewById(R.id.RX05PublishSubject);
        RX05PublishSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RX05PublishActivity.class));
            }
        });

        RX06RXBus= findViewById(R.id.RX06RXBus);
        RX06RXBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RX06RXBusActivity.class));
            }
        });

        RX07RXBinding= findViewById(R.id.RX07RXBinding);
        RX07RXBinding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RX07RXBinding.class));
            }
        });

        RX08RXBackPresure= findViewById(R.id.RX08RXBackPresure);
        RX08RXBackPresure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RX08BackPressureActivity.class));

            }
        });

        RX09RXHotAndCold = findViewById(R.id.RX09RXHotAndCold);
        RX09RXHotAndCold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RX09HotAndColdActivity.class));
            }
        });

        RX11RetrofitActivity = findViewById(R.id.RX11RetrofitActivity);
        RX11RetrofitActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RX11RetrofitActivity.class));
            }
        });
    }
}
