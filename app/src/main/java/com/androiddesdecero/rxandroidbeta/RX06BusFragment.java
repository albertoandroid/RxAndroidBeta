package com.androiddesdecero.rxandroidbeta;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;


public class RX06BusFragment extends Fragment {
    private TextView mTvPrimerFragment;
    private EditText mEtPrimerFrgament;
    private Button mBtPrimerFragment;
    private CompositeDisposable compositeDisposable;


    public RX06BusFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RX06BusFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RX06BusFragment newInstance(String param1, String param2) {
        RX06BusFragment fragment = new RX06BusFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_rx06_bus, container, false);
        configFragment(view);
        return view;
    }

    private void configFragment(View view){
        mTvPrimerFragment = view.findViewById(R.id.primer_fragment_tv);


        compositeDisposable = new CompositeDisposable();

        //Observamos los eventos del bus.
        Observable observable = RX06RXBus.getInstance().getEvents();
        compositeDisposable.add(
                observable.subscribe(
                        e->{
                            mTvPrimerFragment.setText((String)e);
                        })
        );

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}
