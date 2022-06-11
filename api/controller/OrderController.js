const db = require('../model');
require('dotenv').config();
//create main model
const Product = db.products;
const Account = db.Accounts;
const Order = db.Order;

const addOrder = async (req, res) => {
    try{        
        let { SellerUserID, BuyerUserID, ProductID, BuyerPhoneNumber, 
            TotalPrice, ShippingAddress, Image} = req.body;  
            
        if (Image == null || Image == undefined)
        {
            let temp = await Product.findOne({raw: true, where: {
                ProductID: ProductID
            }, attributes: 
            ['Image']
            });

            Image = temp.Image;
        }
        else{
            let fileUrl = req.file.path.replace(/\\/g, "/").substring("OrderImages".length);
            Image = fileUrl;
        }
        let SellerFullNameTemp = await Account.findOne({raw: true, where: {
            UserID : SellerUserID,
            }, attributes: 
            ['FullName']
            });     
        SellerFullName = SellerFullNameTemp.FullName;
        let BuyerFullNameTemp = await Account.findOne({raw: true, where: {
            UserID : BuyerUserID
            }, attributes: 
            ['FullName']});
        BuyerFullName = BuyerFullNameTemp.FullName;
        let ProductNameTemp = await Product.findOne({raw: true, where: {
            ProductID: ProductID
            }, attributes: 
            ['ProductName']
            })
        ProductName = ProductNameTemp.ProductName;
        let getAllOrder = await Order.findAll({raw: true});

        const json = Object.keys(getAllOrder).length;    
        const ts = new Date();      
        const OrderID = `${ts.getFullYear()}O${json}`;
        const createdAt = ts;
        // initialize models database
        const newOrder = new Order({
            OrderID,
            SellerUserID,
            BuyerUserID,
            ProductID,
            SellerFullName,
            BuyerFullName,
            BuyerPhoneNumber,
            ProductName,
            TotalPrice,
            ShippingAddress,
            Image,
            createdAt
        });

            await newOrder.save();
            // newProduk.Image = buff.toString('base64')
            res.status(200).send("Insert Data Success");
            
    }
    catch (err)
    {
        console.error(err.message);
        res.status(500).send("err.message");
        
    }
}

const getOrderFilter = async (req, res) => {
    try{
        let ProductID = req.query.ProductID;
        let SellerUserID = req.query.SellerUserID;
        let BuyerUserID = req.query.BuyerUserID;
        let OrderID = req.query.OrderID;
        
        var List = {
            ProductID: ProductID,
            SellerUserID: SellerUserID,
            BuyerUserID: BuyerUserID,
            OrderID: OrderID,       
        }

        for (var key of Object.keys(List))
        {
            if (List[key] == undefined)
            {
                delete List[key]; 
            }
        }
        
        const getOrder = await Order.findAll({raw: true,
            where: List
        });
        
        res.json(getOrder);
    }
    catch (err)
    {
        console.error(err.message);
        res.status(500).send("server error");
    }
}

module.exports = {
    addOrder,
    getOrderFilter
}