const { Accounts } = require('../model');
const db = require('../model');
require('dotenv').config();

const Followers = db.Follow;
const Account = db.Accounts;

const hasFollowed = async (req, res) => {
    try{
        const { UserIDFollowed, UserIDFollowing} = req.body;      
        const createdAt = Date.now();
        // initialize models database
        const Follow = new Followers({
            UserIDFollowed,
            UserIDFollowing,           
            createdAt
        });
        const DataFollow = await Account.findOne({raw: true, where:{
            UserID: UserIDFollowed
        }});
        
        let newAccount = DataFollow;
        newAccount.Followers = newAccount.Followers + 1;

        await Account.update({Followers: newAccount.Followers},{
            where: {
                UserID: UserIDFollowed
            }
        })

        await Follow.save();
        res.json(Follow);
    }
    catch (err)
    {
        console.error(err.message);
        res.status(500).send("server error");
    }
}
module.exports = {
    hasFollowed
}