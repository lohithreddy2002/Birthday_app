package com.example.birthday_app.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.birthday_app.R
import com.example.birthday_app.databinding.FragmentLoginFragmentBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class login_fragment : Fragment() {
    private lateinit var binding: FragmentLoginFragmentBinding
    // TODO: Rename and change types of parameters
    private lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_login_fragment, container, false)
        (requireActivity() as AppCompatActivity).supportActionBar!!.hide()

    }

    override fun onStart() {
        binding = FragmentLoginFragmentBinding.bind(requireView())
        super.onStart()
        auth = Firebase.auth
        val currrntuser = auth.currentUser
        if(currrntuser == null)
        {
            binding.submit.setOnClickListener {
                val email = binding.email.text.toString()
                val password = binding.password.text.toString()
                if (email  == "" || password == "" ) {
                        Toast.makeText(this.context, "enter password", Toast.LENGTH_SHORT).show()
                }
                else{

                    auth = Firebase.auth
                    auth.signInWithEmailAndPassword(email,password).addOnCompleteListener {task->
                        Toast.makeText(this.context, "$task", Toast.LENGTH_SHORT).show()
                        if(task.isSuccessful){

                            findNavController().navigate(R.id.action_login_fragment_to_mainfragment)

                                Toast.makeText(this.context, "Login successful", Toast.LENGTH_SHORT).show()

                        }
                        else{
                            Toast.makeText(this.context, "${task.exception}", Toast.LENGTH_SHORT).show()
                        }

                    }
                }

            }

        }
        else{
            findNavController().navigate(R.id.action_login_fragment_to_mainfragment)

        }

        binding.signup.setOnClickListener {
            findNavController().navigate(R.id.action_login_fragment_to_signupfragment)
        }
    }

}