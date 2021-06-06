package com.predixteam.predixai.ui.article

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.predixteam.predixai.data.source.remote.response.ResponseItem
import com.predixteam.predixai.databinding.FragmentArticleBinding
import com.predixteam.predixai.ui.article.details.DetailsArticleActivity
import com.predixteam.predixai.viewmodel.ViewModelFactory

class ArticleFragment : Fragment() {

    private lateinit var articleViewModel: ArticleViewModel
    private lateinit var adapter: ArticleAdapter
    private var _binding: FragmentArticleBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentArticleBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = ViewModelFactory.getInstance()
        articleViewModel = ViewModelProvider(this, factory).get(ArticleViewModel::class.java)
        adapter = ArticleAdapter()
        binding.rvArticle.adapter = adapter
        binding.rvArticle.layoutManager = LinearLayoutManager(requireActivity())
        articleViewModel.getArticle().observe(viewLifecycleOwner, {
            adapter.setArticle(it)
        })
        adapter.setOnItemClickCallback(object : ArticleAdapter.OnItemClickCallback{
            override fun onItemClicked(data: ResponseItem) {
                val intent = Intent(activity, DetailsArticleActivity::class.java)
                intent.putExtra(DetailsArticleActivity.EXTRA_DATA, data)
                startActivity(intent)
            }

        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}