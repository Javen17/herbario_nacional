package com.example.herbario_nacional.ui.Fragments


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer

import com.example.herbario_nacional.R
import com.example.herbario_nacional.ui.Activities.NoLoginActivity
import com.example.herbario_nacional.ui.viewModels.MeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class NotificationsFragment : Fragment() {

    private val meViewModel: MeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_notifications, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var currentUser: Int? = 0;

        meViewModel.uiState.observe(viewLifecycleOwner, Observer {
            val dataState = it ?: return@Observer
            if (dataState.result != null && !dataState.result.consumed){
                dataState.result.consume()?.let { result ->
                    currentUser = result.data.id
                    Toast.makeText(context, "Usuario actual: $currentUser", Toast.LENGTH_LONG).show()
                }
            }
            if (dataState.error != null && !dataState.error.consumed){
                dataState.error.consume()?.let { error ->
                    Toast.makeText(context, resources.getString(error), Toast.LENGTH_LONG).show()
                    showActivity(NoLoginActivity::class.java)
                }
            }
        })
    }

    private fun showActivity(activityClass: Class<*>) {
        val intent = Intent(activity, activityClass)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        activity?.finish()
    }


}
