const Sequelize = require('sequelize');
module.exports = (sequelize) => {

const Like = sequelize.define(
    "like",
    {
        // username: {type: Sequelize.STRING},
        // email: {type: Sequelize.STRING},
        // password: {type: Sequelize.STRING}
        PostID: {type: Sequelize.STRING,
            primaryKey: true
        },       
        UserID: {type: Sequelize.STRING, primaryKey: true},
        createdAt: {type: Sequelize.DATE, allowNull: true}
    },
    {
        freezeTableName: true,
        timestamps: false
    }
);  
    return Like;
}