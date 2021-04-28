package com.example.birthday_app.recyclerview

import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.example.birthday_app.R
import com.example.birthday_app.birth_viewmodel
import com.example.birthday_app.db.entity.birthdaye

class adapter(var list:List<birthdaye>, private val viewModel: birth_viewmodel ):RecyclerView.Adapter<adapter.birthdayviewholder>(){

    class birthdayviewholder(itemView: View):RecyclerView.ViewHolder(itemView){
        private val name = itemView.findViewById<TextView>(R.id.name)
        private val date = itemView.findViewById<TextView>(R.id.date)

        fun bind(a:birthdaye){
            name.text  = a.name
            date.text = a.Date.toString()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): birthdayviewholder {
val view = LayoutInflater.from(parent.context).inflate(R.layout.birth_day,parent,false)
        return birthdayviewholder(view)

    }

    override fun onBindViewHolder(holder: birthdayviewholder, position: Int) {

        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

}