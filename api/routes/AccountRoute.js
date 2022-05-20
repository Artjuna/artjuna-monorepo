const AccountController = require('../controller/AccountController');
//router
const router = require('express').Router();

//use routers
router.post('/addAccount', AccountController.addAccount);
router.get('/getAccount', AccountController.getAccount);

module.exports = router;


