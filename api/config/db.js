require('dotenv').config();
// const fs = require('fs');
// const path = require('path');
// const key = fs.readFileSync(path.join(__dirname, '../cert', 'client-key.pem'));
// const cert = fs.readFileSync(path.join(__dirname, '../cert', 'client-cert.pem'));
// const ca = fs.readFileSync(path.join(__dirname, '../cert', 'server-ca.pem'));
// import cKey from 'raw-loader!../client-key.pem';
// import cCert from 'raw-loader!../client-cert.pem';
// import cCa from 'raw-loader!../server-ca.pem';

module.exports = {
    HOST: process.env.HOST,
    USER: 'root',
    PASSWORD: process.env.PASSWORD,
    DB: 'Artjuna',
    dialect: 'mysql',
    dialectOptions: {
        useUTC: false, //for reading from database
        dateStrings: true,
        typeCast: true
  },
  timezone: '+07:00' //for writing to database

    // pool: {
    //     max: 5,
    //     min: 0,
    //     acquire: 30000,
    //     idle: 10000
    // }
}