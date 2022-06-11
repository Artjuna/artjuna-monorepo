const Sequelize = require('sequelize');
module.exports = (sequelize) => {

const Order = sequelize.define(
    "order",
    {
        // username: {type: Sequelize.STRING},
        // email: {type: Sequelize.STRING},
        // password: {type: Sequelize.STRING}\
        OrderID: {type: Sequelize.STRING, primaryKey: true},
        SellerUserID: {type: Sequelize.STRING,
            primaryKey: true
        },        
        BuyerUserID: {type: Sequelize.STRING, primaryKey: true},
        ProductID: {type: Sequelize.STRING, allowNull: false},
        SellerFullName: {type: Sequelize.STRING, allowNull: false},
        BuyerFullName: {type: Sequelize.STRING, allowNull: false},
        BuyerPhoneNumber: {type: Sequelize.STRING, allowNull: false},       
        ProductName: {type: Sequelize.STRING, allowNull: false},
        TotalPrice: {type: Sequelize.INTEGER, allowNull: false},
        ShippingAddress: {type: Sequelize.STRING, allowNull: false},
        Image: {type: Sequelize.STRING, allowNull: true},
        createdAt: {type: Sequelize.DATE, allowNull: true },        
    },
    {
        freezeTableName: true,
        timestamps: false
    }
);  
    return Order;
}