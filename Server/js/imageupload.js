var Q = require('q');
var mysql = require('mysql');
var moment = require('moment');
var fs = require('fs');
require('moment-timezone');
moment.tz.setDefault("Asia/Seoul");
var multer = require('multer');
var client = mysql.createConnection({
	host : 'localhost',
	user : 'ict',
	password : 'rkeldjswm',
	database : 'ictdb'
});
exports.upload = function(req, res){
	var date = moment().format('YYYY-MM-DD HH:mm:ss');
	console.log('imageupload post time : ', date);
	var deferred = Q.defer();
	var serialNumber = req.query.filename;
	client.query('select patientSeq from raspberryPI where serialNumber = ?', serialNumber, function(error, result, field){
		console.log(result[0].patientSeq);
		var storage = multer.diskStorage({
			destination: function(req, file, cb){
				cb(null,'/root/img/');
			},
			filename: function(req, file, cb){
				file.uploadFile = {
					name : result[0].patientSeq,
					ext : file.mimetype.split('/')[1]
				};
				cb(null, file.uploadFile.name + '.' + file.uploadFile.ext);
			}
		});
		var upload = multer({ storage: storage }).single('file');
		upload(req, res, function(err){
			if (err) {
				console.log('upload fail');
				deferred.reject();
				res.send();
			}
			else {
				deferred.resolve(req.file.uploadedFile);
				console.log('upload');
				res.send();
			}
		});
		return deferred.promise;
	})
};
