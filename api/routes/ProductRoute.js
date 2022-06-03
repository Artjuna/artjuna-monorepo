const ProductController = require('../controller/ProductController');
const express = require('express');
const path = require('path');
const multer = require('multer');
const DateTime = require('node-datetime/src/datetime');
//router
const router = require('express').Router();

const storage = multer.diskStorage({
    destination: (req, file, cb) => {
        cb(null, 'images')
    },
    filename: (req, file, cb) => {
        cb(null,  file.fieldname + '-' + Date.now())
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
router.post('/addProduct', upload.single('Image'), ProductController.addProduct);
router.get('/getAllProduct', ProductController.getAllProduct);
router.get('/getProductFilter', ProductController.getProductFilter);
router.get('/getAllProductCategory', ProductController.getProductCategory);
router.get('/getProductByUserID/:ProductID', ProductController.getProductByProductID);
router.post('/seen', ProductController.hasSeen);
router.put('/updateProduct', ProductController.updateProduct);

module.exports = router;


