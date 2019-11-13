var mysql = require('mysql');
var admin = require('firebase-admin');
var FCM = require('fcm-node');
var serverKey = require('/root/js/fcmserverkey.json');
var client = mysql.createConnection({
    host : 'localhost',
    user : 'ict',
    password : 'rkeldjswm',
    database : 'ictdb',
    port : '3306'
});
var fcm = new FCM(serverKey);

exports.sendAlarmList = function(req, res){
    var token = req.get('Access-Token');
    console.log(token);
    client.query('select userSeq from user where accessToken = ?', token, function(error, results, fields) {
        var userSeq = results[0].userSeq;
        client.query('select * from Alarm where userSeq = ? order by timeStamp DESC', userSeq, function (err, result, field) {
            if (err) {
                console.log('sendAlarmList error');
                res.send();
            } else {
                console.log('sendAlarmList');
                res.send(result);
            }
            client.query('update Alarm set readed = 1 where userSeq = ?', userSeq, function (err, result, field) {
                if (err) {
                    console.log('error');
                } else {
                    console.log('Alarm update');
                }
            })
        })
    })
};
exports.sendAlarm = function(req,res){
    var patientSeq = req.query.patientSeq;
        client.query('select * from patient where patientSeq = ?', patientSeq, function(error, results, fields) {
            var timestamp = Date.now();
            client.query('insert into Alarm (userSeq, profileImageUrl, warningRate, patientName, patientSeq, timeStamp, readed) values (1, ?, ?, ?, ?, ?, 0)', [results[0].profileImageUrl, results[0].warningRate, results[0].patientName, patientSeq, timestamp ], function(errors, resul, fieldss){
                if(errors){
                    console.log('insert error');
                    console.log(errors);
                    res.send();
                }
                else{
                    console.log('insert seccess');
                    res.send();
                }
            })
                var client_Token = 'fbMVSRNesRg:APA91bFt3hxWLk-yEvHDsyvRcjKElHqk5-UXb6iSs9jseG0qO-7PuZxcIlDhsr7h7T-DZqBvO864Cd-NkZd5nfUmVDK-Yd45tGo0Xs7fUmgo7i-YSWzMX9kBnvg8TnJZd6CboGvAUd3H';
                var data_warningRate = results[0].warningRate + "%";
                var patientseq = results[0].patientSeq = '';
                var body = results[0].roomCode + "호 " + results[0].patientName + "환자, 위험도 : " + results[0].warningRate + "%";
                var push_data = {
                    to: client_Token,
                    data: {
                        "title": "[위험환자 발생알림]",
                        "body":body,
                        "warningRate": data_warningRate,
                        "patientseq" : patientseq
                    }
                }

                fcm.send(push_data, function (err, response) {
                    if (err) {
                        console.error('Push메시지 발송에 실패했습니다.');
                        console.error(err);
                        return;
                    }
                    console.log('Push메시지가 발송되었습니다.');
                    return;
                });

        });
};

