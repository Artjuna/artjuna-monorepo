const AccountController = require('../controller/AccountController');
const express = require('express');
//router
const router = require('express').Router();

//use routers
router.use(express.json({ limit: '50mb' }));
router.use(express.urlencoded({ limit: '50mb', extended: true }));

//use routers
router.post('/addAccount', AccountController.addAccount);
router.get('/getAllAccount', AccountController.getAllAccount);
router.get('/getAccountByUserID/:UserID', AccountController.getAccountByUserID);
router.get('/getAccountFilter', AccountController.getAccountFilter);
router.put('/updateAccount', AccountController.updateAccount)

module.exports = router;


