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
	database : 'ictdb',
	port : '3306'
});
//오류없음
exports.sendAlarmList = function(req, res){
	var token = req.get('Access-Token');
	var date = moment().format('YYYY-MM-DD HH:mm:ss');
	client.query('select userSeq from user where accessToken = ?', token, function(error, results, fields) {
		var userSeq = results[0].userSeq;
		client.query('select * from Alarm where userSeq = ?', userSeq, function (err, result, field) {
			if (err) {
				console.log('sendAlarmList error time : ', date);
				res.send();
			} else {
				console.log('sendAlarmList time : ', date);
				res.send(result);
			}
		})
	})
}/*
//sendPatientInfo 오류없음
exports.sendPatientInfo = function(req, res){
	var date = moment().format('YYYY-MM-DD HH:mm:ss');
	var order = req.query.ordered;
	if(order) {
		console.log(order);
		client.query('select * from patient order by ?', [order], function (error, results, fields) {
			if (error) {
				console.log('sendinfo error time : ', date);
				console.log(error);
				res.status(400);
			} else {
				console.log("ordered sendinfo time : ", date);
				res.send(results);
				res.status(200);
			}
		})
	}else {
		client.query('select * from patient order by warningRate DESC', function (error, results, fields) {
			if (error) {
				console.log('sendinfo error time : ', date);
				res.status(400);
			} else {
				console.log("sendinfo time : ", date);
				res.send(results);
			}
		})
	}
}
//X  나중에 구현 아직 오류나는지 모름
exports.bedInfo = function(req, res){
	var date = moment().format('YYYY-MM-DD HH:mm:ss');
	var patientSeq = req.query.patientSeq;
	client.query('select roomCode from patient where patientSeq = ?', patientSeq, function(error, results, fields){
		var roomCode = results[0].roomCode;
		client.query('select * from bed where roomCode = ?', roomCode, function(err, result, field){
			if(err){
				console.log(err);
				res.status(400);
			}
			else {
				console.log('bedinfo get time : ', date);
				res.send(result[0]);
			}
		})
	})
}
//정상작동
exports.searchPatientInfo = function(req, res){
	var patientSeq = req.query.patientSeq;
	var date = moment().format('YYYY-MM-DD HH:mm:ss');
	client.query('select * from patient where patientSeq = ?', patientSeq, function(err, result, field){
		if(err){
			console.log('searchPatientInfo error time : ', date);
			res.status(400);
		}
		else{
			console.log('searchPatientinfo time : ', date);
			res.send(result[0]);
		}
	})
}*/
//포스트맨으로 오류 없음
exports.autosearch = function(req, res){
	var string = req.query.str;
	var date = moment().format('YYYY-MM-DD HH:mm:ss');
	client.query("select * from patient where patientName like ?", '%' + string + '%', function(err, result, fields){
		if(err){
			console.log('autosearch err time : ', date);
			res.status(400);
		}
		else{
			console.log('autosearch time : ', date);
			res.send(result);
		}
	})
}/*
//포스트맨 이상 무
exports.roomCodesearch = function(req, res){
	var roomCode = req.query.roomCode;
	var date = moment().format('YYYY-MM-DD HH:mm:ss');
	client.query("select patientName, roomCode from patient where roomCode = ?", roomCode, function(err, result, fields){
		if(err){
			console.log('roomCodeSearch fail time : ', date);
			console.log(err);
			res.status(400);
		}
		else{
			console.log('roomCodeSearch time : ', date);
			res.send(result);
		}
	})
}
//포스트맨 이상 무
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
}*/
//포스트맨 이상무
exports.checkemail = function(req, res) {
	var email = req.body.email;
	var date = moment().format('YYYY-MM-DD HH:mm:ss');
	client.query('select * from user where email = ?', [email],
		function (error, results, fields) {
			if (results.length > 0) {
				console.log('email already exists, time : ', date, 'email : ', email);
				res.status(400);
				res.send();
			} else {
				console.log('you can use this email, time: ', date, 'email : ', email);
				res.status(200);
				res.send();
			}
		})
}
//오류없음
exports.Tokenlogin = function(req, res){
	var token = req.get('Access-Token');
	var date = moment().format('YYYY-MM-DD HH:mm:ss');
	client.query('select * from user where accessToken = ?',[token], function(error, result){
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
}
//X
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
			res.status(400);
		}
		else{
			console.log('registered sucessfully time : ', date);
			res.status(200);
		}
	});
}
//오류없음
exports.login = function(req, res){
	var email = req.query.email;
	var password = req.query.pw;
	var date = moment().format('YYYY-MM-DD HH:mm:ss');
	client.query('select pw from user where email = ?', [email], function(error, results, fields){
		if(error){
			console.log('login failed, time : ', date, 'error ; ', error);
			res.status(400);
		}else{
			if(results[0].pw){
				if((results[0].pw) == (password)){
					console.log('login success, time : ', date, 'email : ', email);
					var token = jwt.sign(email, tokenkey,{
						algorithm : 'HS256'
					});
					client.query('update user set loginToken=? where email=?',[token,email], function(error, result, field){
					})
					client.query('select * from user where email = ?', email, function(error, result, field){
						res.send(result[0]);
						res.status(200);
					})
				}
				else{
					console.log('pw was not matched, time : ', date);
					res.status(400);
				}
			}
			else{
				console.log('id was not matched, time ; ', date);
				res.status(400);
			}
		}
	})
}