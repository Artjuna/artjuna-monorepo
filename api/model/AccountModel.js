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
        FullName: {type: Sequelize.STRING, allowNull: false},
        OriginProvince: {type: Sequelize.STRING, allowNull: false},
        OriginCity: {type: Sequelize.STRING, allowNull: false},
        Telephone: {type: Sequelize.STRING, allowNull: false},
        Followers: {type: Sequelize.INTEGER, allowNull: false},
        createdAt: {type: Sequelize.DATE, allowNull: true },
        IsStore: {type: Sequelize.BOOLEAN, defaultValue: false}
    },
    {
        freezeTableName: true,
        timestamps: false
    }
);  
    return Account;
}