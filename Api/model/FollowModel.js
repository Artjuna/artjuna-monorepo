const Sequelize = require('sequelize');
module.exports = (sequelize) => {

const Follow = sequelize.define(
    "follow",
    {
        // username: {type: Sequelize.STRING},
        // email: {type: Sequelize.STRING},
        // password: {type: Sequelize.STRING}
        UserID: {type: Sequelize.STRING,
            primaryKey: true
        },
        FollowedUserID: {type: Sequelize.STRING, primaryKey: true},
        createdAt: {type: Sequelize.DATE, allowNull: true}
    },
    {
        freezeTableName: true,
        timestamps: false
    }
);  
    return Follow;
}