package com.predixteam.predixai.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.auth.FirebaseAuth
import com.predixteam.predixai.R
import com.predixteam.predixai.databinding.FragmentProfileBinding
import com.predixteam.predixai.ui.login.LoginActivity

class ProfileFragment : Fragment() {

    private lateinit var mAuth : FirebaseAuth
    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAuth = FirebaseAuth.getInstance()

        val currentUser = mAuth.currentUser
        binding.tvId.text = currentUser?.uid
        binding.tvName.text = currentUser?.displayName
        binding.tvEmail.text = currentUser?.email
        Glide.with(requireActivity())
            .load(currentUser?.photoUrl)
            .apply(RequestOptions().placeholder(R.drawable.ic_loading).error(R.drawable.ic_error))
            .into(binding.imgProfile)

        binding.btnLogout.setOnClickListener {
            mAuth.signOut()
            startActivity(Intent(activity, LoginActivity::class.java))
            activity?.finish()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}