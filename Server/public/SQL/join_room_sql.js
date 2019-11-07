var pool=require('../../config/db_config');

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
////////////////////////////////////////////////////////////////

        create_join_room: function(category, number){
            pool.getConnection(function(err,con){
                var sql=`insert into join_room(category,room_num) value('${category}',${room_num})`;
                con.query(sql,function(err,result,field){
                    con.release();
                    if(err) console.log(err);
                    else console.log('채팅방 생성');
                })
            })
        },

        pool:pool
    }
}