var pool = require('../../config/db_config');

module.exports = function () {
    return {
        get_user: function (callback) {
            pool.getConnection(function (err, con) {
                var sql=`select * from user`;
                con.query(sql,function(err,result,fields){
                    con.release();
                    if(err) return;
                    else callback(null,result);
                })
            });
        },
        join: function(id,pw,name,birthday,gender,nickname,email,verified,university,grade,department,profile_image){
            pool.getConnection(function(err,con){
                var sql=`insert into user values('${id}','${pw}','${name}',${birthday},'${gender}','${nickname}','${email}','${verified}','${university}',${grade},'${department}',${profile_image})`
                con.query(sql, function(err,result,fields){
                    con.release();
                    if(err) console.log(err);
                    else console.log("회원가입 완료");
                })
            })
        }
        ,
        check: function(id,callback){
            pool.getConnection(function(err,con){
                var sql=`select ID from user where id='${id}'`
                con.query(sql, function(err,result,fields){
                    con.release();
                    if(err) callback(err)
                    else callback(null,result)
                })
            })
        }
        ,
        check_nickname: function(nickname,callback){
            pool.getConnection(function(err,con){
                var sql=`select nickname from user where nickname='${nickname}'`
                con.query(sql,function(err,result,fields){
                    con.release();
                    if(err) callback(err)
                    else callback(null,result)
                })
            })
        }
        ,
        update_user: function(pw,nickname){ /////////// 상원 : 회원정보수정 (최신화)
            pool.getConnection(function(err,con){
                var sql=`update user set pw='${pw} where nickname='${nickname}' `;
                con.query(sql,function(err,result,field){
                    con.release();
                    if(err) console.log(err);
                    else console.log('회원정보 수정');
                })
            })
        },

        delete_user: function(id){ ////// 상원 : 회원삭제 (최신화)
            pool.getConnection(function(err,con){
                var sql=`delete from user where id='${id}`;
                con.query(sql,function(err,result,field){
                    con.release();
                    if(err) console.log(err);
                    else console.log('회원 탈퇴')
                })
            })
        },

        login: function(id,callback){
            pool.getConnection(function(err,con){
                var sql=`select * from user where ID='${id}'`
                con.query(sql, function(err,result,fields){
                    con.release();
                    if(err) console.log(err);
                    else callback(null,result);
                })
            })
        }
        ,
        pool: pool
    }
};