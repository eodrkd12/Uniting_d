var express=require('express');
var router=express.Router();

var db_university=require('../public/SQL/university_sql')();

router.get('/',function(req,res,next){
    db_university.get_university(req.body.name,function(err,result){
        if(err) console.log(err);
        else res.send(result);
    })
})

module.exports=router;