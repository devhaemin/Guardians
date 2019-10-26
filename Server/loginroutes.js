var mysql = require('mysql');
var jwt = require('jsonwebtoken');
var tokenkey = "test_tokenkey";
var moment = require('moment');
require('moment-timezone');
moment.tz.setDefault("Asia/Seoul");
var client = mysql.createConnection({
	host : 'localhost',
	user : 'ict',
	password : 'rkeldjswm',
	database : 'ictDB'
});
exports.sendpatientinfo = function(req, res){
	var date = moment().format('YYYY-MM-DD HH:mm:ss');
	client.query('select name, age, roomCode, warningRate from patient order by warningRate DESC', function(error, results, rields){
		if(error){
			console.log('sendinfo error time : ', date);
			res.send({
				"code":400
			})
		}else{
			console.log("sendinfo time : ", date);
			res.send(results);
		}
	})
}
exports.checkemail = function(req, res){
	var email = req.body.email;
	var date = moment().format('YYYY-MM-DD HH:mm:ss');
	client.query('select * from user where email = ?', [email],
		function(error, results, fields){
			if(results.length>0){
				console.log('email already exists, time : ', date, 'email : ', email);
				res.send({
					"code":400,
					"failed":"email already exists"
				})
			}else{
				console.log('you can use this email, time: ', date, 'email : ', email);
				res.send({
					"code":200,
					"sucessful":"you can use this email"
				})
			}
		})
}
exports.Tokenlogin = function(req, res){
	var token = req.query.token;
	var date = moment().format('YYYY-MM-DD HH:mm:ss');
	client.query('select * from user where loginToken = ?',[token], function(error, result){
		if(result.length>0){
			console.log('Token login sucess time : ', date);
			res.send({
				"code":200,
				"sucess":"tokenloginsucess"
			})
		}
		else{
			console.log('Token login fail time : ', date);
			res.send({
				"code":400,
				"fail":"tokenloginfail"
			})
		}
	})
}

exports.register = function(req, res){
	var date = moment().format('YYYY-MM-DD HH:mm:ss');
	var users = {
		"email":req.body.email,
		"userName":req.body.userName,
		"pw":req.body.pw
	}
	client.query('insert into user set ?', users, function(error, results, fields){
		if(error){
			console.log('register failed time : ', date);
			res.send({
				"code" : 400,
				"failed": "error ocurred"
			})
		}
		else{
			console.log('registered sucessfully time : ', date);
			res.send({
				"code" : 200,
				"success":"user registered successfully"
			});
		}
	});
	client.end();
};
exports.login = function(req, res){
	var email = req.query.email;
	var password = req.query.pw;
	var date = moment().format('YYYY-MM-DD HH:mm:ss');
	client.query('select pw from user where email = ?', [email],
		function(error, results, fields){
			if(error){
				console.log('login failed, time : ', date, 'error ; ', error);
				res.send({
					"code":400,
					"fail":"loginfail"
				})
			}else{
				if(results.length > 0){
						if(results[0].pw == password){
							console.log('login success, time : ', date, 'email : ', email);
							var token = jwt.sign(email, tokenkey,{
								algorithm : 'HS256'
							});
							client.query('update user set loginToken=? where email=?',[token,email], function(request, result){
							})
							console.log('token : ', token);
							res.send({
								"code":200,
								"success":"user logined successfully",
								"token":token
							})
						}
						else{
							console.log('pw was not matched, time : ', date);
							res.send({
								"code":400,
								"fail":"failed to login, pw was not matched",
							})
						}
				}else{
					console.log('id was not matched, time ; ', date);
					res.send({
						"code":400,
						"fail":"failed to login, id was not matched",
					})
				}
			}
		})
}
