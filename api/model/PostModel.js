const Sequelize = require('sequelize');
module.exports = (sequelize) => {

const Posting = sequelize.define(
    "posting",
    {
        // username: {type: Sequelize.STRING},
        // email: {type: Sequelize.STRING},
        // password: {type: Sequelize.STRING}
        PostID: {type: Sequelize.STRING,
            primaryKey: true
        },
        UserID: {type: Sequelize.STRING, primaryKey: true},
        PostName: {type: Sequelize.STRING, allowNull: false},
        Caption: {type: Sequelize.STRING, allowNull: false},
        Image: {type: Sequelize.STRING, allowNull: true},
        Like: {type: Sequelize.INTEGER},
        createdAt: {type: Sequelize.DATE, allowNull: true}
    },
    {
        freezeTableName: true,
        timestamps: false
    }
);  
    return Posting;
}