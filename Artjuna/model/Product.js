const Sequelize = require('sequelize');
const db = require('../config/db');

const Produk = db.define(
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
        createdAt: {type: Sequelize.DATE, allowNull: true}
    },
    {
        freezeTableName: true,
        timestamps: false
    }
);

module.exports = Produk;