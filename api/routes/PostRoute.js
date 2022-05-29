const PostController = require('../controller/PostController');
const express = require('express');
//router
const router = require('express').Router();

//use routers
router.use(express.json({ limit: '50mb' }));
router.use(express.urlencoded({ limit: '50mb', extended: true }));
//router

//use routers
router.post('/addPost', PostController.addPost);
router.get('/getPost', PostController.getPost);

module.exports = router;


