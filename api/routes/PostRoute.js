const PostController = require('../controller/PostController');

//router
const router = require('express').Router();

//use routers
router.post('/addPost', PostController.addPost);
router.get('/getPost', PostController.getPost);

module.exports = router;


