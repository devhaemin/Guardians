var express = require('express');
var login = require('./js/loginroutes');
var bodyParser = require('body-parser');
var py = require('./js/runModel');
var upload = require('./js/imageupload');
var app = express();
var server = require('http').createServer(app);
var socketio = require('socket.io');
var Q = require('q');
app.use(bodyParser.urlencoded({extended: true}));
app.use(bodyParser.json());

app.use(function(req, res, next){
	res.header("access-control-allow-origin", "*");
	res.header("access-control-allow-headers", "origin, x-requested-with, content-type, accept");
	next();
});

var router = express.Router();

app.get('/', function(req, res){
	res.json({message: 'welcome to our upload module apis'});
	console.log('get /');
});
app.get('/bedinfo', login.bedinfo);
app.get('/editbedinfo', login.editbedinfo);
app.get('/changeRate', py.changeRate);
app.get('/getinfo', login.sendpatientinfo);
app.get('/Tokenlogin', login.Tokenlogin);
app.post('/register', login.register);
app.get('/login', login.login);
app.post('/checkemail', login.checkemail);
app.post('/:filename', upload.upload);
app.use('/api', router);
app.listen(52273, function(){
	console.log('server running');
});
