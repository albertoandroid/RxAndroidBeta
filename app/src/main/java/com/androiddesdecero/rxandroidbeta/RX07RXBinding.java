package com.androiddesdecero.rxandroidbeta;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class RX07RXBinding extends AppCompatActivity {

    /*
    RX nos permite controlar eventos asincronos de una manera directa.
    Por lo tanto las interacciones con la UI tambien se pueden simplicar
    escecialmente cuando hay multiplies eventos de la UI que puden
    lanzarse.
    Par ahacerlo bien necesitamos de Handler, AsyncTas y demás.
    Esta lógica la podemos simplicar con RX:

    RxBinding es un conjunto de bibliotecas
    de soporte para facilitar la implementanción de la interación
    del usuario con los elementos de la
    interfaz de usuario en Androi.
     */

    private Button btRXBinding;
    private EditText etRXBinding;
    private EditText etRXBinding1;
    private Button btRXBinding1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx07_rxbinding);

        btRXBinding = findViewById(R.id.btRXBinding);
        btRXBinding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Manjeamos evento OnClick
            }
        });

        //Manejar evento onClick de un Boton.
        Disposable d = RxView.clicks(btRXBinding).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) {
                Log.d("TAG1", "accept");
            }
        });

        etRXBinding = findViewById(R.id.etRXBinding);
        etRXBinding.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //manejar TextChange de un TextView
        Disposable d1 = RxTextView.textChanges(etRXBinding)
                .subscribe(new Consumer<CharSequence>() {
                    @Override
                    public void accept(CharSequence charSequence) throws Exception {
                        //Add your logic to work on the Charsequence
                        Log.d("TAG1", "onTextChanged");
                    }
                });


        //Debounce -> Si se pulsa un botón repetidamente con debunce
        //sabemos que solo va a coger un click cada segundo desde el
        //evento anterior.

        btRXBinding1 = findViewById(R.id.btRXBinding1);
        RxView.clicks(btRXBinding1)
                .debounce(500, TimeUnit.MILLISECONDS)
                .subscribe(aVoid -> {
                    Log.d("TAG1", "onClic");
                });


        etRXBinding1 = findViewById(R.id.etRXBinding1);
        /*
        RxView.clicks(etRXBinding1).debounce(1, TimeUnit.SECONDS)
                .map(e->etRXBinding1.getText().toString())
                .subscribe(
                        e-> Log.d("TAG1", e)
                );
                */

        RxTextView.textChanges(etRXBinding1)
                .debounce(1, TimeUnit.SECONDS)
                .map(e->etRXBinding1.getText().toString())
                .subscribe(
                        e-> Log.d("TAG1", e)
                );

    }
}
