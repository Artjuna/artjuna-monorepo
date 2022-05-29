const FollowController = require('../controller/FollowController');
const express = require('express');
//router
const router = require('express').Router();

//use routers
router.use(express.json({ limit: '50mb' }));
router.use(express.urlencoded({ limit: '50mb', extended: true }));

//use routers
router.post('/followers', FollowController.hasFollowed);

module.exports = router;


