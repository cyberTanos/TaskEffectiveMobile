package com.example.taskeffectivemobile

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeUnit.SECONDS

class Repository {

    val behaviorSubject = BehaviorSubject.create<String>()

    fun getFirstTaskData(): Single<Person> {
        return Single.just(Person("Tanya")).concatMap {
            Single.just(it)
                .delay(3, TimeUnit.SECONDS)
        }
    }

    fun getSecondTaskData(): Observable<String> {
        return Observable.interval(1, SECONDS).concatMap {
            Observable.just(it.toString())
        }
    }

    fun getCardOne(): Observable<FirstCard> {
        return Observable.just(FirstCard("cardOne")).map {
            throw Exception()
        }
    }

    fun getCardTwo(): Observable<SecondCard> {
        return Observable.just(SecondCard("cardTwo"))
    }
}

data class Person(
    val name: String
)

open class Card(open val name: String)

data class FirstCard(
    override val name: String
) : Card(name)

data class SecondCard(
    override val name: String
) : Card(name)
