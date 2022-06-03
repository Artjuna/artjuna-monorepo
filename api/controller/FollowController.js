const { Accounts } = require('../model');
const db = require('../model');
require('dotenv').config();

const Followers = db.Follow;
const Account = db.Accounts;

const hasFollowed = async (req, res) => {
    try{
        const { UserIDFollowed, UserIDFollowing} = req.body; 
        const FollowDB = await Followers.findOne({raw: true, where:{
            UserIDFollowed: UserIDFollowed,
            UserIDFollowing: UserIDFollowing
        }})
        if (FollowDB != null)
        {
            res.status(200).send("Already following");
            return res;
        }
             
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
        res.status(500).send("Following completed");
    }
    catch (err)
    {
        console.error(err.message);
        res.status(500).send("server error");
    }
}

const unFollowed = async (req, res) => {
    try{
        const { UserIDFollowed, UserIDFollowing} = req.body;      
        const createdAt = Date.now();
        // initialize models database
        // const Follow = new Followers({
        //     UserIDFollowed,
        //     UserIDFollowing,           
        //     createdAt
        // });
        const DataFollow = await Account.findOne({raw: true, where:{
            UserID: UserIDFollowed
        }});
        
        let newAccount = DataFollow;
        newAccount.Followers = newAccount.Followers - 1;

        await Account.update({Followers: newAccount.Followers},{
            where: {
                UserID: UserIDFollowed
            }
        })

        await Followers.destroy({where: {
            UserIDFollowed: UserIDFollowed,
            UserIDFollowing: UserIDFollowing
        }})
        res.status(200).send("Unfollowed completed");
    }
    catch (err)
    {
        console.error(err.message);
        res.status(500).send("server error");
    }
}
module.exports = {
    hasFollowed,
    unFollowed
}