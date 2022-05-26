const Sequelize = require('sequelize');
module.exports = (sequelize) => {

const Product = sequelize.define(
    "produk",
    {
        // username: {type: Sequelize.STRING},
        // email: {type: Sequelize.STRING},
        // password: {type: Sequelize.STRING}
        ProductID: {type: Sequelize.STRING,
            primaryKey: true
        },
        UserID: {type: Sequelize.STRING, primaryKey: true},
        ProdukName: {type: Sequelize.STRING, allowNull: false},
        Category: {type: Sequelize.STRING, allowNull: false},
        Province: {type: Sequelize.STRING, allowNull: false},
        City: {type: Sequelize.STRING, allowNull: false},
        Caption: {type: Sequelize.STRING, allowNull: false},
        Price: {type: Sequelize.INTEGER, allowNull: false},
        Image: {type: Sequelize.STRING, allowNull: true},
        createdAt: {type: Sequelize.DATE, allowNull: true}
    },
    {
        freezeTableName: true,
        timestamps: false
    }
);  
    return Product;
}