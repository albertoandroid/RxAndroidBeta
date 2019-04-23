package com.androiddesdecero.rxandroidbeta;

import android.os.Bundle;
import android.util.Log;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class RX03EmpleadoActivity extends AppCompatActivity {

    private CompositeDisposable disposable;
    private DisposableObserver<Empleado> empleadoObserverPar;
    private Observable<Empleado> empleadoObservable;
    private List<Empleado> empleados;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx03_empleado);

        empleados = Empleado.setUpEmpleados();

        disposable = new CompositeDisposable();

        disposable.add(getEmpleadosObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<Empleado, Empleado>() {
                    @Override
                    public Empleado apply(Empleado empleado) throws Exception {
                        // Making the note to all uppercase
                        empleado.setNombre(empleado.getNombre().toUpperCase());
                        return empleado;
                    }
                })
                .subscribeWith(getEmpleadosObserver()));
        for(Empleado e: empleados){
            Log.d("TAG11", e.getNombre());
        }
    }


    private Observable<Empleado> getEmpleadosObservable() {

        /*
        public interface ObservableOnSubscribe<T>
           void subscribe(@NonNull ObservableEmitter<T> emitter)
           emitter -> the safe emitter instance, never null

           ObservableOnSubsribe es una interfaz funcional que tiene el metodo subscribe()
           que recibe una instancia de ObservableEmiter que permite impulsar
           eventos de forma segura para su cancelación


        Interface ObservableEmitter<T>
        T - the value type to emit
        boolean isDisposed()
        Devuelve verdadero si la secuencia eleminada en sentido descendente
        o el emisor se termió a través de Emitter.onError

         */

        return Observable.create(new ObservableOnSubscribe<Empleado>() {
            @Override
            public void subscribe(ObservableEmitter<Empleado> emitter) throws Exception {
                for (Empleado empleado : empleados) {
                    if (!emitter.isDisposed()) {
                        emitter.onNext(empleado);
                    }
                }

                if (!emitter.isDisposed()) {
                    emitter.onComplete();
                }
            }
        });

    }

    private DisposableObserver<Empleado> getEmpleadosObserver() {
        return new DisposableObserver<Empleado>() {

            @Override
            public void onNext(Empleado empleado) {
                Log.d("TAG1", "Empleado: " + empleado.getNombre());
            }

            @Override
            public void onError(Throwable e) {
                Log.e("TAG1", "onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d("TAG1", "Todos los empleados han sido emitidos!");
            }
        };
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }
}
