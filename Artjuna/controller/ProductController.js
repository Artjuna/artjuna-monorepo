const db = require('../model');
require('dotenv').config();
//create main model
const Product = db.products;

const addProduct = async (req, res) => {
    try{
        const { UserID, NamaProduk, Kategori, Provinsi, Kota, Caption, Harga } = req.body;      
        let getAllProduk = await Product.findAll({raw: true});     
        const json = Object.keys(getAllProduk).length;        
        const ProdukID = `P${json}`;
        console.log(ProdukID);
        const createdAt = Date.now();
        // initialize models database
        const newProduk = new Product({
            ProdukID,
            UserID,
            NamaProduk,
            Kategori,
            Provinsi,
            Kota,
            Caption,
            Harga,
            createdAt
        });

            await newProduk.save();
            res.json(newProduk);
    }
    catch (err)
    {
        console.error(err.message);
        res.status(500).send("server error");
    }
}

const getProduct = async (req, res) => {
    try{
        const getAllProduk = await Produk.findAll({});

        res.json(getAllProduk);
    }
    catch (err)
    {
        console.error(err.message);
        res.status(500).send("server error");
    }
}



module.exports = {
    addProduct,
    getProduct
}