package com.example.nyumbakumiapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.firebase.database.FirebaseDatabase

class CustomAdapter(var context: Context, var data:ArrayList<House>):BaseAdapter() {
    private class ViewHolder(row:View?){
        var mTxtHouseNumber:TextView
        var mTxtHouseSize:TextView
        var mTxtHousePrice:TextView
        var imgHousepic:ImageView
        var btnDelete:Button
        var btnUpdate:Button
        init {
            this.mTxtHouseNumber = row?.findViewById(R.id.mTvHouseNumber) as TextView
            this.mTxtHouseSize = row?.findViewById(R.id.mTvHouseSize) as TextView
            this.mTxtHousePrice= row?.findViewById(R.id.mTvHousePrice) as TextView
            this.imgHousepic= row?.findViewById(R.id.imgHousePic) as ImageView
            this.btnDelete= row?.findViewById(R.id.mbtnDelete) as Button
            this.btnUpdate= row?.findViewById(R.id.mbtnUpdate) as Button

        }
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view:View?
        var viewHolder:ViewHolder
        if (convertView == null){
            var layout = LayoutInflater.from(context)
            view = layout.inflate(R.layout.house_layout,parent,false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        }else{
            view = convertView
            viewHolder = view.tag as ViewHolder
        }
        var item:House = getItem(position) as House
        viewHolder.mTxtHouseNumber.text = item.houseNumber
        viewHolder.mTxtHouseSize.text = item.houseSize
        viewHolder.mTxtHousePrice.text = item.housePrice
        Glide.with(context).load(item.houseimage).into(viewHolder.imgHousepic)

        viewHolder.btnDelete.setOnClickListener {
            var delRef = FirebaseDatabase.getInstance()
                .getReference().child("House/"+item.houseid)
            delRef.removeValue()

        }
        viewHolder.btnUpdate.setOnClickListener {


        }
        return view as View
    }

    override fun getItem(position: Int): Any {
        return  data.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return data.count()
    }
}