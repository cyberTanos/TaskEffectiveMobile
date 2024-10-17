package com.example.taskeffectivemobile

import kotlin.time.Duration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


//Расcказать на каком диспатчере выполнятся emitы

fun task1() {
    flow {
        emit(1) // Default
        withContext(Dispatchers.IO) {
            emit(2) // IO
        }
    }
}

//Необходимо сконвертировать функцию с колбэком в flow. Сделать средствами kotlin.x coroutines

suspend fun <T> flowFrom(api: CallbackBasedApi<T>): Flow<T> {
    return callbackFlow {
        val callback = object : Callback<T> {

            override fun onNextValue(value: T) {
                trySend(value)
            }

            override fun onApiError(cause: Throwable) {
                close(cause)
            }

            override fun onCompleted() {
                close()
            }
        }
        api.registerCallback(callback)
    }
}

/*Задача по flow
Напротив каждой строки написать диспатчер и порядок в котором вызывается цепочка*/

suspend fun task3() {
    CoroutineScope(context = Dispatchers.Main.immediate).launch() {
        doAction() // Main
        flowOf("Hey") // IO
            .onEach { doAction() } // IO
            .map { it.length } // IO
            .onStart { doAction() } // IO
            .flowOn(Dispatchers.Default)
            .flatMapMerge { // IO
                doAction() // IO
                flowOf(1) // Main
                    .flowOn(Dispatchers.Main)
                    .onEach { doAction() } // IO
            }
            .flowOn(Dispatchers.IO)
            .collect {
                doAction() // Main
            }
    }
}

// Напишите аналоги операторов throttleFisrt и throttleLatest из RxJava для Flow.

fun <T> Flow<T>.throttleFirst(timeoutMillis: Long): Flow<T> = flow {
    var lastEmissionTime = 0L
    collect { value ->
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastEmissionTime >= timeoutMillis) {
            lastEmissionTime = currentTime
            emit(value)
        }
    }
}

fun <T> Flow<T>.throttleLatest(period: Duration): Flow<T> =
    flow {
        conflate().collect {
            emit(it)
            delay(period)
        }
    }
