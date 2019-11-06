//환자, 침대 정보관련
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

// 정상작동
exports.bedInfo = function(req, res){
    var date = moment().format('YYYY-MM-DD HH:mm:ss');
    var patientSeq = req.query.patientSeq;
    client.query('select roomCode from patient where patientSeq = ' + [patientSeq], function(error, results, fields){
        var roomCode = results[0].roomCode;
        client.query('select * from bed where roomCode = ?', roomCode, function(err, result, field){
            if(err){
                console.log(err);
                res.status(400);
            }
            else {
                console.log('bedinfo get time : ', date);
                res.send(result);
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
}

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
}