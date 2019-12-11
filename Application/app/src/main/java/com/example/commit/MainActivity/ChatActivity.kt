package com.example.commit.MainActivity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.commit.Adapter.ChatAdapter
import com.example.commit.Class.UserInfo
import com.example.commit.R
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_chat.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.HashMap

class ChatActivity : AppCompatActivity() {

    var chatAdapter=ChatAdapter()
    var roomId:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        list_chat.adapter=chatAdapter

        var reference= FirebaseDatabase.getInstance().reference.child("message")

        val childEventListener=object : ChildEventListener{
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
            }

            override fun onChildRemoved(p0: DataSnapshot) {
            }

        }



        btn_send.setOnClickListener {
            var map= mapOf<String,Any>()

            var key:String?=reference.push().key
            reference.updateChildren(map)

            var root=reference.child(key!!)

            var objectMap= mutableMapOf<String,Any>()

            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("aa hh:mm")
            val formatted = current.format(formatter)

            objectMap.put("room_id",roomId!!)
            objectMap.put("speaker",UserInfo.NICKNAME)
            objectMap.put("content",edit_chat.text.toString())
            objectMap.put("time",formatted)

            root.updateChildren(objectMap)
            edit_chat.text.clear()
        }
    }

    fun chatConversation(dataSnapshot: DataSnapshot){
        var i=dataSnapshot.children.iterator()

        while(i.hasNext()){
            var roomId=((i.next() as DataSnapshot).getValue()) as String
            var speaker=((i.next() as DataSnapshot).getValue()) as String
            var content=((i.next() as DataSnapshot).getValue()) as String
            var time=((i.next() as DataSnapshot).getValue()) as String

            chatAdapter.addItem(roomId,speaker, content, time)
        }

        chatAdapter.notifyDataSetChanged()
    }
}