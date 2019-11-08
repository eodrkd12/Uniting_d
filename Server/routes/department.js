var express=require('express');
var router=express.Router();

var db_department=require('../public/SQL/department_sql')();

router.get('/',function(req,res,next){
    db_department.get_department(req.body.university_name,req.body.department_name,function(err,result){
        if(err) console.log(err);
        else res.send(result);
    })
})

module.exports=router;