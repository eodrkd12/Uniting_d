var express=require('express');
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
    db_post.create_join_room(req.category,req.number)
    res.send('success_chat')
})

module.exports=router;