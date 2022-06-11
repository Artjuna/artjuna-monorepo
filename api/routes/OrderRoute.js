const OrderController = require('../controller/OrderController');
const express = require('express');
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

//use routers
router.post('/addOrder', upload.single('Image'), OrderController.addOrder);
router.get('/getOrderFilter', OrderController.getOrderFilter)

module.exports = router;


