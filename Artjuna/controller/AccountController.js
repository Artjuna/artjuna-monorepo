const db = require('../model');
require('dotenv').config();

//create main model
const Account = db.Accounts;

const addAccount = async (req, res) => {
    try{
        const { Email, UserName, NamaLengkap, ProvinsiAsal, KotaAsal, Telepon } = req.body;      
        let getAllAccount = await Account.findAll({raw: true});     
        const json = Object.keys(getAllAccount).length;    
        const ts = new Date();
        const UserID = `${ts.getFullYear()}U${json}`;
        const Pengikut = 0;
        const createdAt = ts;
        // initialize models database
        const newAccount = new Account({
            UserID,
            Email,
            UserName,
            NamaLengkap,
            ProvinsiAsal,
            KotaAsal,
            Telepon,
            Pengikut,
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

const getAccount = async (req, res) => {
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



module.exports = {
    addAccount,
    getAccount
}