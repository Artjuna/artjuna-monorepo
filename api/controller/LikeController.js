const db = require('../model');
require('dotenv').config();

const Like = db.Like;

const hasLiked = async (req, res) => {
    try{
        const { UserID, PostID} = req.body;      
        const createdAt = Date.now();
        // initialize models database
        const Liked = new Like({
            PostID,
            UserID,           
            createdAt
        });

            await Liked.save();
            res.json(Liked);
    }
    catch (err)
    {
        console.error(err.message);
        res.status(500).send("server error");
    }
}

const unliked = async (req, res) => {
    try{
        const { UserIDFollowed, UserIDFollowing} = req.body;      
        const createdAt = Date.now();
        // initialize models database
        // const Follow = new Followers({
        //     UserIDFollowed,
        //     UserIDFollowing,           
        //     createdAt
        // });
        const DataFollow = await Like.findOne({raw: true, where:{
            UserID: UserIDFollowed
        }});
        
        let newAccount = DataFollow;
        newAccount.Followers = newAccount.Followers - 1;

        await Like.update({Followers: newAccount.Followers},{
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
    hasLiked
}