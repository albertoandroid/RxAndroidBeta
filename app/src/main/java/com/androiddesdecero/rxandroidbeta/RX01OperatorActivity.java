package com.androiddesdecero.rxandroidbeta;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding3.widget.RxTextView;
import com.jakewharton.rxbinding3.widget.TextViewTextChangeEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import androidx.appcompat.app.AppCompatActivity;
import hu.akarnokd.rxjava2.math.MathObservable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observables.GroupedObservable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.schedulers.Timed;
import io.reactivex.subjects.Subject;


public class RX01OperatorActivity extends AppCompatActivity {

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

    private Observer<String> numerosObserver;
    private Disposable disposable;
    private CompositeDisposable compositeDisposable;
    private Observable<String> numerosObservable;


    private TextView tvQuery;
    private EditText etQuery;


    private String even = "";
    private String odd = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx1_operator);

        tvQuery = findViewById(R.id.tvQuery);
        etQuery = findViewById(R.id.etQuery);


        numerosObservable = getNumerosObservable();
        numerosObserver = getNumerosObserver();
        /*
        Predicade -> Un predicado es uan interfaz funcional que define una condición
        que un objeto determiando debe cumplir.
        Es decir que es una función que nos va a devoler un valor de verdadero o falso
        Ejemplo que los numeros sean mayor que cero.
        interface Predicate<T>
        {
            Boolean text (T t)
        }
        */
/*
        numerosObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Predicate<String>(){
                    @Override
                    public boolean test(String s)  {
                        return Integer.parseInt(s)%2==0;
                    }
                })
                .subscribeWith(numerosObserver);

*/
        /*
        El operador map()-> transforma los items emitidos por un observable aplicando
        una función a cada item.
        Ejemplo Multiplicando por 10;
        interface Function<T, R>
        {
            R apply(T t)
        }
        T -> El tipo del parametro de Entrada de la función
        R -> El tipo del parametro de Resultado de la función
         */
