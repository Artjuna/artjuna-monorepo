const LikeController = require('../controller/LikeController');
const express = require('express');
//router
const router = require('express').Router();

//use routers
router.use(express.json({ limit: '50mb' }));
router.use(express.urlencoded({ limit: '50mb', extended: true }));

//use routers
router.post('/liked', LikeController.hasLiked);

module.exports = router;


