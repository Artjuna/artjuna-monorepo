const Sequelize = require('sequelize');
module.exports = (sequelize) => {

const Account = sequelize.define(
    "account",
    {
        // username: {type: Sequelize.STRING},
        // email: {type: Sequelize.STRING},
        // password: {type: Sequelize.STRING}
        UserID: {type: Sequelize.STRING,
            primaryKey: true
        },        
        Email: {type: Sequelize.STRING, allowNull: false},
        UserName: {type: Sequelize.STRING, allowNull: false},
        NamaLengkap: {type: Sequelize.STRING, allowNull: false},
        ProvinsiAsal: {type: Sequelize.STRING, allowNull: false},
        KotaAsal: {type: Sequelize.STRING, allowNull: false},
        Telepon: {type: Sequelize.INTEGER, allowNull: false},
        Pengikut: {type: Sequelize.INTEGER, allowNull: false},
        createdAt: {type: Sequelize.DATE, allowNull: true, }
    },
    {
        freezeTableName: true,
        timestamps: false
    }
);  
    return Account;
}