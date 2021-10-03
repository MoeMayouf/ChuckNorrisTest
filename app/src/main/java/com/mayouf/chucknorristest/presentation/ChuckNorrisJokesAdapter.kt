package com.mayouf.chucknorristest.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mayouf.chucknorristest.databinding.JokesRowBinding
import com.mayouf.chucknorristest.domain.model.ChuckNorrisModel
import com.mayouf.chucknorristest.domain.model.Value

class ChuckNorrisJokesAdapter(private val chuckNorris: ChuckNorrisModel) :
    RecyclerView.Adapter<ChuckNorrisJokesAdapter.ChuckNorrisViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChuckNorrisViewHolder {
        val holderJokesBinding =
            JokesRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChuckNorrisViewHolder(holderJokesBinding)
    }

    override fun onBindViewHolder(holder: ChuckNorrisViewHolder, position: Int) {
        holder.onBind(chuckNorris.value[position])
    }

    override fun getItemCount() = chuckNorris.value.size

    inner class ChuckNorrisViewHolder(private val itemBinding: JokesRowBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun onBind(value: Value) {
            itemBinding.tvChuckNorrisJokes.text = value.joke
        }

    }

}
