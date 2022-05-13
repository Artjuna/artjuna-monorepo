const express = require('express');
const app = express();
const db = require('./config/db');
const Produk = require('./model/Product')

db.authenticate().then(() => console.log("respon nodejs berhasil"));
app.use(express.urlencoded({extended: true}));
app.use(express.json())

app.post("/produk", async (req, res) => {
    try{
        const { NamaProduk, Kategori, Provinsi, Kota, Caption, Harga } = req.body;      
        const getAllProduk = await Produk.findAll({raw: true});     
        const json = Object.keys(getAllProduk).length;        
        const ProdukID = `P${json}`;
        console.log(ProdukID);
        const UserID = `U${json}`;
        const createdAt = Date.now();
        // initialize models database
        const newProduk = new Produk({
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
})

app.get("/produk", async (req, res) => {
    try{
        const getAllProduk = await Produk.findAll({});

        res.json(getAllProduk);
    }
    catch (err)
    {
        console.error(err.message);
        res.status(500).send("server error");
    }
})

app.get("/", (req, res) => res.send("respoin nodeJS berhasil"));

app.listen(5000, () => console.log("port berjalan di 5000"));