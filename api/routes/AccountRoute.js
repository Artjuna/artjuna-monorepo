const AccountController = require('../controller/AccountController');
//router
const router = require('express').Router();

//use routers
router.post('/addAccount', AccountController.addAccount);
router.get('/getAllAccount', AccountController.getAllAccount);
router.get('/getAccountByUserID', AccountController.getAccountByUserID);

module.exports = router;


