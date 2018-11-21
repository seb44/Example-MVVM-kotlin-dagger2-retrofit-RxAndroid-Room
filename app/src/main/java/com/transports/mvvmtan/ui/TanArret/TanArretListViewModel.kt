package com.transports.mvvmtan.ui.TanArret

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import android.view.View
import com.transports.mvvmtan.R
import com.transports.mvvmtan.base.BaseViewModel
import com.transports.mvvmtan.model.TanArret
import com.transports.mvvmtan.model.TanArretDao
import com.transports.mvvmtan.network.TanApi
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class TanArretListViewModel(private val tanArretDao: TanArretDao) : BaseViewModel() {

    @Inject
    lateinit var tanApi: TanApi

    private lateinit var subscription: Disposable

    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()

    val errorMessage: MutableLiveData<Int> = MutableLiveData()
    val errorClickListener = View.OnClickListener { loadTanArrets() }

    val listTanArrets: MutableLiveData<List<TanArret>> = MutableLiveData()

    init {
        loadTanArrets()
    }

    // Utilisation de Observable
   /* private fun loadTanArrets() {

        Log.d("MVVMTAN","TanArretListViewModel - Démarrage de loadTanArrets")

        subscription = Observable.fromCallable {
            // Récupération des données sauvegardées en database locale
            tanArretDao.getTanArrets()
            }
            .concatMap {
                    dbTanArretList ->
                if(dbTanArretList.isEmpty())   // S'il n'y a pas d'arrêts dans la base locale, appelle du WS pour les récupérer
                    tanApi.getTanStops().concatMap {apiTanArretList ->
                             tanArretDao.insertTanArrets(*apiTanArretList.toTypedArray()) // Sauvegarde des arrêts en local
                        Observable.just(apiTanArretList)
                    }
                else
                    Observable.just(dbTanArretList)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveTanArretListStart() }
            .doOnTerminate { onRetrieveTanArretListFinish() }
            .subscribe(
                { result -> onRetrieveTanArretListSuccess(result) },
                { onRetrieveTanArretListError(it) }
            )
    }*/

    // Utilisation de Single
    fun loadTanArrets() {

        subscription = Observable.fromCallable {
            // Récupération des données sauvegardées en database locale
            tanArretDao.getTanArrets()
        }
            .doOnNext { dbListeTanArrets ->

                // Nous allons maintenant récupérer la liste des arrets coté TAN au cas ou si les arrêts ont été mis à jour
                tanApi.getTanArrets()
                    .doOnSuccess { apiListeTanArrets ->

                        // Nous avons récupéré la nouvelle liste, que nous allons sauvegarder en db locale
                        // Suppression des données de la Base
                        tanArretDao.deleteAllTanArrets()

                        // Insertion des nouvelles données
                        tanArretDao.insertTanArrets(*apiListeTanArrets.toTypedArray())

                        // Nous avons mis à jour la liste, Informe cette mis à jour à l'UI
                        listTanArrets.postValue(apiListeTanArrets)
                    }
                    .doOnError {

                        // Informe à l'UI qu'une erreur s'est produite
                        errorMessage.postValue(R.string.tanarret_error)
                    }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        {},
                        {errorMessage.postValue(R.string.tanarret_error)})
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveTanArretListStart() }
            .doOnTerminate { onRetrieveTanArretListFinish() }
            .subscribe(
                { dbListeDesArrets ->
                    listTanArrets.postValue(dbListeDesArrets)
                },
                { error ->
                    errorMessage.postValue(R.string.tanarret_error)
                }
            )
    }


    private fun onRetrieveTanArretListStart() {
        loadingVisibility.value = View.VISIBLE
    }

    private fun onRetrieveTanArretListFinish() {
        loadingVisibility.value = View.GONE
    }


    private fun onRetrieveTanArretListSuccess(listeDesArrets: List<TanArret>?) {
        listTanArrets.value = listeDesArrets
        // equivalent à listTanArrets.setValue(listeDesArrets)
    }

    private fun onRetrieveTanArretListError(error: Throwable) {
        errorMessage.value = R.string.tanarret_error
    }


    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }

}