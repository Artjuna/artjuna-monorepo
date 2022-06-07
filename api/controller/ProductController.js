const db = require('../model');
const { Op } = require("sequelize");
require('dotenv').config();
//create main model
const Product = db.products;
const productSeen = db.productSeen;
const Account = db.Accounts

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
        //let page = req.query.page;
        let getAllProduct = await Product.findAll({raw: true});
        const page = parseInt(req.query.page);
        const limit = parseInt(req.query.limit)

        const startIndex = (page - 1) * limit
        const endIndex = page * limit

        const results = {}

        if (endIndex < Object.keys(getAllProduct).length) {
        results.next = {
            page: page + 1,
            limit: limit
        }
        }
        
        if (startIndex > 0) {
        results.previous = {
            page: page - 1,
            limit: limit
        }
        }

        let slice = getAllProduct.slice(startIndex, endIndex);
        let fix = [];
        for (let i = 0; i < Object.keys(slice).length; i++)
        {
            let temp = slice[i];
            const fullname = await Account.findOne({raw: true, where:{
                UserID : temp.UserID
            },
            attributes: 
            ['FullName']})
            temp.FullName = fullname.FullName;
            fix.push(temp)
        }

        results.results = fix;
        res.json(results);
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
        const fullName = await Account.findOne({raw: true, where:{
            UserID : element.UserID
        },
        attributes: 
        ['FullName']})
        getMyProduct.FullName = fullName.FullName;
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
        let ProductID = req.query.ProductID;
        let UserID = req.query.UserID;
        let ProductName = req.query.ProductName;
        let Category = req.query.Category;
        let Province = req.query.Province;
        let City = req.query.City;
        let Price = req.query.Price;
        var List = {
            ProductID: ProductID,
            UserID: UserID,            
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
        
            // const getProduct = await Product.findAll({raw: true,
            //     where: List
            // }); 

        let getProduct = {};

        if (ProductName == null || ProductName == undefined)
        {
            getProduct = await Product.findAll({raw: true,
                where: List
            });        
        }
        else{

            getProduct = await Product.findAll({raw: true,
                where: {ProductName: {[Op.like]: '%' + ProductName + '%'}}
            });
            
        }
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