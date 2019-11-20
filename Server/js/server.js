var express = require('express');
var login = require('./js/loginroutes');
var patient = require('./js/patient');
var bodyParser = require('body-parser');
var py = require('./js/runModel');
var raspberry = require('./js/raspberry');
var alarm = require('./js/Alarm');
var app = express();
var server = require('http').createServer(app);
var Q = require('q');
app.use(bodyParser.urlencoded({extended: true}));
app.use(bodyParser.json());

app.use(function(req, res, next){
	res.header("access-control-allow-origin", "*");
	res.header("nfoaccess-control-allow-headers", "origin, x-requested-with, content-type, accept");
	next();
});

var router = express.Router();

app.get('/', function(req, res){
	res.json({message: 'welcome to our upload module apis'});
	console.log('get /');
});

app.get('/updatefcm', login.fcmToken);
app.get('/addpatient', patient.addpatient);
app.get('/warnningRate', patient.warningRate)
app.get('/editBedInfo', patient.editbedinfo);
app.get('/edituserinfo', patient.edituserinfo);
app.get('/editroominfo', patient.editroominfo);
app.get('/editpatientinfo', patient.editpatientinfo);
app.get('/push', alarm.sendAlarm);
app.get('/Tokenlogin', login.Tokenlogin);
app.get('/sendAlarmList', alarm.sendAlarmList);
app.get('/autoSearch', login.autosearch);
app.get('/bedInfo', patient.bedInfo);
app.get('/roomCodeSearch', patient.roomCodesearch);
app.get('/changeRate', py.changeRate);
app.get('/sendPatientInfo', patient.sendPatientInfo);
app.get('/searchPatientInfo', patient.searchPatientInfo);
app.get('/login', login.login);

app.post('/turnOnRaspberryPI', raspberry.getIP);
app.post('/register', login.register);
app.post('/checkemail', login.checkemail);
// app.post('/:filename', upload.upload);
app.listen(52273, function(){
	console.log('server running');
});