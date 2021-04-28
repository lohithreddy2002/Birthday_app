package com.example.birthday_app.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.birthday_app.R
import com.example.birthday_app.databinding.FragmentSignupfragmentBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class signupfragment : Fragment() {
    private lateinit var binding: FragmentSignupfragmentBinding
    private lateinit var auth:FirebaseAuth
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_signupfragment, container, false)
    }

    override fun onStart() {
        super.onStart()
        auth = Firebase.auth
        binding = FragmentSignupfragmentBinding.bind(requireView())
        binding.ssignup.setOnClickListener {
            val email = binding.semail.text.toString()
            val password = binding.spassword.text.toString()
            if(email == "" || password == ""){
                Toast.makeText(this.context, "enter the values", Toast.LENGTH_SHORT).show()
            }


            auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener { task->
                if(task.isSuccessful){
                    findNavController().navigate(R.id.action_signupfragment_to_login_fragment)
                }
                else{
                    Toast.makeText(this.context, "signup failes", Toast.LENGTH_SHORT).show()
                }


            }



        }


    }
}