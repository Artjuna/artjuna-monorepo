// const Sequelize = require('sequelize');
// module.exports = (sequelize) => {

// const Order = sequelize.define(
//     "order",
//     {
//         // username: {type: Sequelize.STRING},
//         // email: {type: Sequelize.STRING},
//         // password: {type: Sequelize.STRING}
//         OrderID: {type: Sequelize.STRING,
//             primaryKey: true
//         },
//         SellerUserID: {type: Sequelize.STRING},
//         BuyerUserID: {type: Sequelize.STRING},
//         Harga: {type: Sequelize.STRING, allowNull: false},
//         Kategori: {type: Sequelize.STRING, allowNull: false},
//         Caption: {type: Sequelize.STRING, allowNull: false},
//         Image: {type: Sequelize.STRING, allowNull: true},
//         Like: {type: Sequelize.INTEGER},
//         createdAt: {type: Sequelize.DATE, allowNull: true, primaryKey: true}
//     },
//     {
//         freezeTableName: true,
//         timestamps: false
//     }
// );  
//     return Order;
// }