package com.androiddesdecero.rxandroidbeta;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.AsyncSubject;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.ReplaySubject;

public class RX05PublishActivity extends AppCompatActivity {

    /*
    Un Subject es una especie de puente o proxy que actua a la vez
    como observador y observer.
    Como es un observer, puede subscribirse a uno o más
    observables.
    Como es un observable puede pasar items a sus observers.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx05_publish);

        //publishSubjectExample();
        //replaySubjectExample();
        asyncSubjectExample();

    }

    /*
        Publish Subject
        Emite todos los elementos subsquiguentes de la
        fuente Observable en el momento de la subscripcion.
         */

    private void publishSubjectExample(){
        PublishSubject<String> source = PublishSubject.create();


        Observer<String> primerObserver = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(String string) {
                Log.d("TAG1", "Primer Observer onNext value : " + string);

            }

            @Override
            public void onError(Throwable e) {
                Log.d("TAG1", " Primer onError : " + e.getMessage());

            }

            @Override
            public void onComplete() {
                Log.d("TAG1", " Primer onComplete");

            }
        };

        Observer<String> segundo0Observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(String string) {
                Log.d("TAG1", "Segundo Observer onNext value : " + string);

            }

            @Override
            public void onError(Throwable e) {
                Log.d("TAG1", " Segundo onError : " + e.getMessage());

            }

            @Override
            public void onComplete() {
                Log.d("TAG1", " Segundo onComplete");

            }
        };

        source.subscribe(primerObserver);//->Emtite Alberto y onComplete
        source.onNext("A");
        source.onNext("l");
        source.onNext("b");
        source.subscribe(segundo0Observer);//->Emtite erto y onComplete
        source.onNext("e");
        source.onNext("r");
        source.onNext("t");
        source.onNext("o");
        source.onComplete();
    }

    /*
    Replay Subject
    Emite todos los items de un observable, independientemnete de cuando se
    subscriba el subscriptor.
     */

    private void replaySubjectExample(){
        ReplaySubject<String> source = ReplaySubject.create();

        Observer<String> primerObserver = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(String string) {
                Log.d("TAG1", "Primer Observer onNext value : " + string);

            }

            @Override
            public void onError(Throwable e) {
                Log.d("TAG1", " Primer onError : " + e.getMessage());

            }

            @Override
            public void onComplete() {
                Log.d("TAG1", " Primer onComplete");

            }
        };

        Observer<String> segundo0Observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(String string) {
                Log.d("TAG1", "Segundo Observer onNext value : " + string);

            }

            @Override
            public void onError(Throwable e) {
                Log.d("TAG1", " Segundo onError : " + e.getMessage());

            }

            @Override
            public void onComplete() {
                Log.d("TAG1", " Segundo onComplete");

            }
        };

        source.subscribe(primerObserver); //->Emtite Alberto y onComplete
        source.onNext("A");
        source.onNext("l");
        source.onNext("b");
        source.onNext("e");
        source.onNext("r");
        source.onNext("t");
        source.onNext("o");
        source.onComplete();

        source.subscribe(segundo0Observer);//->Emtite Alberto y onComplete
    }

    /*
    Solo emite el último valor del observable
    solo despues de que el observable finaliza.
     */

    private void asyncSubjectExample(){

        AsyncSubject<String> source = AsyncSubject.create();

        Observer<String> primerObserver = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(String string) {
                Log.d("TAG1", "Primer Observer onNext value : " + string);

            }

            @Override
            public void onError(Throwable e) {
                Log.d("TAG1", " Primer onError : " + e.getMessage());

            }

            @Override
            public void onComplete() {
                Log.d("TAG1", " Primer onComplete");

            }
        };

        Observer<String> segundo0Observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(String string) {
                Log.d("TAG1", "Segundo Observer onNext value : " + string);

            }

            @Override
            public void onError(Throwable e) {
                Log.d("TAG1", " Segundo onError : " + e.getMessage());

            }

            @Override
            public void onComplete() {
                Log.d("TAG1", " Segundo onComplete");

            }
        };

        source.subscribe(primerObserver);//-> Solo emite la "o" y onComplete
        source.onNext("A");
        source.onNext("l");
        source.onNext("b");
        source.onNext("e");
        source.onNext("r");
        source.onNext("t");
        source.onNext("o");
        source.onComplete();

        source.subscribe(segundo0Observer);//-> Solo emite la "o" y onComplete
    }
}