/*
        numerosObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Predicate<String>(){
                    @Override
                    public boolean test(String s) throws Exception  {
                        return Integer.parseInt(s)%2==0;
                    }
                })
                .map(new Function<String, String>() {
                    @Override
                    public String apply(String s) throws Exception {
                        return String.valueOf(Integer.parseInt(s) * 10);
                    }
                })
                .subscribeWith(numerosObserver);
*/

        /*
        Operator: Los operadores modifican los datos que son emitidos por los observables
antes de que el observador los reciba. Esto nos ofrece una gran versatilidad.

        Los operadores de RX se pueden clasificar en:
        1.- Operadores que crean observables. Just, create, from Array, range -> Crean observable
        2.- Operadores para filtrar datos: filter, skip, last, debounce.
        3.- Operadores que crean un observable transformando los datos emitidos por
        otro observable -> Buffer, Map, SwitchMap, Compose, flatMap
http://reactivex.io/documentation/operators.html#categorized
         */

        /*
        Operadores para crear Observables a partir de un conjunto de Elementos.
        Estos son los más básicos de usar y no realizan ninguna operación compleja, excepto
        crear el observable.
         */
        //probarJust();
        //probarJustArray();
        //probarFromArray();
        //probarRange();
        //probarRepeat();
        //probarInterval();
        //probarInterval();



        /*
        Operadores que Transorman items emitidos por los Observables.
         */
        //probarBuffer();
        //probarMap();
        //probarFlatMap();
        //probarGroupBy();
        //probarScan();
        //probarWindow();


        /*
        Operadores que selectivamente emiten items de un observabnle.
         */

        //probarDeobunce();
        //probarDebounce();
        //probarDistinct();
        //probarElementAt();
        //probarFilter();
        //probarFirst();
        //probarIgnoreElements();
        //probarLast();
        //probarSample();
        //probarSkip();
        //probarSkipLast();
        //probarSkip();
        //probarSkipLast();

        /*
        Operadores que trabajan con multiples observables para crear un Single Observable
         */

        //probarCombineLast();
        //probarJoin();
        //probarMerge();
        //probarZip();

        /*
        Operadores que ayunda a recuperar erros de notificación en los observables
         */
        //probarRetryWhen();


        /*
        Operadores utiles de RX
         */
        //probarDealy();
        //probarDo();
        //probarOnbserveOn();
        //probarTimeInterval();
        //probarTiemOut();
        //probarTimestamp();
        //probarUsing();

        /*
        Operadores Booleanos y Condicionales
         */
        //probarAll();
        //probarAmb();
        //probarContains();
        //probarDefaultIfEmpty();
        //probarSequenceEqual();
        //probarSkipUntil();
        //probarSkipWhile();
        //probarTakeUntil();
        //probarTakeWhile();


        /*
        Operadores Matematicos
         */
        //probarAverage();
        //probarCount();
        //probarMaxyMin();

        //probarCrearHipoteca();



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }


    private Observable<String> getNumerosObservable() {
        return Observable.just("1", "2", "3", "4", "5",
                "6", "7", "8", "9", "10");
    }

    private Observer<String> getNumerosObserver() {
        return new Observer<String>() {
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

/*
Operadores para crear Observables a partir de un conjunto de Elementos.
    Estos son los más básicos de usar y no realizan ninguna operación compleja, excepto
    crear el observable.
 */


    private void probarJust() {
        /*
        1.- El operador Just tomo una lista de argumentos y converte los elementos
        en elementos observables.
        Just solo puede enviar 10 elementos, es decir si ponemos 11 o más nos dara
        error.
         */
        Log.d("TAG1", "--------------Just--------------");
        Observable.just("1", "2", "3", "4", "5",
                "6", "7", "8", "9", "10")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        Log.d("TAG1", "Just-> onNest: " + s);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void probarJustArray() {
        //Pasamos los datos en forma de Array, pero ahora lo que se hace, es enviar todo
        //el array como un único elemento.
        Log.d("TAG1", "--------------Just Array--------------");
        String[] numeros = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        Observable.just(numeros)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String[]>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String[] strings) {
                        Log.d("TAG1", "Just Array->onNest: " + strings.length);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void probarFromArray() {
        /*
        FromArray crea un observable a partir de un conjunto de elementos utilizando
        un Iterable.
        Es decir cada elemento es emitido de uno en uno.
         */
        Log.d("TAG1", "--------------FromArray--------------");
        String[] numeros = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        Observable.fromArray(numeros)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        Log.d("TAG1", "FromArray-> onNest: " + s);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void probarRange() {
        /*
        Range()
        Range crea un observable a partir de una secuencia de enteros generados.
        En range le pasamos el valor inicia y final de la secuencia.
         */
        Log.d("TAG1", "--------------Range--------------");
        Observable.range(7, 17)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d("TAG1", "Range-> onNext: " + integer);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private void probarRepeat() {
        /*
        Repeat()
        Repeat-> Crea un observable que emite un elemente o una serie de elemntos repetidamente.
        Le podemos pasar un argumento para limitar el número de repiticiones.
         */
        Log.d("TAG1", "--------------Repeat--------------");
        Observable
                .range(10, 5)
                .repeat(4)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d("TAG1", "Repeat-> onNext: " + integer);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    private void probarInterval() {
        /*
        interval()-> Crea un oblservable que emite una secuencia de items espaciados
        por un intervalo de tiempo dado.
        En este caso estaría emitiendo datos infinitamente. A no ser que usemos
        otros operadores que todavía no hemos visto como take.
        */
        Log.d("TAG1", "--------------Interval--------------");
        Observable
                .interval(1, TimeUnit.SECONDS)
                .take(10)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Long dataLong) {
                        Log.d("TAG1", "Interval-> onNext: " + dataLong);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                    }
                });


        String[] letters = {"a", "b", "c", "d", "e", "f", "g"};
        String result = "";
        Observable<String> observable = Observable.fromArray(letters);
        observable.subscribe(
                i -> Log.d("TAG1", i),  //OnNext
                Throwable::printStackTrace, //OnError
                () -> Log.d("TAG1", "complete") //OnCompleted
        );
    }

    private void probarBuffer() {
        /*
        Buffer()-> Agrupa los items emitidos por un observable en lotes y
        emite dicho loto en lugar de emitir cada item por separado.
        */
        Log.d("TAG1", "--------------Buffer--------------");
        Observable<Integer> integerObservable = Observable.just(1, 2, 3, 4,
                5, 6, 7, 8, 9);

        integerObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .buffer(3)
                .subscribe(new Observer<List<Integer>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Integer> integers) {
                        Log.d("TAG1", "Buffer-> onNext");
                        for (Integer integer : integers) {
                            Log.d("TAG1", "Buufer-> Item: " + integer);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        Log.d("TAG1", "Buffer->onComplete");
                    }
                });
    }

    private void probarMap() {
       /*
        FlatMap()-> Transforma los items emitidos por un observable
        aplicando una función a cada item.
        */
        Log.d("TAG1", "--------------Map--------------");
        List<Empleado> empleados = Empleado.setUpEmpleados();
        Observable.fromArray(empleados)
                .map(new Function<List<Empleado>, List<String>>() {
                    @Override
                    public List<String> apply(List<Empleado> empleados) {
                        List<String> nombres = new ArrayList<>();
                        for (Empleado e : empleados) {
                            nombres.add(e.getNombre());
                        }
                        return (nombres);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<String>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<String> strings) {
                        Log.d("TAG1", "Map-> Item: " + strings);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    private void probarFlatMap() {
         /*
        FlatMap()-> Transforma los items emitidos por un observable
        en observables. Y luego emite todo en un Unico Observable.
        */
        Log.d("TAG1", "--------------FlatMap--------------");
        Observable
                .just("item2")
                .flatMap(str -> {
                    Log.d("TAG1", "inside the flatMap " + str);
                    return Observable.just(str + "+", str + "++", str + "+++");//Generamos los observables de retorno
                    //}).subscribe(e->Log.d("TAG1", e));
                }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                Log.d("TAG1", s);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });


    }

    private void probarGroupBy() {
         /*
        groupBy()-> Divide un Observable en un conjunto de observables que emiten un grupo
        diferentes de items del observable original, organizado por clave.
        Es decir nos permite clasificar los intems del observable en categoria de items de salida
        */
        Log.d("TAG1", "--------------groupBy--------------");


        Disposable integerObservable = Observable.just(1, 2, 3, 4,
                5, 6, 7, 8, 9)
                .groupBy(i -> 0 == (i % 2) ? "EVEN" : "ODD")
                .subscribe(group ->
                        group.subscribe((number) -> {
                            if (group.getKey().toString().equals("EVEN")) {
                                even += number;
                            } else {
                                odd += number;
                            }
                        })
                );
        Log.d("TAG1", even);
        Log.d("TAG1", odd);

        Observable<Integer> integerObservable2 = Observable.just(1, 2, 3, 4,
                5, 6, 7, 8, 9);

        Observable<GroupedObservable<String, Integer>> groupedObservableObservable =
                integerObservable2
                .groupBy(new Function<Integer, String>() {
                    @Override
                    public String apply(Integer integer) {
                        if(integer%2==0){
                            return "PAR";
                        }else {
                            return "IMPAR";
                        }
                    }
                });
        groupedObservableObservable
                .subscribe(
                        new Observer<GroupedObservable<String, Integer>>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(GroupedObservable<String, Integer> stringIntegerGroupedObservable) {
                                    stringIntegerGroupedObservable
                                            .subscribe(new Observer<Integer>() {
                                                @Override
                                                public void onSubscribe(Disposable d) {

                                                }

                                                @Override
                                                public void onNext(Integer integer) {
                                                    if(stringIntegerGroupedObservable.getKey().equals("PAR")){
                                                        Log.d("TAG1", "PAR " + integer);
                                                    } else {
                                                        Log.d("TAG1", "Impar " + integer);
                                                    }

                                                }

                                                @Override
                                                public void onError(Throwable e) {

                                                }

                                                @Override
                                                public void onComplete() {

                                                }
                                            });

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        }
                );

    }

    private void probarScan() {
         /*
        Scan()-> Este operador transforma cada item en otro item, como map,
        solo que tambien puedes utilizar el item previos para hacer
        alguna operacion. ejemplo fibonacci
        */
        Log.d("TAG1", "--------------Scan--------------");
        Disposable fibonacci =
                Observable.fromArray(0)
                        .repeat(10)
                        .scan(new int[]{0, 1}, (a, b) -> new int[]{a[1], a[0] + a[1]})
                        .map(a -> a[1])
                        .subscribe(e -> Log.d("TAG1", e + ""));


        Observable.just(1,2,3,4,5,6,7,8)
                .scan(new BiFunction<Integer, Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer, Integer integer2) throws Exception {
                        return integer + integer2;
                    }
                })
                .subscribe(
                        e->Log.d("TAG1", "onNext: " + e)
                );
    }

    private void probarWindow() {
         /*
        Window()-> Este operador subdivide los items de un observable periodicamente en una
        ventana observable y emita esa ventaja de items en lugar de emitir todos los items de una
        vez
        */
        Log.d("TAG1", "--------------Window--------------");
        Observable<Observable<Integer>> observable = Observable.range(1, 133).window(3);
        observable.subscribe(s -> {
            Log.d("TAG1", "siguiente ventana: ");
            s.subscribe(i -> Log.d("TAG1", "item en ventana: " + i));
        });
    }

     /*
        Debounce()-> Solo emite un item del observable si un timestam particular ha pasado sin
        emitir otro item.
        */
     /*
     Otra de las grandes ventajas de RX es que nos pemite manejar eventos asincronos
     de una mejor manera.
     Las interacciones de los usuarios con la interfaz de usuario son eventos
     asincronos y rx nos permite manejarlos de mejor manera.
     En un mundo sin RX manejamos estos evetnos con listener, handler, asynstask, etc.
     per con RX unas pocas lineas de codigo hacen todo el trabajo.
     Imagina que haces una aplicación que que como gogle cuando introduces
     una letra en su buscador hace una petición para mostrarte resultados que comienzan de
     esa forma, no quieres enviar peticiones que no puedan ser gestionadas, por que el usuario
     escriba muy rapido y por ejemplo si escribe a -> alberto, Albacete, pero en muy poco tiempo
     escribe as, sabes que será asturias, o astro, etc. En este caso, vamos a esperar
     medio segundo para enviar esta petición, es decir que el usuario tiene que estar
     medio segundo sin escribir para que se envie la petición.
      */

    private void probarDebounce(){
        Observable<String> observable = (Observable<String>) RxTextView.textChanges(etQuery)
                .debounce(500, TimeUnit.MILLISECONDS)
                .map(e->e.toString())
                .subscribe(
                        e->{
                            Log.d("TAG1", "String busqueda: " + e);
                            tvQuery.setText("query: " + e);
                        }
                );
    }

    private void probarDeobunce() {
         /*
        Debounce()-> Solo emite un item del observable si un timestam particular ha pasado sin
        emitir otro item.
        */
        Log.d("TAG1", "--------------Debounce--------------");
        compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(
                RxTextView.textChangeEvents(etQuery)
                        .skipInitialValue()
                        .debounce(500, TimeUnit.MILLISECONDS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(buscarQuery()));

        tvQuery.setText("Esperando datos....");


    }

    private DisposableObserver<TextViewTextChangeEvent> buscarQuery() {
        return new DisposableObserver<TextViewTextChangeEvent>() {
            @Override
            public void onNext(TextViewTextChangeEvent textViewTextChangeEvent) {
                Log.d("TAG1", "String de Busqueda: " + textViewTextChangeEvent.getText().toString());
                tvQuery.setText("Query: " + textViewTextChangeEvent.getText().toString());
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
    }

    private void probarDistinct() {
         /*
        Distinct()-> Suprimer items duplicados emitodos por un obsercable.
        */
        Log.d("TAG1", "--------------Distinct--------------");
        Observable<Integer> numerosObservable = Observable.just(1, 1, 7, 6, 7, 1, 5, 2, 1, 5);
        numerosObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .distinct()
                .subscribe(
                        i -> Log.d("TAG1", "onNext: No Repetidos " + i),  //OnNext
                        Throwable::printStackTrace, //OnError
                        () -> Log.d("TAG1", "onCompleted con Lambdas") //OnCompleted
                );

    }

    private void probarElementAt() {
         /*
        ElementAt()-> emite solo el item de la posición n del Observable.
        */
        Log.d("TAG1", "--------------ElementAt--------------");
        Observable<Integer> numerosObservable = Observable.just(1, 1, 7, 6, 7, 1, 5, 2, 1, 5);
        numerosObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .elementAt(6)
                .subscribe(
                        i -> Log.d("TAG1", "onNext: Element posición 3 " + i),  //OnNext
                        Throwable::printStackTrace, //OnError
                        () -> Log.d("TAG1", "onCompleted con Lambdas") //OnCompleted
                );
    }

    private void probarFilter() {
         /*
        Filter()-> emite solo los items del observable que pasas un predicado.
        */
        Log.d("TAG1", "--------------Filter--------------");
        /*
        Predicade -> Un predicado es uan interfaz funcional que define una condición
        que un objeto determiando debe cumplir.
        Es decir que es una función que nos va a devoler un valor de verdadero o falso
        Ejemplo que los numeros sean mayor que cero.
        interface Predicate<T>
        {
            Boolean text (T t)
        }
        */
        Observable<String> numerosObservable = Observable.just("1", "1", "7", "6", "7", "1", "5", "2", "1", "5");
        numerosObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(String s) {
                        return Integer.parseInt(s) % 2 == 0;
                    }
                })
                .subscribe(
                        i -> Log.d("TAG1", "onNext: Es par" + i),  //OnNext
                        Throwable::printStackTrace, //OnError
                        () -> Log.d("TAG1", "onCompleted con Lambdas") //OnCompleted
                );

    }

    private void probarFirst() {
         /*
        First()-> emite solo el primer item. O el primer item que cumple una condición del observable.
        */
        Log.d("TAG1", "--------------First--------------");
        Observable<String> numerosObservable = Observable.just("1", "1", "7", "6", "7", "1", "5", "2", "1", "5");
        numerosObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .first("No items")
                .subscribe(
                        i -> Log.d("TAG1", "onNext: Es el primero: " + i)  //OnNext
                );
    }

    private void probarIgnoreElements() {
         /*
        IgnoreElements()-> No emeite ningun item del observable pero notifica su terminación
        */
        Log.d("TAG1", "--------------IgnoreElements--------------");
        Observable<String> numerosObservable = Observable.just("1", "1", "7", "6", "7", "1", "5", "2", "1", "5");
        numerosObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .ignoreElements()
                .subscribe(
                        () -> Log.d("TAG1", "onComplete")
                );
    }

    private void probarLast() {
         /*
        Last()-> Emite el último item de un observable. .
        */
        Log.d("TAG1", "--------------Last--------------");
        Observable<String> numerosObservable = Observable.just("1", "1", "7", "6", "7", "1", "5", "2", "1", "5");
        numerosObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .last("No items")
                .subscribe(
                        i -> Log.d("TAG1", "onNext: Es el primero: " + i)  //OnNext
                );
    }

    private void probarSample() {
         /*
        Sample()-> Emite el item más reciente emitido por el observable
        en un intervalo de tiempo periodico
        */
        Log.d("TAG1", "--------------Sample--------------");

        Observable
                .interval(500, TimeUnit.MILLISECONDS)
                .take(10)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .sample(2000, TimeUnit.MILLISECONDS)
                .subscribe(
                        i -> Log.d("TAG1", "onNext: " + i),  //OnNext
                        Throwable::printStackTrace, //OnError
                        () -> Log.d("TAG1", "onCompleted") //OnCompleted
                );
    }

    private void probarSkip() {
         /*
        Skip()-> Suprimer los primeros n elementos emitidos por un Observable
        */
        Log.d("TAG1", "--------------Skip--------------");
        Observable<String> numerosObservable = Observable.just("1", "1", "7", "6", "7", "1", "5", "2", "1", "5");
        numerosObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .skip(3)
                .subscribe(
                        i -> Log.d("TAG1", "onNext: Es el primero: " + i)  //OnNext
                );
    }

    private void probarSkipLast() {
         /*
        SkipLast()-> Suprimer los último n elementos emtidos por un observable.
        */
        Log.d("TAG1", "--------------SkipLast--------------");
        Observable<String> numerosObservable = Observable.just("1", "1", "7", "6", "7", "1", "5", "2", "1", "5");
        numerosObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .skipLast(3)
                .subscribe(
                        i -> Log.d("TAG1", "onNext: Es el primero: " + i)  //OnNext
                );
    }

    private void probarTake() {
         /*
        Take()-> Emite solo los n primeros elementos emitods por el Observable
        */
        Log.d("TAG1", "--------------Take--------------");
        Observable<String> numerosObservable = Observable.just("1", "1", "7", "6", "7", "1", "5", "2", "1", "5");
        numerosObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .take(3)
                .subscribe(
                        i -> Log.d("TAG1", "onNext: Es el primero: " + i)  //OnNext
                );
    }

    private void probarTakeLast() {
         /*
        TakeLast()-> Emite solo los n ultimos elementos emitods por el Observable
        */
        Log.d("TAG1", "--------------TakeLast--------------");
        Observable<String> numerosObservable = Observable.just("1", "1", "7", "6", "7", "1", "5", "2", "1", "5");
        numerosObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .takeLast(3)
                .subscribe(
                        i -> Log.d("TAG1", "onNext: Es el primero: " + i)  //OnNext
                );
    }

    private void probarCombineLast() {
        /*
        CombineLast()-> Cuando un item es emitido por cualquier de los dos observables, combina
        el ultimo item emitido por cada observable a traves de una funcion y emite
        el item basado en el resultado de esa función.
        */
        Log.d("TAG1", "--------------CombineLast--------------");
        Observable<Long> observable1 = Observable.interval(100, TimeUnit.MILLISECONDS).take(10);
        Observable<Long> observable2 = Observable.interval(50, TimeUnit.MILLISECONDS).take(20);

        Observable.combineLatest(observable1, observable2,
                (observable1Times, observable2Times) ->
                        "Refreshed Observable1: " + observable1Times + " refreshed Observable2: " + observable2Times)
                .subscribe(item -> Log.d("TAG1", "onNext: " + item));

    }

    private void probarJoin() {
        /*
        Joint()-> Combina los items emitidos por dos observables siempre que un item
        del un observable se emita durante una ventana de timepo definida
        de acuerdo con un elmento emitido por el otro observable.
        */
        Log.d("TAG1", "--------------Join--------------");

        /*
*/

        Long LEFTWINDOWDURATION = 0L;
        Long RIGHTWINDOWDURATION = 0L;


        Observable<Long> left = Observable
                .interval(100, TimeUnit.MILLISECONDS).take(10);

        Observable<Long> right = Observable
                .interval(100, TimeUnit.MILLISECONDS).take(10);
        left.join(right,
                aLong -> Observable.timer(LEFTWINDOWDURATION, TimeUnit.SECONDS),
                aLong -> Observable.timer(RIGHTWINDOWDURATION, TimeUnit.SECONDS),
                (l, r) -> {
                    Log.d("TAG1", "Left result: " + l + " Right Result: " + r);
                    return l + r;
                })
                .subscribe(
                        e -> Log.d("TAG1", "result: " + e)
                );

    }

    private void probarMerge() {
        /*
        Merge()-> Convina multiples observables megeando su emision
        */
        Log.d("TAG1", "--------------Merge--------------");
        Observable<String> observable1 = Observable
                .interval(2, TimeUnit.SECONDS).map(id -> "Grupo 1: " + id);

        Observable<String> observable2 = Observable
                .interval(1, TimeUnit.SECONDS).map(id -> "Grupo 2: " + id);

        Observable.merge(observable1, observable2)
                .subscribe(
                        e -> Log.d("TAG1", e + "")
                );

    }

    private void probarZip() {
        /*
        Zip()-> convina la emisión de varios observables en un solo Observable que emite
        los items emtidos por los observables más recientes.
        */
        Log.d("TAG1", "--------------Zip--------------");
        Observable<String> observable1 = Observable
                .interval(1, TimeUnit.SECONDS).map(id -> "Grupo 1: " + id);

        Observable<String> observable2 = Observable
                .interval(1, TimeUnit.SECONDS).map(id -> "Grupo 2: " + id);

        Observable.zip(observable1, observable2, (x, y) -> x + " " + y)
                .subscribe(
                        e -> Log.d("TAG1", e)
                );


    }


    private void probarRetryWhen() {
        /*
        RetryWhen()-> Si un observable envia un onError notification, se resuscribe
        esperando que la siguiente vez no haya error. Pero con When le anotamos
        una función con lo que saldrá cuando se cumpla.
        */
        Log.d("TAG1", "--------------RetryWhen--------------");
        Observable
                .create(e -> {
                    e.onNext("probando retry");
                    e.onError(new Throwable("test"));
                })
                .retryWhen(errors -> errors.retry())//la función es que lo siga intentando. Pero podría ser salir a la tercera vez.
                .subscribeOn(Schedulers.io())
                .subscribe(
                        next -> Log.d("TAG1", "onNext: " + next),
                        error -> Log.d("TAG1", "onError: " + error),
                        () -> Log.d("TAG1", "onComplete")
                );
    }

    private void probarDealy() {
        /*
        Delay()-> Retrasa la emisión de un observable en el tiempo indicado.
        */
        Log.d("TAG1", "--------------Delay--------------");
        Observable<String> numerosObservable = Observable.just("1", "1", "7", "6", "7", "1", "5", "2", "1", "5");
        numerosObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .delay(5, TimeUnit.SECONDS)
                .subscribe(
                        i -> Log.d("TAG1", "onNext: Es el primero: " + i)  //OnNext
                );
    }

    private void probarDo() {
        /*
        Dop()-> Registra una acción para tomar en cuenta una variedad de eventos del ciclo de vida
        del observable. Nos permite tener control que pasa antes y despues.
        */
        Log.d("TAG1", "--------------Do--------------");
        Observable<String> numerosObservable = Observable.just("1", "1", "7", "6", "7", "1", "5", "2", "1", "5");
        numerosObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(list -> Log.d("TAG1", "doOnNext"))
                .doAfterNext(list -> Log.d("TAG1", "doAfterNext"))
                .doOnComplete(() -> Log.d("TAG1", "doOnComplete"))
                .subscribe(
                        i -> Log.d("TAG1", "onNext: Es el primero: " + i)  //OnNext
                );
    }

    private void probarOnbserveOn() {
        /*
        OnbserveOn()-> Este operador indica el Scheduler donde el observador observará al observable.
        Por defecto, un observable y sus operadores operarán en el mismo hilo en el que se llamda
        al metodo subscribe.
        SubscribeOn ->  Indica el Scheduler que un observable debe utilizar cuando se suscriban.
        */
        Log.d("TAG1", "--------------OnbserveOn--------------");
        Observable<String> numerosObservable = Observable.create(e -> {
            e.onNext("probando retry");
            e.onNext(Thread.currentThread().getName().toString());
        });
        numerosObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        e -> Log.d("TAG1", e + " Hilo Observer: " + Thread.currentThread().getName().toString())
                );

    }

    private void probarTimeInterval() {
        /*
        TimeInterval()-> Convierte un Observable que emite items en uno
        que emite indicaciones de la cantidad de tiempo
        transcurrido entre las emisiones. Es decir que nos permite tener controlado
        El tiempo que pasa entre items y items.
        */
        Log.d("TAG1", "--------------TimeInterval--------------");
        Observable<String> numerosObservable = Observable.create(e -> {
            e.onNext("A");
            e.onNext("B");
            e.onNext("C");
        });
        numerosObservable.interval(100, TimeUnit.MILLISECONDS)
                .take(3)
                .timeInterval()
                .subscribe(new Subject<Timed<Long>>() {
                               @Override
                               public boolean hasObservers() {
                                   return false;
                               }

                               @Override
                               public boolean hasThrowable() {
                                   return false;
                               }

                               @Override
                               public boolean hasComplete() {
                                   return false;
                               }

                               @Override
                               public Throwable getThrowable() {
                                   return null;
                               }

                               @Override
                               protected void subscribeActual(Observer<? super Timed<Long>> observer) {

                               }

                               @Override
                               public void onSubscribe(Disposable d) {

                               }

                               @Override
                               public void onNext(Timed<Long> longTimed) {
                                   Log.d("TAG1", "ONNEXT: " + longTimed);
                               }

                               @Override
                               public void onError(Throwable e) {

                               }

                               @Override
                               public void onComplete() {

                               }
                           }

                );

    }

    private void probarTiemOut() {
        /*
        TiemOut()-> Reflea la fuente de observable, pero emite una noticiación
        de error si en un periodo de tiempo indicado no hay ningún elmente emitido.
        */
        Log.d("TAG1", "--------------TiemOut--------------");
        Observable<String> numerosObservable = Observable.create(e -> {
            e.onNext("A");
            e.onNext("B");
            e.onNext("C");
        });
        numerosObservable
                .timer(1, TimeUnit.SECONDS)
                .timeout(500, TimeUnit.MILLISECONDS)
                .subscribe(
                        e -> Log.d("TAG1", "onNext" + e),
                        e -> Log.d("TAG1", "onError: " + e)
                );
    }

    private void probarTimestamp() {
        /*
        Timestamp()-> adjunta a timeStamp a cada item emitido por un observable.
        */
        Log.d("TAG1", "--------------Timestamp--------------");
        Observable<String> numerosObservable = Observable.create(e -> {
            e.onNext("A");
            e.onNext("B");
            e.onNext("C");
        });
        numerosObservable
                .timestamp()
                .subscribe(new Observer<Timed<String>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Timed<String> stringTimed) {
                        Log.d("TAG1", "onNext: " + stringTimed);

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private void probarUsing() {
        /*
        Using()-> crear un recurso Disposable que tenga la misma vida útil que el observable.
        Es decir que ello solo hace el dispose cuando el observable termina.
        */
        Log.d("TAG1", "--------------Using--------------");
        Observable.using(
                new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        return "Using";
                    }
                },
                new Function<String, ObservableSource<Character>>() {
                    @Override
                    public ObservableSource<Character> apply(String s) {
                        return Observable.create(o -> {
                            for (Character c : s.toCharArray()) {
                                o.onNext(c);
                            }
                            o.onComplete();
                        });
                    }
                },

                new Consumer<String>() {
                    @Override
                    public void accept(String s) {
                        Log.d("TAG1", "Disposable: " + s);
                    }
                })
                .subscribe(
                        e -> Log.d("TAG1", "onNext: " + e)
                );
    }

    private void probarAll() {
        /*
        All()-> Determina si todos los items emitidos por un observable
        cumple alguna condición.
        */
        Log.d("TAG1", "--------------All--------------");
        Observable<Integer> numerosObservable = Observable.just(1, -1, -7, 6, 7, 1, -5, -2, 1, -5);
        numerosObservable.all(e -> e > 0)
                .subscribe(
                        e -> Log.d("TAG1", "onSuccess: " + e)
                );


        /*
        De la otra manera
         */
        numerosObservable.all(e -> e > 0)
                .subscribe(new SingleObserver<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Boolean aBoolean) {
                        System.out.println("onNext: " + aBoolean);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    private void probarAmb() {
        /*
        Amb()-> Dados dos o más Observables, solo se emitirá el obserbable que emita el
        primer item y se emitiran todos los itemas de ese obserbable, pero ninguno de los demas.
        */
        Log.d("TAG1", "--------------Amb--------------");
        Observable<Integer> numerosObservable = Observable.just(1, -1, -7, 6, 7, 1, -5, -2, 1, -5);

        Observable<Integer> numerosObservable2 = Observable.just(14, -14, -74, 64, 74, 14, -54, -24, 14, -54);

        Observable
                .ambArray(numerosObservable.delay(10, TimeUnit.SECONDS), numerosObservable2)
                .subscribe(
                        e -> Log.d("TAG1", "onNext:" + e)
                );
    }

    private void probarContains() {
        /*
        Contains()-> Este operador determimna si un Observable emite un item particular o no.
        */
        Log.d("TAG1", "--------------Contains--------------");
        Observable<Integer> numerosObservable = Observable.just(1, -1, -7, 6, 7, 1, -5, -2, 1, -5);
        numerosObservable
                .contains(-1)
                .subscribe(
                        e -> Log.d("TAG1", "onSuccess: " + e)
                );


        numerosObservable
                .contains(-1)
                .subscribe(new SingleObserver<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Boolean aBoolean) {
                        Log.d("TAG1", "onSuccess: " + aBoolean);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });

    }

    private void probarDefaultIfEmpty() {
        /*
        DefaultIfEmpty()-> Este operado emite items del observable de origen o un item
        por defecto si el observable no emite nada.
        */
        Log.d("TAG1", "--------------DefaultIfEmpty--------------");
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) {
                //Si el número es par envia item. Si es impar no se envia.
                int num = 7;
                //int num = 8;
                if (num % 2 == 0) {
                    emitter.onNext(num);
                }
                emitter.onComplete();
            }
        })
                .defaultIfEmpty(-10)
                .subscribe(
                        e -> Log.d("TAG1", "onNext:" + e)
                );


    }

    private void probarSequenceEqual() {
        /*
        SequenceEqual()-> Este operador determina si dos observable emiten
        la misma secuencia de items.
        */
        Log.d("TAG1", "--------------SequenceEqual--------------");

        Observable<Integer> observable1 = Observable
                .just(4, 5, 23, -5, 9, 1);

        Observable<Integer> observable2 = Observable
                .just(4, 5, 23, -5, 9, 1);

        Observable.sequenceEqual(observable1, observable2)
                .subscribe(
                        e -> Log.d("TAG1", e + "")
                );


    }

    private void probarSkipUntil() {
        /*
        probarSkipUntil()-> Este operador descarta los items emitidos por un observable
        hasta que un segundo observable empieza a emitir items.
        */
        Log.d("TAG1", "--------------SkipUntil--------------");

        Observable<Integer> observable1 = Observable
                .create(emitter -> {
                    for (int i = 0; i <= 10; i++) {
                        Thread.sleep(500);
                        emitter.onNext(i);
                    }
                    emitter.onComplete();
                });

        Observable<Long> observable2 = Observable
                .create(emitter -> {
                    emitter.onNext(4);
                    emitter.onComplete();
                })
                .timer(3, TimeUnit.SECONDS);

        observable1.skipUntil(observable2)
                .subscribe(
                        e -> Log.d("TAG1", "onNext: " + e)
                );


    }

    private void probarSkipWhile() {
        /*
        SkipWhile()-> Este operador descarta los items emtididos por el observable
        hasta que una condición especifica se vuelva falsta.
        */
        Log.d("TAG1", "--------------SkipWhile--------------");
        Observable
                .create(emitter -> {
                    for (int i = 0; i <= 6; i++) {
                        Thread.sleep(1000);
                        emitter.onNext(i);
                    }
                    emitter.onComplete();
                })
                .skipWhile(e -> (Integer) e <= 2) //Predicate
                .subscribe(
                        e -> Log.d("TAG1", "onNext: " + e)
                );
    }

    private void probarTakeUntil() {
        /*
        TakeUntil()-> Este operador descarta los items emtididos por el observable
        en el momento que un segundo observable emita un item o termine.
        */
        Log.d("TAG1", "--------------TakeUntil--------------");

        Observable<Integer> observable1 = Observable
                .create(emitter -> {
                    for (int i = 0; i <= 4; i++) {
                        Thread.sleep(1000);
                        emitter.onNext(i);
                    }
                    emitter.onComplete();
                });

        Observable<Long> observable2 = Observable
                .create(emitter -> {
                    emitter.onNext(4);
                    emitter.onComplete();
                })
                .timer(3, TimeUnit.SECONDS);

        observable1.takeUntil(observable2)
                .subscribe(
                        e -> Log.d("TAG1", "onNext " + e)
                );
    }

    private void probarTakeWhile() {
        /*
        TakeWhile()-> Este operador descartará los items emitidos por un observable
        despues de que una condición especifica se vuelva falsa.
        */
        Log.d("TAG1", "--------------TakeWhile--------------");

        Observable
                .create(emitter -> {
                    for (int i = 0; i <= 6; i++) {
                        Thread.sleep(1000);
                        emitter.onNext(i);
                    }
                    emitter.onComplete();
                })
                .takeWhile(
                        e -> (Integer) e <= 2
                )
                .subscribe(
                        e -> Log.d("TAG1", "onNext: " + e)
                );

    }

    /*
    A partir de aqui se necesita esta implementación
        implementation 'com.github.akarnokd:rxjava2-extensions:0.20.0'

     */

    private void probarAverage() {
        /*
        Average()-> Este operador calcula la media de los items emitidos por el observable
        y emite este valor.
        */
        Log.d("TAG1", "--------------Average--------------");
        Observable<Integer> numberObservable = Observable.fromArray(1, 29, 87, 43, 3);

        MathObservable.averageDouble(numberObservable)
                .subscribe(
                        e->Log.d("TAG1", "average: " + e)
                );
    }

    private void probarCount() {
        /*
        Count()-> Este operador calcula el número de elementos emitidos
        por un observable.
        */
        Log.d("TAG1", "--------------Count--------------");
        Observable.just(19, 21, 32, 54, 75)
                .count()
                .subscribe(
                        e->Log.d("TAG1", "count: " + e)
                );
    }

    private void probarMaxyMin() {
        /*
        Max()-> Este operador indica el itemMax
        */
        Log.d("TAG1", "--------------Max--------------");
        Observable<Integer> observable = Observable.just(1, 77, 389, 24, 67);

        MathObservable
                .max(observable)
                .subscribe(
                        e->Log.d("TAG1", "Max: " + e)
                );

        /*
        Min()-> Este operador indica el itemMin
        */
        Log.d("TAG1", "--------------Min--------------");
        MathObservable
                .min(observable)
                .subscribe(
                        e->Log.d("TAG1", "Min: " + e)
                );

        /*
        Sum()-> Este operador hace un sumatorio de los dato emitidos por el observable
        */
        Log.d("TAG1", "--------------Sum--------------");
        MathObservable
                .sumInt(observable)
                .subscribe(
                        e->Log.d("TAG1", "Suma: " + e)
                );


        /*
        Reduce-> Este operador aplica una función a cada item emitido por un observable,
        secuencialmente y emite un valor final.
        Primero aplica una función al primer item, y luego toma el resultado y vuelve a
        aplicar la misma función en el segundo elemento.
        Este proceso continua hata la última emisión.
        Una vez que todos los items han termiando se emite el resultado final.
         */
        Log.d("TAG1", "--------------Reduce--------------");
        Observable.just(2, 2, 2, 2)
                .reduce(new BiFunction<Integer, Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer, Integer integer2) {
                        return (integer * integer2);
                    }
                })
                .subscribe(
                        e->Log.d("TAG1", "Resultado: " + e)
                );

    }

    /*
    FIN
     */

    private void probarCreate(){

       // Observable<String> crearObservable =
                Observable
                .create(new ObservableOnSubscribe<String>() {
                            @Override
                            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                                try{
                                    emitter.onNext("A");
                                    emitter.onNext("l");
                                    emitter.onNext("b");
                                    emitter.onNext("e");
                                    emitter.onNext("r");
                                    emitter.onNext("t");
                                    emitter.onNext("o");
                                } catch (Exception e){
                                    emitter.onError(e);
                                }
                            }
                        }
                )
                .subscribe(
                        new Observer<String>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(String s) {
                                Log.d("TAG1", "onNext: " + s);
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        }
                );

    }

    private void probarCreateException(){
        Observable
                .create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> emitter) {
                        try{
                            emitter.onNext(15/3);
                            emitter.onNext(3/0);
                        }catch (Exception e){
                            emitter.onError(e);
                        }
                    }
                }).subscribe(
                new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d("TAG1", "onNext " + integer);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("TAG1", "onError " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                }
        );

    }

    /*
    Remplazar with Lambda
    Windows -> Alt + Enter
    Mac -> Option + Return
     */
    private void probarCrearTareaLargaConLambda(){
        Observable
                .create((ObservableOnSubscribe<String>) emitter -> {
                    try{
                        emitter.onNext(tareaLarga());
                    }catch (Exception e){
                        emitter.onError(e);
                    }
                })
                .subscribe(
                        (String s)->Log.d("TAG1", "onNext: " + s),
                        (Throwable e)->Log.d("TAG1", "onError: " + e),
                        ()->Log.d("TAG1", "onComplete")
                );
    }

    /*

    /*
    Lambdas -> Funciones Anonimas
        -> Esenciales para la programación declarativa
        -> Función sin nombre
        -> La clave de las funciones lambda es que es un metodo abstracto. Es decir un metodo que solo
           esta definido en una interfaz pero no ha sido implementado. Y por lo tanto el programador lo
           puede implemntar donde creamos necesario sin haber heredado de la interfaz.
        Ventajas Funciones Lamda
        1.- Funciones que solo se van a utilizar una vez.
        2.- La funcion puede ser utilizada como parametro de entrada de High Level Funcion
        3.- Muchas veces es una perdida de tiempo y esfuerzo generar una funcion. Es decir hay que irse
        fuera de la linea en la que estamos, hay que darle un buen nombre, ya sabeís que el nombre es
        importante y que no este repetido por nuestros compañeros de codigo, pero si solo
        vamos a utilizar esta función una vez pues no es necesario hacer este esfuerzo.
        4.- Funcionan muy bien con filter, map, reduce etc...
        5.- Codigo más claro
        6.- Reduces las lineas de Codigo. Es decir escribes menos boirpolate codigo

    Extructura
        (argumentos) -> {cuerpo o body "expresión"}
        (arg1, arg2) -> {arg1+arg2}
     */

    Sumar sumar = new Sumar() {
        @Override
        public int apply(int a, int b) {
            int resultado;
            resultado = a+b;
            return resultado;
        }
    };

    Sumar sumarL = (a,b)->a+b;


    private String tareaLarga(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Tarea Larga";
    }

    private void probarCrearHipoteca(){
        Log.d("TAG1", "comienza");
        calculaHipoteca();
        Log.d("TAG1", "Finaliza");
    }

    private double calculaHipoteca(){

        int loanAmount =1000;
        int termInYears = 50;
        double interestRate = 6.5;

        // Convert interest rate into a decimal
        // eg. 6.5% = 0.065

        interestRate /= 100.0;

        // Monthly interest rate
        // is the yearly rate divided by 12

        double monthlyRate = interestRate / 12.0;

        // The length of the term in months
        // is the number of years times 12

        int termInMonths = termInYears * 12;

        // Calculate the monthly payment
        // Typically this formula is provided so
        // we won't go into the details

        // The Math.pow() method is used calculate values raised to a power

        double monthlyPayment =
                (loanAmount*monthlyRate) /
                        (1-Math.pow(1+monthlyRate, -termInMonths));

        return monthlyPayment;
    }



}
