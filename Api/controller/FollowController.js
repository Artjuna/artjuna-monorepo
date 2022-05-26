const db = require('../model');
require('dotenv').config();

const Follow = db.Follow;

const hasFollowed = async (req, res) => {
    try{
        const { UserID, FollowedUserID} = req.body;      
        const createdAt = Date.now();
        // initialize models database
        const Followed = new Follow({
            FollowedUserID,
            UserID,           
            createdAt
        });

            await Followed.save();
            res.json(Followed);
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