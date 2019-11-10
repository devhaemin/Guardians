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
}

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

exports.Tokenlogin = function(req, res){
	var date = moment().format('YYYY-MM-DD HH:mm:ss');
	var token = req.get('Access-Token');
	client.query('select * from user where accessToken = ?',[token], function(error, result){
		if(result.length){
			console.log('Token login sucess time : ', date);
			res.status(200);
			res.send(result[0]);
		}
		else{
			console.log(result);
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
	client.query('select * from user where email = ?', users.email, function(err, result, field) {
		if(result.length >0) {
			console.log('email alread exists, time : ', date, 'dmail : ', users.email);
			res.status(400);
		}else {
			client.query('insert into user set ?', users, function (error, results, fields) {
				if (error) {
					console.log('register failed time : ', date);
					res.status(400);
				} else {
					console.log('registered sucessfully time : ', date);
					res.status(200);
				}
			});
		}
	})
}

exports.login = function(req, res){
	var email = req.query.email;
	var password = req.query.pw;
	var firebasetoken = req.query.pushToken;
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
					client.query('update user set (loginToken=?, firebaseToken = ?) where email=?',[token, firebasetoken, email], function(error, result, field){
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
