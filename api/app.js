const express = require('express');
const app = express();
const { sequelize } = require('./model');
require('dotenv').config();
const swaggerUI = require('swagger-ui-express');
const bodyParser = require('body-parser')
// const specs = require('./swagger.json')
// app.use("/api-docs", swaggerUI.serve, swaggerUI.setup(specs))

// sequelize.authenticate().then(() => console.log("respon nodejs berhasil"));
var jsonParser = bodyParser.json({limit:1024*1024*10, type:'application/json'}); 
var urlencodedParser = bodyParser.urlencoded({ extended:true,limit:1024*1024*10,type:'application/x-www-form-urlencoded' });
app.use(urlencodedParser);
app.use(jsonParser);

//routers
const ProductRoute = require('./routes/ProductRoute');
app.use('/Product', ProductRoute);

const AccountRoute = require('./routes/AccountRoute');
app.use('/Account', AccountRoute);

const LikeRoute = require('./routes/LikeRoute');
app.use('/Like', LikeRoute);

const PostRoute = require('./routes/PostRoute');
app.use('/Post', PostRoute);

const FollowRoute = require('./routes/FollowRoute');
app.use('/Follow', FollowRoute);

app.listen(process.env.PORT, () => console.log("port berjalan di " + `${process.env.PORT}`));