package com.mayouf.chucknorristest.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mayouf.chucknorristest.databinding.FragmentChuckNorrisBinding
import com.mayouf.chucknorristest.domain.model.ChuckNorrisModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChuckNorrisFragment : Fragment() {
    private var fragmentChuckNorrisBinding: FragmentChuckNorrisBinding? = null
    private val viewModel: ChuckNorrisViewModel by viewModels()
    private var adapter: ChuckNorrisJokesAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getChuckNorrisJokes()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentChuckNorrisBinding = FragmentChuckNorrisBinding.inflate(inflater, container, false)

        viewModel.loadingLiveData.observe(viewLifecycleOwner, {
            fragmentChuckNorrisBinding!!.tvErrorMessage.visibility = View.GONE
            fragmentChuckNorrisBinding!!.progressBar.visibility =
                if (it) View.VISIBLE else View.GONE
        })

        viewModel.contentLiveData.observe(viewLifecycleOwner, {
            fragmentChuckNorrisBinding!!.tvErrorMessage.visibility = View.GONE
            initRecyclerView(it)
        })

        viewModel.errorLiveData.observe(viewLifecycleOwner, {
            fragmentChuckNorrisBinding!!.tvErrorMessage.visibility = View.VISIBLE
            fragmentChuckNorrisBinding!!.tvErrorMessage.text = it.message
        })

        return fragmentChuckNorrisBinding!!.root
    }

    override fun onDestroy() {
        fragmentChuckNorrisBinding = null
        super.onDestroy()
    }


    private fun initRecyclerView(chuckNorris: ChuckNorrisModel) {
        Log.i("ChuckNorrisFragment", chuckNorris.toString())
        adapter = ChuckNorrisJokesAdapter(chuckNorris)
        fragmentChuckNorrisBinding!!.jokesRecyclerView.adapter = adapter
    }

    companion object {
        val FRAGMENT_NAME: String = ChuckNorrisFragment::class.java.name

        @JvmStatic
        fun newInstance() =
            ChuckNorrisFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}