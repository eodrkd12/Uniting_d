var express=require('express');
var moment=require('moment');
require('moment-timezone');
moment.tz.setDefault("Asia/Seoul");

var router=express.Router();

var db_join_room=require('../public/SQL/join_room_sql')();

router.get('/date',function(req,res,next){
    db_join_room.get_join_room_date(function(err,result){
        if(err) console.log(err);
        else {
            console.log("데이팅 조회 성공");
            res.send(result);
        }
    })
})

router.get('/study',function(req,res,next){
    db_join_room.get_join_room_study(function(err,result){
        if(err) console.log(err);
        else {
            console.log("스터디 조회 성공");
            res.send(result);
        }
    })
})

router.get('/market',function(req,res,next){
    db_join_room.get_join_room_study(function(err,result){
        if(err) console.log(err);
        else {
            console.log("장터 조회 성공");
            res.send(result);
        }
    })
})
///////////////////////////////////////// 
router.post('/',function(req,res,next){//채팅방 생성
	var cate_name=req.body.cate_name
	var maker=req.body.maker
	var user=req.body.user
	var universityName=req.body.univ_name
        var time=moment().format('YYYY-MM-DD HH:mm:ss')
        var room_id=`${universityName}-${maker}-${time}`
	var room_title=`${maker}&${user}`
	
    db_join_room.create_join_room(room_id,room_title,cate_name,maker,user,universityName,function(err,result){
	    if(err) console.log(err)
	    else{
		    db_join_room.insert_join_room(room_id,maker,function(err,result){
			    if(err) console.log(err)
			    else{
				    db_join_room.insert_join_room(room_id,user,function(err,result){
					    if(err) console.log(err)
					    else{
						    db_join_room.joined_true(maker,function(err,result){
							    if(err) console.log(err)
							    else{
								    db_join_room.joined_true(user,function(err,result){
									    if(err) console.log(err)
									    else{
										    var object=new Object()
										    object.room_id=room_id
										    res.send(object)
									    }
								    })
							    }
						    })
					    }
				    })
			    }
		    })
	    }
    })
})

router.post('/chat_room',function(req,res,next){
	var nickname=req.body[0].nickname
	db_join_room.get_chat_room(nickname,function(err,result){
		if(err) console.log(err)
		else res.send(result)
	})
})

router.post('/exit',function(req,res,next){
	var roomId=req.body.room_id
	var nickname=req.body.nickname
	
	db_join_room.exit_room(nickname,roomId,function(err,result){
		if(err) console.log(err)
		else{
			db_join_room.sub_cur(roomId,function(err,result){
				if(err) console.log(err)
				else{
					db_join_room.get_cur(roomId,function(err,result){
						if(err) console.log(err)
						else{
							console.log(result[0].cur_num)
							if(result[0].cur_num<1){
								db_join_room.del_room(roomId,function(err,result){
									if(err) console.log(err)
									else{
										var object=new Object()
										object.result='delete_room'
										res.send(object)
									}
								})
							}
							else{
								var object=new Object()
								object.result='exit'
								res.send(object)
							}
						}
					})
				}
			})
		}		
	})
})

router.post('/exit/dating',function(req,res,next){
	var nickname=req.body.nickname
	db_join_room.joined_false(nickname,function(err,result){
		if(err) console.log(err)
		else{
			var object=new Object()
			object.result='ok'
			res.send(object)
		}
	})
})

			
module.exports=router;
