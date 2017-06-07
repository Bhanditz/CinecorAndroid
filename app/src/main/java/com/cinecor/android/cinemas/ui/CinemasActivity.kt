package com.cinecor.android.cinemas.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import com.cinecor.android.R
import com.cinecor.android.common.model.Cinema
import com.cinecor.android.common.ui.BaseActivity
import com.cinecor.android.common.viewmodel.CinemaViewModel
import com.cinecor.android.common.viewmodel.CinemaViewModelFactory
import kotlinx.android.synthetic.main.activity_cinemas.*
import javax.inject.Inject

class CinemasActivity : BaseActivity(), Observer<List<Cinema>> {

    @Inject lateinit var factory: CinemaViewModelFactory
    @Inject lateinit var adapter: CinemasAdapter

    private lateinit var viewModel: CinemaViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cinemas)
        setupView()
        getCinemas()
    }

    private fun setupView() {
        pager.offscreenPageLimit = 2
        pager.adapter = adapter
    }

    private fun getCinemas() {
        viewModel = ViewModelProviders.of(this, factory).get(CinemaViewModel::class.java)
        viewModel.cinemas.observe(this, this)
    }

    override fun onChanged(cinemas: List<Cinema>?) {
        cinemas?.let { adapter.setCinemas(it) }
    }
}
