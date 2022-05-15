const ProductController = require('../controller/ProductController');
const AccountController = require('../controller/AccountController');
const swaggerJsdoc = require('swagger-jsdoc');
//router
const router = require('express').Router();

//use routers
router.post('/addProduct', ProductController.addProduct);
router.get('/getProduct', ProductController.getProduct);

router.post('/addAccount', AccountController.addAccount);
router.post('/getAccount', AccountController.getAccount);
module.exports = router;

/**
 * @swagger
 * components:
 *  schemas:
 *      
 */