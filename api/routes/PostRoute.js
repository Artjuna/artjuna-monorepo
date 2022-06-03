const PostController = require('../controller/PostController');
const express = require('express');
const path = require('path');
const multer = require('multer');

//router
const router = require('express').Router();

const storage = multer.diskStorage({
    destination: (req, file, cb) => {
        cb(null, 'PostImages')
    },
    filename: (req, file, cb) => {
        cb(null,   Date.now() + '-' + file.originalname )
    }
});
const upload = multer({storage: storage,
    fileFilter: (req, file, cb) => {
        const fileTypes = /jpeg|jpg|png|gif/
        const mimeType = fileTypes.test(file.mimetype)  ;
        const extname = fileTypes.test(path.extname(file.originalname));
        if(mimeType && extname) {
            return cb(null, true)
        }
        cb('Give proper files formate to upload')
    }
});

//use routers
router.use(express.json({ limit: '50mb' }));
router.use(express.urlencoded({ limit: '50mb', extended: true }));
//router

//use routers
router.post('/addPost', upload.single('Image'), PostController.addPost);
router.get('/getPost', PostController.getPost);
router.put('/updatePost', upload.single('Image'), PostController.updatePost);

module.exports = router;


