package com.example.taskeffectivemobile

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.example.taskeffectivemobile.databinding.ActivityMainBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit.SECONDS

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private val repository = Repository()
    private val adapter = TaskThreeAdapter()
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        taskThree()
        taskFour()

        binding.button.setOnClickListener {
            taskFive_1()
        }
    }

    // Сделайте сетевой запрос и отобразите результат на экране
    private fun taskOne() {
        compositeDisposable.add(
            repository.getFirstTaskData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    binding.text.text = "Загрузка данных"
                }
                .subscribe(
                    { person -> // onSuccess
                        binding.text.text = "Данные: ${person.name}"
                    },
                    { error -> // onError
                        binding.text.text = "Ошибка: ${error.message}"
                    }
                )
        )
    }

    // Сделайте таймер. TextView которая раз в секунду меняется (timer)
    private fun taskTwo() {
        compositeDisposable.add(
            repository.getSecondTaskData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    binding.text.text = it
                }
        )
    }

    /* Сделайте ресайклер. По нажатию на элемент передавайте его позицию во фрагмент
     и во фрагменте этот номер отображайте в тосте. (Subject)*/
    private fun taskThree() {
        binding.recycler.adapter = adapter
        val listName = listOf(
            Person("Tanya"),
            Person("Vanya"),
            Person("Pavel"),
            Person("Lida"),
            Person("Bob"),
        )
        adapter.submitList(listName)
        compositeDisposable.add(
            adapter.behaviorSubject.subscribe {
                Toast.makeText(applicationContext, "$it", Toast.LENGTH_SHORT).show()
            }
        )
    }

    /* Сделайте EditText. При наборе текста выводите в лог содержимое EditText всегда,
     когда пользователь 3 секунды что-то не вводил (debounce)*/
    private fun taskFour() {
        binding.editText.doAfterTextChanged { text ->
            repository.behaviorSubject.onNext(text.toString())
        }
        compositeDisposable.add(
            repository.behaviorSubject
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .debounce(3, SECONDS)
                .subscribe { // onNext
                    Log.d("TAGGGGGGG", "taskFour: $it")
                }
        )
    }

    // Есть 2 сервера на которых лежат скидочные карты. Нужно получить эти данные и вывести в единый список. (zip и тд)
    /* а) Если 1 из запросов падает, то все равно выводить
     (найти метод RX для такого, чтоб самому не прописывать логику)*/
    private fun taskFive_1() {
        compositeDisposable.add(
            Observable.merge(
                repository.getCardOne().onErrorComplete(),
                repository.getCardTwo().onErrorComplete()
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .toList()
                .subscribe({ result -> // onSuccess
                    Log.d("TAGGGG", "List Cards: ${result}")
                },
                    { error -> // onError
                        Log.d("TAGGGG", "Ошибка: ${error.message}")
                    })
        )
    }

    // б) Если 1 из запросов падает, то не выводить ничего (найти метод RX)
    private fun taskFive_2() {
        compositeDisposable.add(
            Observable.merge(
                repository.getCardOne(),
                repository.getCardTwo()
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .toList()
                .toMaybe()
                .onErrorComplete()
                .subscribe({ result -> // onSuccess
                    Log.d("TAGGGG", "List Cards: ${result}")
                },
                    { error -> // onError
                        Log.d("TAGGGG", "Ошибка: ${error.message}")
                    },
                    {
                        Log.d("TAGGGG", "Complete")
                    })
        )
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
        _binding = null
    }
}
