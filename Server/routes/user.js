var express = require('express');
var router = express.Router();

var db_user = require('../public/SQL/user_sql')();

router.get('/', function(req, res, next) {
  db_user.get_user(function(err,result){
    if(err) console.log(err);
    else res.send(result);
  })
});

router.post('/dating', function(req, res, next) {
	console.log(req.body[0].nickname)
  db_user.get_dating(req.body[0].nickname,req.body[0].gender,req.body[0].univ_name,function(err,result){
    if(err) console.log(err);
    else res.send(result);
  })
});

router.post('/login', function(req,res,next){
  db_user.login(req.body.id,function(err,result){
    if(err) console.log(err);
    else {
      var data=result[0]
      res.send(data);
    }
  })
})

router.post('/check', function(req,res,next){
  db_user.check(req.body.id,function(err,result){
    if(err) console.log(err)
    else 
      res.send(result[0])
  })
})
router.post('/check/nickname', function(req,res,next){
  db_user.check_nickname(req.body.nickname,function(err,result){
    if(err) console.log(err)
    else 
      res.send(result[0])
  })
})

router.post('/', function(req,res,next){
	var id=req.body.id
	var pw=req.body.pw
	var name=req.body.name
	var birthday=req.body.birthday
	var gender=req.body.gender
	var nickname=req.body.nickname
	var webMail=req.body.web_mail
	var universityName=req.body.university_name
	var departmentName=req.body.department_name
	var enterYear=req.body.enter_year

	db_user.join(id,pw,name,birthday,gender,nickname,webMail,universityName,enterYear,departmentName,null)
	db_user.insert_dating(id,nickname,universityName,departmentName,birthday,gender)

	var json=new Object()
	json.result="success"
	res.send(json)
})

router.put('/',function(req,res,next){ // 상원 회원정보수정 (최신화)
  db_user.update_user(req.body.pw,req.body.nickname)
  res.send('success');
})

router.delete('/',function(req,res,next){ // 상원 회원정보 삭제 (최신화)
  db_user.delete_user(req.body.id)
  res.send('success');
})

module.exports = router;
