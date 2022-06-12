const Sequelize = require('sequelize');
module.exports = (sequelize) => {

const hasSeen = sequelize.define(
    "productSeen",
    {
        // username: {type: Sequelize.STRING},
        // email: {type: Sequelize.STRING},
        // password: {type: Sequelize.STRING}
        ProductID: {type: Sequelize.STRING,
            primaryKey: false
        },       
        UserID: {type: Sequelize.STRING, primaryKey: false},
        createdAt: {type: Sequelize.DATE, allowNull: true}
    },
    {
        freezeTableName: true,
        timestamps: false
    }
);  
    return hasSeen;
}