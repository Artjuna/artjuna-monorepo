const ProductController = require('../controller/ProductController');

//router
const router = require('express').Router();

//use routers
router.post('/addProduct', ProductController.addProduct);
router.get('/getAllProduct', ProductController.getAllProduct);
router.get('/getProductByUserID', ProductController.getProductByUserID);
router.post('/seen', ProductController.hasSeen);

module.exports = router;


