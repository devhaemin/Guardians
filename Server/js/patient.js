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

exports.searchPatientInfo = function(req, res){
    var patientSeq = req.query.patientSeq;
    var date = moment().format('YYYY-MM-DD HH:mm:ss');
    client.query('select * from patient where patientSeq = ?', patientSeq, function(err, patientinfo, field){
        if(err){
            console.log('searchPatientInfo error time : ', date);
            res.status(400);
        }
        else{
            client.query('select * from bed where roomCode = ?', patientinfo[0].roomCode, function(error, bedinfo, fields){
                if(err){
                    res.status(400);
                    console.log('select bed error');
                }
                else{
                    patientinfo[0]["bedInfo"] = bedinfo;
                    var set = patientinfo[0];
                    res.send(set);
                }
            })
        }
    })
}

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

exports.editpatientinfo = function(req, res){
    var name = req.query.patientName;
    var age = req.query.age;
    var phone = req.query.phone;
    var roomCode = req.query.roomCode;
    var gender = req.query.gender;
    var paininfo = req.query.paininfo;
    client.query('update patient set patientName = ?, age = ?, phone = ?, roomCode = ?, gender = ?, paininfo = ?', [name, age, phone, roomCode, gender, paininfo], function(err, result, field){
        if(err){
            console.log('patientinfo update error');
            res.status(400);
        }
        else{
            console.log('edit success');
            res.status(200);
        }
    })
}

exports.editroominfo = function(req, res){
    var name = req.query.roomName;
    var capacity = req.query.capacity;
    var roomCode = req.query.roomCode;
    client.query('update room set roomName = ?, capacity = ? where roomCode = ?', [name, capacity, roomCode], function(err, result, field){
        if(err){
            console.log('roominfo update error');
            res.status(400);
        }
        else{
            console.log('edit success');
            res.status(200);
        }
    })
}

exports.edituserinfo = function(req, res){
    var name = req.query.userName;
    var phone = req.query.phone;
    var Type = req.query.userType;
    var roomCode = req.query.roomCode;
    var Seq = req.query.userSeq;
    client.query('update user set userName = ?, phone = ?, userType = ?, roomCode = ? where userSeq = ?', [name, phone, Type, roomCode, Seq], function(err, result, field){
        if(err){
            console.log('userinfo update error');
            res.status(400);
        }
        else{
            console.log('edit success');
            res.status(200);
        }
    })
}

exports.addbedinfo = function(req, res){
    var roomCode = req.query.roomCode;
    var bedX = req.query.bedX;
    var bedY = req.query.bedY;
    var patientSeq = req.query.patientSeq;
    var patientname = req.query.patientname;
    client.query('insert into bed (roomCode, bedX, bedY, patientSeq, patientName) values (?, ?, ?, ?, ?)', [roomCode, bedX, bedY, patientSeq, patientname], function(err, result, field){
        if(err){
            console.log('insert error');
            res.status(400);
        }
        else{
            console.log('add success');
            res.status(200);
        }
    })
}
