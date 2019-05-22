package com.ydhnwb.arrangeit.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.ydhnwb.arrangeit.LecturerActivity
import com.ydhnwb.arrangeit.R
import com.ydhnwb.arrangeit.RoomActivity
import com.ydhnwb.arrangeit.StudentActivity
import com.ydhnwb.arrangeit.models.CarouselModel
import kotlinx.android.synthetic.main.list_item_overview.view.*

class CarouselAdapter(private var mList : List<CarouselModel>, private var context : Context) : RecyclerView.Adapter<CarouselAdapter.ViewHolder>()  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_overview, parent, false))

    override fun getItemCount() = mList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding(mList[position], context)
    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        fun binding(model : CarouselModel, context: Context){
            itemView.tv_title.text = model.title
            itemView.setOnClickListener {
                if(model.target == 0){
                    context.startActivity(Intent(context, StudentActivity::class.java))
                }else if (model.target == 1){
                    context.startActivity(Intent(context, LecturerActivity::class.java))
                }else if(model.target == 2){
                    context.startActivity(Intent(context, RoomActivity::class.java))
                }else{
                    Toast.makeText(context, "Others", Toast.LENGTH_SHORT).show() ;
                }
            }
        }
    }
}