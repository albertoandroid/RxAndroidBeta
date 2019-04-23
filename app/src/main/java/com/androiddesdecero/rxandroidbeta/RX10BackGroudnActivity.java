package com.androiddesdecero.rxandroidbeta;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RX10BackGroudnActivity extends AppCompatActivity {

    private Disposable disposable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx10_back_groudn);


        /*
        Sin Lambdas
         */

        Log.d("TAG1", "----------------------Sin Lambdas----------------------");
        Observable observableCreateSinLambdas = Observable.create(new ObservableOnSubscribe<Double>() {
            @Override
            public void subscribe(ObservableEmitter<Double> emitter) throws Exception {
                try {
                    Log.d("TAG1", "Observable en Hilo Sin Lambdas: " + Thread.currentThread().getName());
                    if(!emitter.isDisposed()){
                        emitter.onNext(calculaHipoteca());//Emitimos onNet
                        emitter.onComplete();
                    }
                           //Emitimos el onComplete
                } catch (Exception e) {
                    if(!emitter.isDisposed()){
                        emitter.onError(e);        // Si hay error lo emitimos
                    }
                }
            }
        });
        observableCreateSinLambdas
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                new Observer<Double>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(Double loan) {
                        Log.d("TAG1", "Hilo Sin Lambdas: " +Thread.currentThread().getName() + " onNext: " + loan);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        Log.d("TAG1", "onComplete: com Lambdas");
                    }
                }
        );


        Log.d("TAG1", "----------------------Con Lambdas----------------------");
        Observable<Double> observable1 = Observable.create(subscriber -> {
            try{
                subscriber.onNext(calculaHipoteca());
                //subscriber.onNext(calculaHipoteca());
                subscriber.onComplete();
            }catch (Exception e){
                subscriber.onError(e);
            }
        });

        observable1
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                i -> Log.d("TAG1", "onNext: Com Lambdas " + i),  //OnNext
                Throwable::printStackTrace, //OnError
                () -> Log.d("TAG1", "onCompleted con Lambdas") //OnCompleted
        );


        Log.d("TAG1", "----------------------Utilizando un Creador de Obsevables con la Tarea Lara Que se Le pasa como Parametro----------------------");
        Observable<Double> observable2 = Hipoteca.getInstance().crearObservable(calculaHipoteca());
        observable2.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        i -> Log.d("TAG1", "onNext: Com Lambdasff " + i),  //OnNext
                        Throwable::printStackTrace, //OnError
                        () -> Log.d("TAG1", "onCompleted con Lambdasfff") //OnCompleted
                );
    }


    private double calculaHipoteca(){

        int loanAmount =1000;
        int termInYears = 2;
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();

    }
}
