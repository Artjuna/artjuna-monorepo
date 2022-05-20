const ProductController = require('../controller/ProductController');

//router
const router = require('express').Router();

//use routers
router.post('/addProduct', ProductController.addProduct);
router.get('/getProduct', ProductController.getProduct);

module.exports = router;


