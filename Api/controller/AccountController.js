const { Follow } = require('../model');
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
        const getAllAccount = await Account.findAll({raw: true});
        for (const element of getAllAccount)
        {
            const follow = await Follow.findAll({raw: true, where: {
                UserID: element.UserID
            }})
            const sumLike = Object.keys(follow).length;
            element.Pengikut = sumLike;
        }
        res.json(getAllAccount);
    }
    catch (err)
    {
        console.error(err.message);
        res.status(500).send("server error");
    }
}

const getMyAccount = async (req, res) => {
    try{
        const getMyAccount = await Account.findAll({raw: true, where:
        {
            UserID: req.body.UserID
        }});

        res.json(getMyAccount);
    }
    catch (err)
    {
        console.error(err.message);
        res.status(500).send("server error");
    }
}

const updateMyAccount = async (req, res) => {
    try{
        const { UserID, Email, UserName, NamaLengkap, ProvinsiAsal, KotaAsal, Telepon } = req.body;
        
        await Account.upsert({
            UserID: UserID,
            Email: Email,
            UserName: UserName,
            NamaLengkap: NamaLengkap,
            ProvinsiAsal: ProvinsiAsal,
            KotaAsal: KotaAsal,
            Telepon: Telepon
        });
        
        res.json("Update Succes");
    }
    catch (err)
    {
        console.error(err.message);
        res.status(500).send("server error");
    }
}

module.exports = {
    addAccount,
    getAccount,
    getMyAccount,
    updateMyAccount
}