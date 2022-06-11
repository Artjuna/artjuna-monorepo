const db = require('../model');
require('dotenv').config();
const {unlink} = require('node:fs/promises') 

//create main model
const Post = db.Post;
const Like = db.Like;
const Account = db.Accounts;

const addPost = async (req, res) => {
    try{
        const { UserID, PostName, Caption} = req.body;    
        let fileUrl = req.file.path.replace(/\\/g, "/").substring("PostImages".length);
        let Image = fileUrl;  
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

            const fullName = await Account.findOne({raw: true, where:{
                UserID : element.UserID
            },
            attributes: 
            ['FullName']})
            element.FullName = fullName.FullName;
        }
        res.json(getAllPost);
    }
    catch (err)
    {
        console.error(err.message);
        res.status(500).send("server error");
    }
}

const updatePost = async (req, res) => {
    try{
        

        let { PostID, PostName, Caption} = req.body;
        let fileUrl = req.file.path.replace(/\\/g, "/").substring("PostImages".length);
        let Image = fileUrl; 
        const getMyPost = await Post.findOne({
            where: {    
                PostID: PostID
            },
            raw: true            
        });       

        let imagePath = getMyPost.Image;
        var List = {                       
            PostName: PostName,
            Caption: Caption,
            Image: Image,
        }

        for (var key of Object.keys(List))
        {
            if (List[key] == undefined)
            {
                delete List[key]; 
            }
        }

        await Post.update(List, {where:{
          PostID: PostID
        }});

        await unlink('PostImages'+imagePath);
        console.log('successfully deleted' + 'PostImages' + imagePath);  
        res.status(200).send("Update data success");
        
    }
    catch (err)
    {
        console.error(err.message);
        res.status(500).send(err.message);
        
    }
    
}

const getPostFilter = async (req, res) => {
    let PostID = req.query.PostID;
    let UserID = req.query.UserID;
    let PostName = req.query.PostName;
    
    var List = {
        PostID: PostID,
        UserID: UserID,
        PostName: PostName        
    }

    for (var key of Object.keys(List))
    {
        if (List[key] == undefined)
        {
            delete List[key]; 
        }
    }
    
    const getPost = await Post.findAll({raw: true,
        where: List
    });

    res.json(getPost);
}

module.exports = {
    addPost,
    getPost,
    updatePost,
    getPostFilter
}