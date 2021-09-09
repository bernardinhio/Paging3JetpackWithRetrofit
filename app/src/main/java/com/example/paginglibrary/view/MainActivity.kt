package com.example.paginglibrary.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import com.example.paginglibrary.R
import com.example.paginglibrary.api.RetrofitApi
import com.example.paginglibrary.databinding.ActivityMainBinding
import com.example.paginglibrary.viewmodel.MainFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * https://www.youtube.com/watch?v=xao8KwaaKwQ
 * https://proandroiddev.com/how-to-use-the-paging-3-library-in-android-5d128bb5b1d8
 * https://stackoverflow.com/questions/64370736/how-to-show-empty-view-while-using-android-paging-3-library
 *
 * The Retrofit might return to us an Empty pr Null result for our call
 * of the Api. In this case it is better to hide completely the RecyclerView
 * because we only want to return from the PagingSource, a LoadResult.Page
 * when there is result and we return LoadResult.Error when there is error
 * or empty or null Result
 *
 * In the case we return LoadResult.Error, the Repository cannot create a flow
 * of data that we can use to fill the View or the RecyclerView.
 *
 * This is why it is important that we handle the LoadState in case of
 * Error or Empty/null Result to for example hide the RecyclerView or
 * show some text instead or Button to retry with other Query or after
 * connecting to Internet.
 *
 * In most cases we have configured the RecyclerView with background or
 * with Margins and padding at the bottom so better is to not show it at all
 *
 *
 *
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var viewBinding: ActivityMainBinding
    val viewModel: MainFragmentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding =  ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
    }

}