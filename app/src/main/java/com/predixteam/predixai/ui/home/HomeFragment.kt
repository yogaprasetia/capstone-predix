package com.predixteam.predixai.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.predixteam.predixai.data.ModelEntity
import com.predixteam.predixai.databinding.FragmentHomeBinding
import com.predixteam.predixai.ui.detection.DetectionActivity

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var homeAdapter: HomeAdapter
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        val model = homeViewModel.getModels()
        homeAdapter = HomeAdapter()
        homeAdapter.setModels(model)

        binding.rvModel.apply {
            adapter = homeAdapter
            layoutManager = LinearLayoutManager(requireActivity())
        }

        homeAdapter.setOnItemClickCallback(object : HomeAdapter.OnItemClickCallback{
            override fun onItemClicked(data: ModelEntity) {
                startActivity(Intent(activity, DetectionActivity::class.java))
            }

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}