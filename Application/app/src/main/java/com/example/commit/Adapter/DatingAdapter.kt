package com.example.commit.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.commit.ListItem.DatingItem
import com.example.commit.R

class DatingAdapter : BaseAdapter() {

    private var datingList = ArrayList<DatingItem>()

    override fun getCount(): Int {
        return datingList.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItem(position: Int): Any {
        return datingList.get(position)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        val context:Context? = parent?.context

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (view == null) {
            val inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.item_dating_list, parent, false)
        }

        var textNickname=view?.findViewById(R.id.text_nickname) as TextView
        var textDepartment=view?.findViewById(R.id.text_department) as TextView
        var textAge=view?.findViewById(R.id.text_age) as TextView

        var item=datingList[position]

        textNickname.setText(item.nickname)
        textDepartment.setText(item.department)
        textAge.setText(item.age)

        return view
    }

    fun addItem(nickname: String, department: String, age:Int){
        val item=DatingItem()

        item.nickname=nickname
        item.department=department
        item.age=age.toString()


        datingList.add(item)
    }

    fun clear(){
        datingList.clear()
    }
}