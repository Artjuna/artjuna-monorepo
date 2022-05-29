const db = require('../model');
require('dotenv').config();

//create main model
const Account = db.Accounts;
const Follow = db.Follow;

const addAccount = async (req, res) => {
    try{
        const { Email, UserName, FullName, OriginProvince, OriginCity, Telephone } = req.body;      
        let getAllAccount = await Account.findAll({raw: true});     
        const json = Object.keys(getAllAccount).length;    
        const ts = new Date();
        const UserID = `${ts.getFullYear()}U${json}`;
        const Followers = 0;
        const createdAt = ts;
        // initialize models database
        const newAccount = new Account({
            UserID,
            Email,
            UserName,
            FullName,
            OriginProvince,
            OriginCity,
            Followers,
            Telephone,
            createdAt
        });

            await newAccount.save();
            res.json(newAccount);
    }
    catch (err)
    {
        console.error(err.message);
        res.status(500).send("server error");
    }
}

const getAllAccount = async (req, res) => {
    try{
        const getAllAccount = await Account.findAll({});

        res.json(getAllAccount);
    }
    catch (err)
    {
        console.error(err.message);
        res.status(500).send("server error");
    }
}

const getAccountByUserID = async (req, res) => {
    try{
        const UserID = req.params.UserID;
        const getMyAccount = await Account.findAll({    
            where: {
                UserID: UserID
            }
        });
        const getFollowers = await Follow.findAll({raw: true, where: {
            UserIDFollowed: UserID
        }});
        

        res.json(getMyAccount);

    }
    catch (err)
    {
        console.error(err.message);
        res.status(500).send(err.message);
    }
}

module.exports = {
    addAccount,
    getAllAccount,
    getAccountByUserID
}