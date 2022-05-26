const db = require('../model');
require('dotenv').config();
//create main model
const Post = db.Post;
const Like = db.Like;

const addPost = async (req, res) => {
    try{
        const { UserID, PostName, Category, Caption, Image } = req.body;      
        let getAllPosting = await Post.findAll({raw: true});     
        const json = Object.keys(getAllPosting).length;      
        const ts = new Date();  
        const PostID = `${ts.getFullYear()}PST${json}`;
        const createdAt = ts;
        const Like = 0;
        // initialize models database
        const newPost = new Post({
            PostID,
            UserID,
            PostName,
            Category,
            Caption,
            Image,   
            Like,
            createdAt
        });

            await newPost.save();
            res.json(newPost);
    }
    catch (err)
    {
        console.error(err.message);
        res.status(500).send("server error");
    }
}

const getPost = async (req, res) => {
    try{
        const PostRespone = [];
        const getAllPost = await Post.findAll({raw: true});
        for (const element of getAllPost)
        {
            const like = await Like.findAll({raw: true, where: {
                PostID: element.PostID
            }})
            const sumLike = Object.keys(like).length;
            element.Like = sumLike;
        }
        res.json(getAllPost);
    }
    catch (err)
    {
        console.error(err.message);
        res.status(500).send("server error");
    }
}

const hasLiked = async (req) => {

}



module.exports = {
    addPost,
    getPost
}