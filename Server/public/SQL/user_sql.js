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
	    get_dating: function(nickname,gender,univ_name,callback){
		    pool.getConnection(function(err,con){
			    var sql=`select * from dating_on where user_gender <> '${gender}' AND  user_nickname <> '${nickname}' AND univ_name LIKE '${univ_name}' AND joined='false'`
			    con.query(sql,function(err,result,fields){
				    con.release();
				    if(err) callback(err,result);
				    else callback(null,result);
			    })
		    })
	    }    
	    ,
        join: function(id,pw,name,birthday,gender,nickname,webMail,universityName,enterYear,departmentName,profileImage){
            pool.getConnection(function(err,con){
                var sql=`insert into user values('${id}','${pw}','${name}',${birthday},'${gender}','${nickname}','${webMail}','${universityName}',${enterYear},'${departmentName}','${profileImage}')`
                con.query(sql, function(err,result,fields){
                    con.release();
                    if(err) console.log(err);
                    else console.log("회원가입 완료");
                })
            })
        }
	    ,
	    insert_dating: function(id,nickname,universityName,departmentName,birthday,gender){
		    pool.getConnection(function(err,con){
			    var sql=`insert into dating_on values('${id}','${nickname}','${universityName}','${departmentName}',${birthday},'${gender}','false')`
			    con.query(sql, function(err,result,fields){
				    con.release();
				    if(err) console.log(err)
				    else console.log("데이팅 등록 완료")
			    })
		    })
	    }
        ,
        check: function(id,callback){
            pool.getConnection(function(err,con){
                var sql=`select user_id from user where user_id='${id}'`
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
                var sql=`select user_nickname from user where user_nickname='${nickname}'`
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
                var sql=`update user set user_pw='${pw}' where user_nickname='${nickname}' `;
                con.query(sql,function(err,result,field){
                    con.release();
                    if(err) console.log(err);
                    else console.log('회원정보 수정');
                })
            })
        },

        delete_user: function(id){ ////// 상원 : 회원삭제 (최신화)
            pool.getConnection(function(err,con){
                var sql=`delete from user where user_id='${id}`;
                con.query(sql,function(err,result,field){
                    con.release();
                    if(err) console.log(err);
                    else console.log('회원 탈퇴')
                })
            })
        },


        login: function(id,callback){
            pool.getConnection(function(err,con){
                var sql=`select * from user where user_id='${id}'`
                con.query(sql, function(err,result,fields){
                    con.release();
                    if(err) console.log(err);
                    else callback(null,result);
                })
            })
        },
	get_image:function(nickname,callback){
		pool.getConnection(function(err,con){
			var sql=`select user_image from user where user_id='${nickname}'`
			con.query(sql,function(err,result,fields){
				con.release()
				if(err) console.log(err)
				else callback(null,result)
			})
		})
	},
        pool: pool
    }
};
