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
module.exports = {
    hasLiked
}