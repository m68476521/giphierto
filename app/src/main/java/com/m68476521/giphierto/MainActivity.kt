package com.m68476521.giphierto

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.m68476521.giphierto.api.GiphyManager
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

const val API_KEY = ""
class MainActivity : AppCompatActivity() {

    private var imagesAdapter = ImagesAdapter()
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        GiphyManager.setToken(API_KEY)
        images.layoutManager = GridLayoutManager(this, 3)
        images.adapter = imagesAdapter
        search()
    }

    private fun search() {
        val disposable = GiphyManager.giphyApi.trending()
            .subscribeOn(Schedulers.io())
            .subscribe({
                runOnUiThread { imagesAdapter.swapImages(it.data) }
            }, { it.printStackTrace() })
        compositeDisposable.add(disposable)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}