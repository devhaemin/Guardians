var pythonShell = require('python-shell')
var fs = require('fs')
var mysql = require('mysql')
var client = mysql.createConnection({
    host: 'localhost',
    user: 'ict',
    password: 'rkeldjswm',
    database: 'ictDB'
});
var pyPath = './py/model.py'
var imgPath = './img/112_20190928090320.jpg' // (roomNum)_(time)
exports.changeRate = function (req, res) {
    fs.exists(imgPath, function (exists) {
        if (!exists) fs.open(imgPath, 'w', function (err, f) {
            if (err) throw err;
        });
    });

    var options = {
        mode: 'text',
        pythonPath: '',
        pythonOptions: ['-u'],
        scriptPath: '',
        args: [imgPath]
    };
    pythonShell.PythonShell.run(pyPath, options, function (err, results) {

        if (err) throw err;

        results[0] *= 1;

        // results: 0~6(pos)  imgPath: (roomNum)_(time).jpg

        client.query('update patient set warningRate = ? where patientSeq=?', [results[0], 1], function (err) {
        })
        fs.unlink(imgPath, function (err) {
            if (err) throw err;
        });
    });
}
