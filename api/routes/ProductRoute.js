const ProductController = require('../controller/ProductController');
const express = require('express');
//router
const router = require('express').Router();

//use routers
router.use(express.json({ limit: '50mb' }));
router.use(express.urlencoded({ limit: '50mb', extended: true }));
router.post('/addProduct', ProductController.addProduct);
router.get('/getAllProduct', ProductController.getAllProduct);
router.get('/getProductByUserID', ProductController.getProductByUserID);
router.post('/seen', ProductController.hasSeen);

module.exports = router;


