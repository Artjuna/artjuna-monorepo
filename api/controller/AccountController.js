const { sequelize } = require('../model');
const db = require('../model');
require('dotenv').config();

//create main model
const Account = db.Accounts;
const Follow = db.Follow;

const addAccount = async (req, res) => {
    try{
        const { Email, UserName, FullName, OriginProvince, OriginCity, Telephone, IsStore } = req.body;      
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
            IsStore,
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

const getAccountFilter = async (req, res) => {
    try{
        let { Email, UserName, FullName, OriginProvince, OriginCity, Telephone, IsStore} = req.body;
        var List = {
            Email: Email,
            UserName: UserName,
            FullName: FullName,
            OriginProvince: OriginProvince,
            OriginCity: OriginCity,
            Telephone: Telephone,
            IsStore: IsStore
        }

        for (var key of Object.keys(List))
        {
            if (List[key] == undefined)
            {
                delete List[key]; 
            }
        }
        
        const getAllAccount = await Account.findAll({raw: true,
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

const updateAccount = async (req, res) => {
    let { UserID, Email, UserName, FullName, OriginProvince, OriginCity, Telephone, IsStore} = req.body;
        var List = {
            Email: Email,
            UserName: UserName,
            FullName: FullName,
            OriginProvince: OriginProvince,
            OriginCity: OriginCity,
            Telephone: Telephone,
            IsStore: IsStore
        }

        for (var key of Object.keys(List))
        {
            if (List[key] == undefined)
            {
                delete List[key]; 
            }
        }

        await Account.update(List, {where:{
          UserID: UserID  
        }})

        res.status(200).send("Update data success");
}

module.exports = {
    addAccount,
    getAllAccount,
    getAccountByUserID,
    getAccountFilter,
    updateAccount
}