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

exports.modifyDevice = function (req, res) {
    var device = {
        "deviceId" : req.deviceId,
        "videoUrl" : req.videoUrl,
        "snapShotUrl" : req.snapShotUrl
    }
}

exports.getIP = function(req, res){
    var IP = req.query.IP;
    var serialNumber = req.query.serialNumber;
    console.log('post raspberry');
    client.query('select * from raspberryPI where serialNumber = ?' , serialNumber, function(error, result, field){
        if(error){
            console.log('serialNumber error');
            console.log('IP : ', IP);
            console.log('serial : ', serialNumber);
            res.send();
        }
        else if(result.length>0){
            console.log('raspberryPI result : ', result);
            client.query('update raspberryPI set Ipaddress = ? where serialNumber = ?', [IP, serialNumber])
            res.send();
        }
        else{
            client.query('insert into raspberryPI (serialNumber, Ipaddress) values (?, ?)', [serialNumber, IP], function(err, results, fields){
                if(err){
                    console.log('insert error');
                    console.log(err);
                    res.send();
                }
                else{
                    console.log('not insert error');
                    res.send();
                }
            })
        }
    })
}