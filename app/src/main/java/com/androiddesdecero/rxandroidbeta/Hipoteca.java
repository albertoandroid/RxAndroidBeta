package com.androiddesdecero.rxandroidbeta;

import io.reactivex.Observable;

public class Hipoteca<T> {

    public static Hipoteca instance;

    private Hipoteca(){

    }

    public static synchronized Hipoteca getInstance(){
        if(instance == null){
            instance = new Hipoteca();
        }
        return instance;
    }


    public Observable crearObservable(T t){
        return Observable.create(subscriber -> {
            try{
                subscriber.onNext(t);
                //subscriber.onNext(calculaHipoteca());
                subscriber.onComplete();
            }catch (Exception e){
                subscriber.onError(e);
            }
        });
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



}
