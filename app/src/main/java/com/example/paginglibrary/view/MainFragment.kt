package com.example.paginglibrary.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.paginglibrary.R
import com.example.paginglibrary.adapter.PhotosLoadStateAdapter
import com.example.paginglibrary.adapter.PhotosPagingDataAdapter
import com.example.paginglibrary.databinding.FragmentMainBinding
import com.example.paginglibrary.viewmodel.MainFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint


/**
 *
 * VERY IMPORTANT  setHasFixedSize(false)  RECYCLER VIEWif
 * our RecyclerView has other views below it or above it in
 * the Parent
 *
 * We can keep setHasFixedSize(true) IF the height of the
 * recyclerView is match_parent !!
 *
 * Best way is to use a returned flow from teh Pager in the Repo
 */

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {

    lateinit var viewBinding: FragmentMainBinding
    val viewModel: MainFragmentViewModel by viewModels()
    lateinit var photosPagingDataAdapter: PhotosPagingDataAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentMainBinding.bind(view)

        photosPagingDataAdapter = PhotosPagingDataAdapter()


        val concatAdapter: ConcatAdapter = photosPagingDataAdapter.withLoadStateHeaderAndFooter(
            header = PhotosLoadStateAdapter(actionLambdaForOnClick = { photosPagingDataAdapter.retry() }),
            footer = PhotosLoadStateAdapter(actionLambdaForOnClick = { photosPagingDataAdapter.retry() })
        )


        with(viewBinding.rvResults){
            layoutManager = LinearLayoutManager(this.context)
            setHasFixedSize(false)  // IMPORTANT we have to make it FALSE not like Normal RecyclerView!!!

            adapter = concatAdapter
        }


        viewBinding.btnAddLoadStateListenerToPagingDataAdapter.setOnClickListener {
            //viewModel.giveMeLiveDataOfPagingDataOfPhotos("pinion").observe(
            //viewModel.giveMeLiveDataOfPagingDataOfPhotos("evrvbrjhbvjrbrjvb").observe(
            viewModel.giveMeLiveDataOfPagingDataOfPhotos("hello").observe(
                viewLifecycleOwner,
                { pagingData ->
                    viewBinding.rvResults.visibility = View.VISIBLE
                    photosPagingDataAdapter.submitData(
                        viewLifecycleOwner.lifecycle,
                        pagingData
                    )
                }
            )
        }



        photosPagingDataAdapter.addLoadStateListener { combinedLoadState ->

            when (combinedLoadState.source.refresh) {

                is LoadState.Loading -> {
                    Log.d("pagingLog", "addLoadStateListener: source.refresh = Loading")
                    viewBinding.pbProgressOutsideRecyclerView.visibility = View.VISIBLE
                    viewBinding.tvMessageOutsideRecyclerView.visibility = View.VISIBLE
                    viewBinding.tvMessageOutsideRecyclerView.text = "Searching Photos..."
                }

                is LoadState.NotLoading -> {
                    Log.d("pagingLog", "addLoadStateListener: source.refresh = NotLoading")
                    viewBinding.tvMessageOutsideRecyclerView.visibility = View.INVISIBLE
                    viewBinding.btnRetryOutsideRecyclerView.visibility = View.INVISIBLE
                    viewBinding.pbProgressOutsideRecyclerView.visibility = View.INVISIBLE
                }

                // for ex no Internet or Server broken
                is LoadState.Error -> {
                    Log.d("pagingLog", "addLoadStateListener: source.refresh = Error")
                    // show message and button retry
                    viewBinding.tvMessageOutsideRecyclerView.visibility = View.VISIBLE
                    viewBinding.tvMessageOutsideRecyclerView.text = (combinedLoadState.source.refresh as LoadState.Error).error.message
                    viewBinding.btnRetryOutsideRecyclerView.visibility = View.VISIBLE
                    viewBinding.btnRetryOutsideRecyclerView.setOnClickListener { photosPagingDataAdapter.retry() }
                    viewBinding.pbProgressOutsideRecyclerView.visibility = View.GONE
                    viewBinding.rvResults.visibility = View.INVISIBLE
                }
            }

        }





    }


}
