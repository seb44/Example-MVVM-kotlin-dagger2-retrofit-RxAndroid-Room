package com.transports.mvvmtan.ui.TanArret

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.transports.mvvmtan.R
import com.transports.mvvmtan.injection.ViewModelFactory
import kotlinx.android.synthetic.main.activity_tan_arret.*

class TanArretActivity : AppCompatActivity() {

    private lateinit var viewModel: TanArretListViewModel

    private var errorSnackbar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tan_arret)

        //viewModel = ViewModelProviders.of(this).get(TanArretListViewModel::class.java)
        viewModel = ViewModelProviders.of(this, ViewModelFactory(this)).get(TanArretListViewModel::class.java)

        viewModel.loadingVisibility.observe(this, Observer {
            it?.let {
                progressBar.visibility = it
                Log.d("MVVMTAN", "PROGRESS_BAR = $it")
            }
        }
        )

        viewModel.errorMessage.observe(this, Observer { errorMessage ->

            errorMessage?.let {
                errorSnackbar = Snackbar.make(findViewById(R.id.progressBar), it, Snackbar.LENGTH_INDEFINITE)
                errorSnackbar?.setAction(R.string.retry, viewModel.errorClickListener)
                errorSnackbar?.show()
            }
        })

        viewModel.listTanArrets.observe(this, Observer { listeDesArrets ->

            Log.d("MVVMTAN", "Il y a ${listeDesArrets?.size} arrêts")
            listeDesArrets?.forEach {
                Log.d("MVVMTAN", "Arret ${it.codeLieu} - ${it.libelle}")
            }
        })
    }
}