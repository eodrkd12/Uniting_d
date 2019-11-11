var pool=require('../../config/db_config');

module.exports=function(){
    return {
        get_university:function(name,callback){
            pool.getConnection(function(err,con){
                var sql=`select * from university where name='%${name}%'`
                con.query(sql,function(err,result){
                    if(err) return;
                    callback(null,result);
                })
            })
        },  
        pool:pool
    }
}