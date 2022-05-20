const Sequelize = require('sequelize');
module.exports = (sequelize) => {

const Product = sequelize.define(
    "produk",
    {
        // username: {type: Sequelize.STRING},
        // email: {type: Sequelize.STRING},
        // password: {type: Sequelize.STRING}
        ProdukID: {type: Sequelize.STRING,
            primaryKey: true
        },
        UserID: {type: Sequelize.STRING, primaryKey: true},
        NamaProduk: {type: Sequelize.STRING, allowNull: false},
        Kategori: {type: Sequelize.STRING, allowNull: false},
        Provinsi: {type: Sequelize.STRING, allowNull: false},
        Kota: {type: Sequelize.STRING, allowNull: false},
        Caption: {type: Sequelize.STRING, allowNull: false},
        Harga: {type: Sequelize.INTEGER, allowNull: false},
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