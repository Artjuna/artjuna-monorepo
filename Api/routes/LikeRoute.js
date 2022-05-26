const LikeController = require('../controller/LikeController');

//router
const router = require('express').Router();

//use routers
router.post('/liked', LikeController.hasLiked);

module.exports = router;


