package com.teamfilmo.filmo.ui.write.thumbnail

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.teamfilmo.filmo.base.fragment.BaseFragment
import com.teamfilmo.filmo.databinding.FragmentReportThumbnailBinding
import com.teamfilmo.filmo.ui.write.adapter.MovieThumbnailAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class ReportThumbnailFragment : BaseFragment<FragmentReportThumbnailBinding, ReportThumbnailViewModel, ReportThumbnailEffect, ReportThumbnailEvent>(
    FragmentReportThumbnailBinding::inflate,
) {
    override val viewModel: ReportThumbnailViewModel by viewModels()
    private val movieThumbnailAdapter by lazy {
        context?.let { MovieThumbnailAdapter(it) }
    }

    companion object {
        fun newInstance(
            movieName: String,
            movieId: String,
        ): ReportThumbnailFragment {
            return ReportThumbnailFragment().apply {
                arguments =
                    Bundle().apply {
                        putString("MOVIE_NAME", movieName)
                        putString("MOVIE_ID", movieId)
                    }
            }
        }
    }

    override fun onBindLayout() {
        // 기본 스팬 적용
        val span = 3
        binding.movieImageGridView.layoutManager = GridLayoutManager(requireContext(), span)
        binding.movieImageGridView.adapter = movieThumbnailAdapter
        binding.movieImageGridView.layoutManager = GridLayoutManager(requireContext(), 3)
        movieThumbnailAdapter?.setViewType(0)

        movieThumbnailAdapter?.setOnItemClickListener(
            object : MovieThumbnailAdapter.OnItemClickListener {
                override fun onItemClick(
                    position: Int,
                    uri: String?,
                ) {
                    // todo : 감상문 작성화면에서 이미지 uri 전달하기
                    Timber.d("클릭 :  썸네일 uri : thumbnamefragment : $uri")
                    val result =
                        Bundle().apply {
                            putString("SELECTED_IMAGE_URI", uri.toString())
                        }
                    parentFragmentManager.setFragmentResult("REQUEST_OK", result)
                    parentFragmentManager.popBackStack()
                }
            },
        )

        binding.btnPoster.isSelected = true
        binding.btnBackground.isSelected = false
        binding.txtSelectedMovie.text = arguments?.getString("MOVIE_NAME")

        val movieId = arguments?.getString("MOVIE_ID")

        Timber.d("Report Thumbnail Fragment selectedMovieId : $movieId")

        lifecycleScope.launch {
            if (movieId != null) {
                viewModel.handleEvent(ReportThumbnailEvent.SelectPoster(movieId))
            }

            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.moviePosterList.collect {
                    movieThumbnailAdapter?.setPosterUriList(it)
                }
            }
        }

        binding.btnPoster.setOnClickListener {
            binding.movieImageGridView.layoutManager = GridLayoutManager(requireContext(), 3)
            movieThumbnailAdapter?.setViewType(0)
            binding.btnPoster.isSelected = true
            binding.btnBackground.isSelected = false

            lifecycleScope.launch {
                if (movieId != null) {
                    viewModel.handleEvent(ReportThumbnailEvent.SelectPoster(movieId))
                }

                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.moviePosterList.collect {
                        movieThumbnailAdapter?.setPosterUriList(it)
                    }
                }
            }
        }

        binding.btnBackground.setOnClickListener {
            val layoutManager = GridLayoutManager(requireContext(), 2)
            binding.movieImageGridView.layoutManager = layoutManager
            movieThumbnailAdapter?.setViewType(2)
            binding.btnPoster.isSelected = false
            binding.btnBackground.isSelected = true

            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    if (movieId != null) {
                        viewModel.handleEvent(ReportThumbnailEvent.SelectBackground(movieId))
                        viewModel.movieBackdropList.collect {
                            movieThumbnailAdapter?.setPosterUriList(it)
                        }
                    }
                }
            }
        }

        binding.btnBack.setOnClickListener {
            handleBackNavigation()
        }
    }

    private fun handleBackNavigation() {
        if (parentFragmentManager.backStackEntryCount > 0) {
            parentFragmentManager.popBackStack()
        } else {
            requireActivity().onBackPressed()
        }
    }
}
