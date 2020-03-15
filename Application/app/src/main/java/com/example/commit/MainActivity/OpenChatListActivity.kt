package com.example.commit.MainActivity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.LinearLayout

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.commit.Adapter.CategoryAdapter
import com.example.commit.Adapter.ChatRoomListAdapter
import com.example.commit.Adapter.MyChatListAdapter
import com.example.commit.Class.UserInfo
import com.example.commit.R
import com.example.commit.Singleton.VolleyService
import kotlinx.android.synthetic.main.activity_open_chat_list.*
import org.json.JSONObject
import com.example.commit.ListItem.ChatRoomListItem
import org.json.JSONArray


class OpenChatListActivity : AppCompatActivity() {

    init {
        INSTANCE = this
    }

    companion object {
        var CATEGORY: String = "전체"
        var INSTANCE: OpenChatListActivity? = null
        var HANDLER: Handler? = null
        var OPENCHATRV: RecyclerView? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_open_chat_list)
        OPENCHATRV = findViewById(R.id.rv_open)

        btn_make.setOnClickListener(View.OnClickListener() {
            var intent = Intent(this, MakeRoomActivity::class.java)
            startActivity(intent)
        }
        )
      /*  btn_search.setOnClickListener(View.OnClickListener {
            var intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)

        })*/

        rv_category.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv_category.adapter = CategoryAdapter(this)
        rv_category.setHasFixedSize(true)

        var chatRoomAdapter = ChatRoomListAdapter(this)
        rv_open.adapter=chatRoomAdapter

        var layoutManager=LinearLayoutManager(this)
        rv_open.layoutManager=layoutManager
        rv_open.setHasFixedSize(true)

        VolleyService.openChatRoomListReq(UserInfo.UNIV, CATEGORY, this, { success ->
            chatRoomAdapter.clear()

            var chatRoomArray = success
            if (chatRoomArray!!.length() == 0) {

            } else {
                for (i in 0..chatRoomArray.length() - 1) {
                    var json = chatRoomArray[i] as JSONObject
                    var roomId = json.getString("room_id")
                    var category = json.getString("cate_name")
                    var maker = json.getString("maker")
                    var roomTitle = json.getString("room_title")
                    var limitNum = json.getInt("limit_num")
                    var universityName = json.getString("univ_name")
                    var curNum = json.getInt("cur_num")
                    var introduce = json.getString("introduce")

                    chatRoomAdapter.addItem(
                        roomId,
                        category,
                        maker,
                        roomTitle,
                        limitNum,
                        universityName,
                        curNum,
                        introduce
                    )
                }
                chatRoomAdapter.notifyDataSetChanged()

            }

            /*listChatRoom.setOnItemClickListener { parent, view, position, id ->

                var roomId = chatRoomAdapter.getRoomId(position)
                var category = chatRoomAdapter.getCategory(position)
                var introduce=chatRoomAdapter.getIntroduce(position)

                VolleyService.checkJoinReq(roomId, UserInfo.NICKNAME, this, { success ->
                    if (success == "true") {
                        val builder =
                            AlertDialog.Builder(ContextThemeWrapper(this, R.style.Theme_AppCompat_Light_Dialog))
                        builder.setTitle("참여 중인 방입니다.")

                        builder.setPositiveButton("입장") { _, _ ->
                            var intent = Intent(this, ChatActivity::class.java)
                            intent.putExtra("room_id", roomId)
                            intent.putExtra("category", category)
                            startActivity(intent)
                        }
                        builder.setNegativeButton("취소") { _, _ ->

                        }
                        builder.show()
                    } else {
                        var builder=AlertDialog.Builder(ContextThemeWrapper(this, R.style.Theme_AppCompat_Light_Dialog))
                        builder.setTitle("방에 입장하시겠습니까?")
                        builder.setMessage(introduce)
                        builder.setPositiveButton("입장"){_,_ ->
                            VolleyService.joinChatRoomReq(roomId, UserInfo.NICKNAME, this, { success ->
                                if (success == 1) {
                                    var isFull = chatRoomAdapter.isFull(position)

                                    if (isFull) {
                                        val builder =
                                            AlertDialog.Builder(ContextThemeWrapper(this, R.style.Theme_AppCompat_Light_Dialog))
                                        builder.setTitle("입장할 수 없습니다.")
                                        builder.setPositiveButton("확인") { _, _ ->

                                        }
                                        builder.show()
                                    }

                                    var intent = Intent(this, ChatActivity::class.java)
                                    intent.putExtra("room_id", roomId)
                                    intent.putExtra("category", category)
                                    startActivity(intent)
                                }
                            })
                        }
                        builder.setNegativeButton("취소"){_,_ ->

                        }
                        builder.show()
                    }
                })
            }

            chatRoomAdapter.notifyDataSetChanged()
        })*/
            HANDLER = object : Handler() {
                override fun handleMessage(msg: Message?) {
                    Log.d("test", "카테고리 메시지 도착 : ${msg!!.what}")
                    when (msg!!.what) {
                        0 -> {
                            //카테고리 방 갱신
                            VolleyService.openChatRoomListReq(
                                UserInfo.UNIV,
                                CATEGORY,
                                INSTANCE!!,
                                { success ->
                                    rv_open.adapter = chatRoomAdapter
                                    chatRoomAdapter.clear()

                                    var chatRoomArray = success
                                    if (chatRoomArray!!.length() == 0) {

                                    } else {
                                        for (i in 0..chatRoomArray.length() - 1) {
                                            var json = chatRoomArray[i] as JSONObject
                                            var roomId = json.getString("room_id")
                                            var category = json.getString("cate_name")
                                            var maker = json.getString("maker")
                                            var roomTitle = json.getString("room_title")
                                            var limitNum = json.getInt("limit_num")
                                            var universityName = json.getString("univ_name")
                                            var curNum = json.getInt("cur_num")
                                            var introduce = json.getString("introduce")
                                            chatRoomAdapter.addItem(
                                                roomId,
                                                category,
                                                maker,
                                                roomTitle,
                                                limitNum,
                                                universityName,
                                                curNum,
                                                introduce
                                            )
                                        }
                                    }
                                })

                            chatRoomAdapter.notifyDataSetChanged()
                        }
                    }
                }
            }
        })

