const dbConfig = require('../config/db');

const {Sequelize} = require('sequelize');

const sequelize = new Sequelize(
    dbConfig.DB,
    dbConfig.USER,
    dbConfig.PASSWORD, {
        host: dbConfig.HOST,
        dialect: dbConfig.dialect,
        operatorsAliases: false,
    },
    dbConfig.timezone
);

sequelize.authenticate().then( () => {console.log('connected to database')})
.catch(err => {console.log('Error' + err)});

const db = {};

db.sequelize = sequelize;

db.products = require('./ProductModel.js')(sequelize);
db.Accounts= require('./AccountModel.js')(sequelize);

db.sequelize.sync({force: false}).then(() => {
    console.log('yes re-sync done!')
});

module.exports = db, sequelize;