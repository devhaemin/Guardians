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
	database : 'ictDB',
	port : '3306'
});

exports.sendpatientinfo = function(req, res){
	var date = moment().format('YYYY-MM-DD HH:mm:ss');
	var order = req.query.ordered;
	if(order) {
		client.query('select name, age, roomCode, warningRate from patient order by ?', order, function (error, results, fields) {
			if (error) {
				console.log('sendinfo error time : ', date);
				console.log(error);
				res.status(400).end();
			} else {
				console.log("sendinfo time : ", date);
				res.status(200).end();
				res.send(results);
			}
		})
	}else {
		client.query('select name, age, roomCode, warningRate from patient order by warningRate DESC', function (error, results, fields) {
			if (error) {
				console.log('sendinfo error time : ', date);
				console.log(error);
				res.send({
					"code": 400
				})
			} else {
				console.log("sendinfo time : ", date);
				res.send(results);
			}
		})
	}
}
exports.bedinfo = function(req, res){
	var date = moment().format('YYYY-MM-DD HH:mm:ss');
	var patientSeq = req.query.patientSeq;
	client.query('select roomCode from patient where patientSeq = ?', patientSeq, function(error, results, fields){
		var roomCode = results[0].roomCode;
		client.query('select * from bed where roomCode = ?', roomCode, function(err, result, field){
			if(err){
				console.log(err);
				res.send();
			}
			else {
				console.log('bedinfo get time : ', date);
				res.send(result[0]);
			}
		})
		client.end();
	})
}
exports.autosearch = function(req, res){
	var string = req.query.string;
	var date = moment().format('YYYY-MM-DD HH:mm:ss');
	client.query("select patientName, roomCode from patient where patientName like ?", '%' + string + '%', function(err, result, fields){
		if(err){
			console.log('autosearch err time : ', date);
			res.send();
		}
		else{
			console.log('autosearch time : ', date);
			res.send(result);
		}
	})
}
exports.editbedinfo = function(req, res){
	var date = moment().format('YYYY-MM-DD HH:mm:ss');
	var bedCode = req.query.bedCode;
	var bedX = req.query.bedX;
	var bedY = req.query.bedY;
	client.query('update bed set bedX = ?, bedY = ? where bedCode = ?', [bedX, bedY, bedCode], function(err, result, fields){
		if(err){
			console.log(err);
			res.status(400);
		}
		else{
			console.log('editbedinfo time ; ', date);
			res.status(200);
			res.send();
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
				res.status(400).end();
			}else{
				console.log('you can use this email, time: ', date, 'email : ', email);
				res.status(200).end();
			}
		})
}
exports.Tokenlogin = function(req, res){
	var token = req.query.token;
	var date = moment().format('YYYY-MM-DD HH:mm:ss');
	client.query('select * from user where loginToken = ?',[token], function(error, result){
		if(result.length){
			console.log('Token login sucess time : ', date);
			res.status(200);
			res.send(result[0]);
		}
		else{
			console.log('Token login fail time : ', date);
			res.status(400).send('토큰이 만료되었습니다. 다시 로그인해주세요.').end();

		}
	})
	client.end();
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
			res.status(400).end();
		}
		else{
			console.log('registered sucessfully time : ', date);
			res.status(200).end();
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
				res.status(400).end();
			}else{
				if(results[0].length > 0){
						if(results[0].pw == password){
							console.log('login success, time : ', date, 'email : ', email);
							var token = jwt.sign(email, tokenkey,{
								algorithm : 'HS256'
							});
							client.query('update user set loginToken=? where email=?',[token,email], function(request, result){
							})
							client.query('select * from user where email=?', [token, email], function(request, result){
									res.status(200).end();
									res.send(result[0]);
							})
						}
						else{
							console.log('pw was not matched, time : ', date);
							res.status(400).end();
						}
				}
				else{
					console.log('id was not matched, time ; ', date);
					res.status(400).end();
				}
			}
		})
}
