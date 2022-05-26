const FollowController = require('../controller/FollowController');

//router
const router = require('express').Router();

//use routers
router.post('/followed', FollowController.hasFollowed);

module.exports = router;


