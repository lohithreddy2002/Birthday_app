@file:Suppress("DEPRECATION")

package com.example.birthday_app.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.birthday_app.R
import com.example.birthday_app.birth_viewmodel
import com.example.birthday_app.birthfactory
import com.example.birthday_app.databinding.FragmentAddToListFragmentBinding
import com.example.birthday_app.db.entity.birthdaye
import com.example.birthday_app.db.repository.birth_repository
import com.example.birthday_app.db.repository.birthday_database
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat


class add_to_list_fragment : Fragment() {
    private lateinit var binding: FragmentAddToListFragmentBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_add_to_list_fragment, container, false)

    }

    override fun onStart() {
        super.onStart()
        binding = FragmentAddToListFragmentBinding.bind(requireView())
        val databse = birthday_database(this.requireContext())
        val repo = birth_repository(databse)
        val factory = birthfactory(repo)
        binding.button.visibility = View.INVISIBLE

        val viewModel = ViewModelProviders.of(this,factory).get(birth_viewmodel::class.java)


        val date = MaterialDatePicker.Builder.datePicker().build()

        date.addOnPositiveButtonClickListener {
            Toast.makeText(this.context, "date picked", Toast.LENGTH_SHORT).show()
            binding.button.visibility = View.VISIBLE
        }
        date.addOnNegativeButtonClickListener {
            Toast.makeText(this.context, "date not picked", Toast.LENGTH_SHORT).show()
        }
        binding.date.setOnClickListener {
            date.show(childFragmentManager,"s")

        }
        binding.button.setOnClickListener {
            val name = binding.nameToAdd.text.toString()
            val format =SimpleDateFormat("dd/MM/yyyy")
            val date_ = format.format(date.selection)

            val x = birthdaye(name,date_)
            viewModel.insert(x)
            findNavController().navigate(R.id.action_add_to_list_fragment_to_mainfragment)
        }
    }
}