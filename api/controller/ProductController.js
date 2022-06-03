const db = require('../model');
const fs = require('fs');
require('dotenv').config();
//create main model
const Product = db.products;
const productSeen = db.productSeen;

const addProduct = async (req, res) => {
    try{
        
        let { UserID, ProductName, Category, Province, City, Description, Price} = req.body;  
        let fileUrl = req.file.path.replace(/\\/g, "/").substring("ProductImages".length);
        let Image = fileUrl;
        let getAllProduk = await Product.findAll({raw: true});     
        const json = Object.keys(getAllProduk).length;    
        const ts = new Date();      
        const ProductID = `${ts.getFullYear()}P${json}`;
        const createdAt = ts;
        const HasSeen = 0;
        // initialize models database
        const newProduk = new Product({
            ProductID,
            UserID,
            ProductName,
            Category,
            Province,
            City,
            Description,
            Price,
            Image,
            HasSeen,
            createdAt
        });

            await newProduk.save();
            // newProduk.Image = buff.toString('base64')
            res.status(200).send("Insert Data Success");
            
    }
    catch (err)
    {
        console.error(err.message);
        res.status(500).send("err.message");
        
    }
}

const getAllProduct = async (req, res) => {
    try{
        let getAllProduk = await Product.findAll({raw: true});
        let Gambar = "";
        for(let element in getAllProduct)
        {
            element.Image = new Buffer(element.Image, 'base64');
        }
        res.json(getAllProduk);
    }
    catch (err)
    {
        console.error(err.message);
        res.status(500).send("err.message");
    }
}

const getProductByProductID = async (req, res) => {
    try{
        const ProductID = req.params.ProductID;
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

const updateProduct = async (req, res) => {
    try{
        let { ProductID, ProductName, Category, Province, City, Description, Price} = req.body;
        var List = {
            ProductName: ProductName,
            Category: Category,
            Province: Province,
            City: City,
            Description: Description,
            Price: Price
        }

        for (var key of Object.keys(List))
        {
            if (List[key] == undefined)
            {
                delete List[key]; 
            }
        }

        await Product.update(List, {where:{
          ProductID: ProductID
        }})

        res.status(200).send("Update data success");
    }
    catch (err)
    {
        console.error(err.message);
        res.status(500).send(err.message);
    }
    
}

const getProductFilter = async (req, res) => {
    try{
        let { ProductID, ProductName, Category, Province, City, Price} = req.body;
        var List = {
            ProductID: ProductID,
            ProductName: ProductName,
            Category: Category,
            Province: Province,
            City: City,
            Price: Price
        }

        for (var key of Object.keys(List))
        {
            if (List[key] == undefined)
            {
                delete List[key]; 
            }
        }
        
        const getProduct = await Product.findAll({raw: true,
            where: List
        });
        // const getAllAccount = await Account.findAll({raw: true,
        //     attributes: {
        //         include: [
        //             [
        //                 sequelize.literal(`
        //                 (
        //                     SELECT * FROM account
        //                 )
        //                 `)
        //             ]
        //         ]
        //     }
        // });

        res.json(getProduct);
    }
    catch (err)
    {
        console.error(err.message);
        res.status(500).send("server error");
    }
}

const getProductCategory = async (req, res) => {
    try{
        const getMyProduct = await Product.findAll({
            
            attributes: 
                ['Category']
            ,
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

module.exports = {
    addProduct,
    getAllProduct,
    getProductByProductID,
    hasSeen,
    updateProduct,
    getProductFilter,
    getProductCategory
}