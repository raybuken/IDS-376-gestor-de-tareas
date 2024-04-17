package com.example.tasktify.ui.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.tasktify.LoginActivity
import com.example.tasktify.MainActivity
import com.example.tasktify.R
import com.example.tasktify.TaskDetailsActivity
import com.example.tasktify.adapter.TaskListAdapter
import com.example.tasktify.databinding.FragmentProfileBinding
import com.example.tasktify.model.Task
import com.example.tasktify.ui.home.HomeViewModel

class ProfileFragment: Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val logoutButton = view.findViewById<TextView>(R.id.logout)

        logoutButton.setOnClickListener(){
            val sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()

            editor.putString("token", "")
            editor.putBoolean("isLoggedIn", false)

            editor.apply()
            startActivity(Intent(context, LoginActivity::class.java))

            requireActivity().finish()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}