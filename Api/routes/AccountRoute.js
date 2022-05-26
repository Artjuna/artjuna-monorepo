const AccountController = require('../controller/AccountController');
//router
const router = require('express').Router();

//use routers
router.post('/addAccount', AccountController.addAccount);
router.get('/getAccount', AccountController.getAccount);
router.get('/getMyAccount', AccountController.getMyAccount);
router.put('/updateMyAccount', AccountController.updateMyAccount);
module.exports = router;


