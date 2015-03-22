var express = require('express');
var path = require('path');
var app = express();

var mysql = require('mysql');
var pool = mysql.createPool({
    connectionLimit: 100, //important
    host: 'markkarledb.c3jdqwxitldw.us-east-1.rds.amazonaws.com',
    user: 'markakarle',
    password: 'markisawesome',
    database: 'nashvilledb',
    debug: false
});
function handle_database(req, res) {

    pool.getConnection(function (err, connection) {
        if (err) {
            console.log(err);
            res.json({ "code": 100, "status": "Error in connection database" });
            connection.release();
            return;
        }
        var resRows = null;
        connection.query("SELECT * FROM `Restaurants` WHERE `ID` >= (SELECT FLOOR( MAX(`ID`) * RAND()) FROM `Restaurants` ) LIMIT 1;", function (err, rows) {
            if (!err) {
                resRows = rows;
            }
        });
        connection.query("SELECT * FROM `Activities` WHERE `ID` >= (SELECT FLOOR( MAX(`ID`) * RAND()) FROM `Activities` ) LIMIT 1;", function (err, rows) {
            connection.release();
            if (!err) {
                resRows = resRows.concat(rows);
                console.log(resRows);

                res.json(resRows);
            }
        });
        connection.on('error', function (err) {
            console.log(err);
            res.json({ "code": 100, "status": "Error in connection database" });
            return;
        });
    });
}

app.use(express.static(__dirname + "/public"));

app.get('/', function (req, res) {
    var p = path.resolve(__dirname + '/Site.html');
	res.sendFile(p);
});
app.get('/generate', function (req, res) {
    handle_database(req, res);
})
var server = app.listen(8888, function (){
	var host = server.address().address;
	var port = server.address().port;
	console.log('Server listening at http://%s:%s', host, port);
})