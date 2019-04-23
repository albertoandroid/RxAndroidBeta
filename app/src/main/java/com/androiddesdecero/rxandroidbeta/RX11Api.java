package com.androiddesdecero.rxandroidbeta;

import com.androiddesdecero.rxandroidbeta.model.Contributor;
import com.androiddesdecero.rxandroidbeta.model.Repository;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RX11Api {

    @GET("users/{user}/repos")
    Observable<List<Repository>> listRepos(@Path("user") String user);

    @GET("repos/{user}/{repo}/contributors")
    Observable<List<Contributor>> listRepoContributors(
            @Path("user") String user,
            @Path("repo") String repo);
}
