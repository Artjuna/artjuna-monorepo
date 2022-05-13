const sequelize = require('sequelize');

const db = new sequelize("Artjuna", "root", "", {
    dialect:"mysql"
});

db.sync({});

module.exports = db;