const ProductController = require('../controller/ProductController.js');

//router
const router = require('express').Router();

//use routers
router.post('/addProduct', ProductController.addProduct);
router.get('/getProduct', ProductController.getProduct);
router.get('/getMyProduct', ProductController.getMyProduct);

module.exports = router;


