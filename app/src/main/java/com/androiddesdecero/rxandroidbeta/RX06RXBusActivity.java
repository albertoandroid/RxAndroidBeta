package com.androiddesdecero.rxandroidbeta;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import io.reactivex.Observable;


public class RX06RXBusActivity extends AppCompatActivity {

    /*
    La idea de Bus es que puedes conectar dos objetos, dos clases,
    que tienen un ciclo de vida diferente de una manera simple.
    Es decir como podemos conectar Activities con Fragment, Service
    o Digalog. Pues con RXBus.
     */
    private RX06BusFragment mPrimerFragment;
    private FragmentTransaction mFrgamentTransaction;
    private Button btRXBus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx06_rxbus);

        mPrimerFragment = new RX06BusFragment();
        mFrgamentTransaction = getSupportFragmentManager().beginTransaction();
        mFrgamentTransaction.add(R.id.frame2, mPrimerFragment);
        mFrgamentTransaction.commit();


        btRXBus = findViewById(R.id.btRXBus);


        //Observamos los eventos del bus.
        Observable observable = RX06RXBus.getInstance().getEvents();
        observable.subscribe(
                e-> Log.d("TAG1", "onString: " + e)
        );

        btRXBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RX06RXBus.getInstance().setEvents("Hola");
            }
        });


    }


}
