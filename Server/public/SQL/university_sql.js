var pool=require('../../config/db_config');

module.exports=function(){
    return {
        get_university:function(name,callback){
            pool.getConnection(function(err,con){
                var sql=`select * from university where univ_name like '%${name}%'`
                con.query(sql,function(err,result){
                    if(err) callback(err,result);
                    callback(null,result);
                })
            })
        },  
        pool:pool
    }
}
