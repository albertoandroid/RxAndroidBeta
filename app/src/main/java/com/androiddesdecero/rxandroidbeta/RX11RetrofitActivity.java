package com.androiddesdecero.rxandroidbeta;

import android.os.Bundle;

import com.androiddesdecero.rxandroidbeta.model.Contributor;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RX11RetrofitActivity extends AppCompatActivity {

    private RX11Api api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx11_retrofit);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        api = retrofit.create(RX11Api.class);

        getTopContributors("albertoandroid").subscribe(System.out::println);



    }

    Observable<String> getTopContributors(String userName) {
        return api.listRepos(userName)
                .flatMapIterable(x -> x)
                .flatMap(repo -> api.listRepoContributors(userName, repo.getName()))
                .flatMapIterable(x -> x)
                .filter(c -> c.getContributions() > 100)
                .sorted((a, b) -> b.getContributions() - a.getContributions())
                .map(Contributor::getName)
                .distinct();
    }
}
