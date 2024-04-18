package com.example.tasktify.ui.home

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
import com.example.tasktify.R
import com.example.tasktify.TaskDetailsActivity
import com.example.tasktify.adapter.TaskListAdapter
import com.example.tasktify.databinding.FragmentHomeBinding
import com.example.tasktify.model.Task

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPreferences = requireActivity().getSharedPreferences(
            "MyPrefs", Context.MODE_PRIVATE
        )

        val emptyTaskText = view.findViewById<TextView>(R.id.task_list_empty)

        val homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        homeViewModel.taskList.observe(viewLifecycleOwner) { taskList ->
            if(!taskList.isNullOrEmpty()){
                val taskListData = taskList.map { taskItem ->
                    Task(
                        title = taskItem.title,
                        description = taskItem.description,
                        status = taskItem.status,
                        id = taskItem.id,
                        date = taskItem.date,
                        statusId = taskItem.statusId
                    )
                }
                val taskListView = view.findViewById<ListView>(R.id.task_list_view)
                taskListView.adapter = TaskListAdapter(requireContext(), taskListData)

                emptyTaskText.text = ""
            }else{
                emptyTaskText.text = getString(R.string.empty_task_message)
            }
        }

        homeViewModel.loadTask(sharedPreferences)

        val taskListView = view.findViewById<ListView>(R.id.task_list_view)
        taskListView.setOnItemClickListener{_, itemView, _, _ ->
            val itemId = itemView.findViewById<TextView>(R.id.task_list_item_id).text.toString()
            val intent = Intent(activity, TaskDetailsActivity::class.java)

            intent.putExtra("ID", itemId)

            startActivity(intent)

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}