        var openchatArray: JSONArray? = null
        var openchatList: ArrayList<ChatRoomListItem> = arrayListOf()
        var openchatFilter: ArrayList<ChatRoomListItem> = arrayListOf()

        VolleyService.getSearchReq(UserInfo.UNIV,this, {success ->
            openchatArray = success

            var chatRoomArray = success
            if (chatRoomArray!!.length() == 0) {

            } else {
                for (i in 0..chatRoomArray.length() - 1) {
                    var json = chatRoomArray[i] as JSONObject
                    var roomId = json.getString("room_id")
                    var category = json.getString("cate_name")
                    var maker = json.getString("maker")
                    var roomTitle = json.getString("room_title")
                    var limitNum = json.getInt("limit_num")
                    var universityName = json.getString("univ_name")
                    var curNum = json.getInt("cur_num")
                    var introduce = json.getString("introduce")

                   var item:ChatRoomListItem = ChatRoomListItem()
                    item.roomId=roomId
                    item.cateName=category
                    item.maker=maker
                    item.roomTitle=roomTitle
                    item.limitNum=limitNum
                    item.universityName=universityName
                    item.curNum=curNum
                    item.introduce=introduce

                   openchatList.add(item)
                }

            }
        })
        text_search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(openchatList.size == 0)
                {

                }
                else{
                    openchatFilter.clear()
                    for(i in 0..openchatList.size-1) {
                        if((openchatList.get(i).roomTitle!!.contains(text_search.text) == true) && text_search.text.toString() != "") {
                            var item:ChatRoomListItem = ChatRoomListItem()
                            item.roomId=openchatList.get(i).roomId
                            item.cateName=openchatList.get(i).cateName
                            item.maker=openchatList.get(i).maker
                            item.roomTitle=openchatList.get(i).roomTitle
                            item.limitNum=openchatList.get(i).limitNum
                            item.universityName=openchatList.get(i).universityName
                            item.curNum=openchatList.get(i).curNum
                            item.introduce=openchatList.get(i).introduce

                            openchatFilter.add(item)
                        }
                    }
                    OPENCHATRV!!.setHasFixedSize(true)
                    OPENCHATRV!!.layoutManager = LinearLayoutManager(this@OpenChatListActivity, LinearLayout.VERTICAL, false)
                   // OPENCHATRV!!.adapter = MyChatListAdapter(this@OpenChatListActivity, openchatFilter)
                }
            }
        })



    }
}
