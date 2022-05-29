const Sequelize = require('sequelize');
module.exports = (sequelize) => {

const hasSeen = sequelize.define(
    "productSeen",
    {
        // username: {type: Sequelize.STRING},
        // email: {type: Sequelize.STRING},
        // password: {type: Sequelize.STRING}
        ProductID: {type: Sequelize.STRING,
            primaryKey: true
        },       
        UserID: {type: Sequelize.STRING, primaryKey: true},
        createdAt: {type: Sequelize.DATE, allowNull: false}
    },
    {
        freezeTableName: true,
        timestamps: false
    }
);  
    return hasSeen;
}