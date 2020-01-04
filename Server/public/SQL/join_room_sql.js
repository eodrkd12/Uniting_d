var pool=require('../../config/db_config');
require('date-utils')


module.exports=function(){
    return {
        get_join_room_date: function(callback){
            pool.getConnection(function(err,con){
                var sql=`select * from join_room where category='date'`;
                con.query(sql,function(err,result,field){
                    con.release();
                    if(err)return;
                    callback(null,result);
                })
            })
        },

        get_join_room_study: function(callback){
            pool.getConnection(function(err,con){
                var sql=`select * from join_room where category='study'`;
                con.query(sql,function(err,result,field){
                    con.release();
                    if(err)return;
                    callback(null,result);
                })
            })
        },
        

        get_join_room_market: function(callback){
            pool.getConnection(function(err,con){
                var sql=`select * from join_room where category='market'`;
                con.query(sql,function(err,result,field){
                    con.release();
                    if(err)return;
                    callback(null,result);
                })
            })
        },


        create_join_room: function(room_id,room_title,cate_name,maker,user,universityName,callback){
            pool.getConnection(function(err,con){
                var sql=`insert into chat_room value('${room_id}','${cate_name}','${maker}','${room_title}',2,'${universityName}',2)`;
                con.query(sql,function(err,result,field){
                    con.release()
                    if(err) callback(err)
                    else callback(null,result)
                })
            })
        },
	insert_join_room: function(room_id,user_nickname,callback){
		pool.getConnection(function(err,con){
			var sql=`insert into join_room value('${room_id}','${user_nickname}')`
			con.query(sql,function(err,result,field){
				con.release()
				if(err) callback(err)
				else callback(null,result)
			})
		})
	},
	    joined_true: function(user_nickname,callback){
		    pool.getConnection(function(err,con){
		    var sql="update dating_on set joined='true' where user_nickname='"+user_nickname+"'"
			    con.query(sql,function(err,result,field){
				    con.release()
				    if(err) callback(err)
				    else callback(null,result)
			    })
		    })
	    },
	    joined_false: function(user_nickname,callback){
                    pool.getConnection(function(err,con){
                            var sql="update dating_on set joined='false' where user_nickname='"+user_nickname+"'"
                            con.query(sql,function(err,result,field){
                                    con.release()
                                    if(err) callback(err)
                                    else callback(null,result)
                            })
                    })
            },
	    get_chat_room: function(user_nickname,callback){
		    pool.getConnection(function(err,con){
			    var sql=`select chat_room.room_id, cate_name,maker,room_title,limit_num,univ_name,cur_num  from chat_room,join_room where chat_room.room_id=join_room.room_id and join_room.user_nickname='${user_nickname}'`
			    con.query(sql,function(err,result,field){
				    con.release()
				    if(err) callback(err)
				    else callback(null,result)
			    })
		    })
	    },
	    get_category: function(room_id,callback){
		    pool.getConnection(function(err,con){
			    var sql=`select cate_name from chat_room where room_id='${room_id}'`
			    con.query(sql,function(err,result,field){
				    con.release()
				    if(err) callback(err)
				    else callback(null,result)
			    })
		    })
	    },
	    exit_room: function(user_nickname,room_id,callback){
		    pool.getConnection(function(err,con){
			    var sql="delete from join_room where user_nickname='"+user_nickname+"' AND room_id='"+room_id+"'"
			    con.query(sql,function(err,result,field){
				    con.release()
				    if(err) callback(err)
				    else callback(null,result)
			    })
		    })
	    },
	    sub_cur:function(room_id,callback){
		    pool.getConnection(function(err,con){
			    var sql="update chat_room set cur_num=cur_num-1 where room_id='"+room_id+"'"
			    con.query(sql,function(err,result,field){
				    con.release()
				    if(err) callback(err)
				    else callback(null,result)
			    })
		    })
	    },
	    get_cur:function(room_id,callback){
		    pool.getConnection(function(err,con){
			    var sql="select cur_num from chat_room where room_id='"+room_id+"'"
			    con.query(sql,function(err,result,field){
				    con.release()
				    if(err) callback(err)
				    else callback(null,result)
			    })
		    })
	    },
	    del_room:function(room_id,callback){
		    pool.getConnection(function(err,con){
			    var sql="delete from chat_room where room_id='"+room_id+"'"
			    con.query(sql,function(err,result,field){
				    con.release()
				    if(err) callback(err)
				    else callback(null,result)
			    })
		    }) 
	    },
        pool:pool
    }
}
