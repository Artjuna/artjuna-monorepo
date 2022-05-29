const db = require('../model');
require('dotenv').config();
//create main model
const Product = db.products;
const productSeen = db.productSeen;

const addProduct = async (req, res) => {
    try{
        const { UserID, ProductName, Category, Province, City, Caption, Price, Image } = req.body;      
        let getAllProduk = await Product.findAll({raw: true});     
        const json = Object.keys(getAllProduk).length;    
        const ts = new Date();    
        const ProductID = `${ts.getFullYear()}P${json}`;
        const createdAt = ts;
        // initialize models database
        const newProduk = new Product({
            ProductID,
            UserID,
            ProductName,
            Category,
            Province,
            City,
            Caption,
            Price,
            Image,
            createdAt
        });

            await newProduk.save();
            res.json(newProduk);
    }
    catch (err)
    {
        console.error(err.message);
        res.status(500).send("err.message");
    }
}

const getAllProduct = async (req, res) => {
    try{
        const getAllProduk = await Product.findAll({});

        res.json(getAllProduk);
    }
    catch (err)
    {
        console.error(err.message);
        res.status(500).send("err.message");
    }
}

const getProductByUserID = async (req, res) => {
    try{
        const { UserID} = req.body;
        const getMyProduct = await Product.findAll({
            where: {
                UserID: UserID
            }            
            
        });       

        res.json(getMyProduct);
    }
    catch (err)
    {
        console.error(err.message);
        res.status(500).send(err.message);
    }
}

const getProductByProductID = async (req, res) => {
    try{
        const {ProductID} = req.body;
        const getMyProduct = await Product.findAll({
            where: {
                ProductID: ProductID
            },
            raw: true
            
        });       
        res.json(getMyProduct);

    }
    catch (err)
    {
        console.error(err.message);
        res.status(500).send(err.message);
    }
}

const hasSeen = async (req, res) => {
    try{
        const { UserID, ProductID} = req.body;
        const createdAt = Date.now();
        const ProductSeen = new productSeen({
            ProductID,
            UserID,
            createdAt
        });

        

        const product = await Product.findOne({
            where: {
                ProductID: ProductID
            },
            raw: true
        });

        

        let newProduct = product;
        newProduct.HasSeen = newProduct.HasSeen + 1;
 
        await Product.update({HasSeen: newProduct.HasSeen},{
            where: {
                ProductID: product.ProductID
            }
        });
        await ProductSeen.save();
        res.status(200).send("seen added")
    }

    catch (err)
    {
        console.error(err.message);
        res.status(500).send(err.message);
    }
}

module.exports = {
    addProduct,
    getAllProduct,
    getProductByUserID,
    hasSeen
}