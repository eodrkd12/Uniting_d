var pool=require('../../config/db_config');

module.exports=function(){
    return {
        get_department:function(univ_name,dept_name,callback){
            pool.getConnection(function(err,con){
                var sql=`select * from department where univ_name like '%${univ_name}% AND dpet_name like '%${dept_name}%''`
                con.query(sql,function(err,result){
                    if(err) return;
                    callback(null,result);
                })
            })
        },  
        pool:pool
    }
}