const express = require('express');
const path = require('path');
const app = express();
const { sequelize } = require('./model');
require('dotenv').config();
const swaggerUI = require('swagger-ui-express');

// const specs = swaggerJsDoc(options);
const specs = require('./swagger.json')
app.use("/api-docs", swaggerUI.serve, swaggerUI.setup(specs))

sequelize.authenticate().then(() => console.log("respon nodejs berhasil"));
app.use(express.urlencoded({extended: true}));
app.use(express.json());

app.get("/", (req, res) => {
    res.sendFile(path.join(__dirname) + '/views/index.html');
})

//routers
const ProductRoute = require('./routes/ProductRoute');
app.use('/Product', ProductRoute);

const AccountRoute = require('./routes/AccountRoute');
app.use('/Account', AccountRoute);

const LikeRoute = require('./routes/LikeRoute');
app.use('/Like', LikeRoute);

const PostRoute = require('./routes/PostRoute');
app.use('/Post', PostRoute);

const FollowRoute = require('./routes/FollowRoute.js')
app.use('/Follow', FollowRoute);



app.listen(5000, () => console.log("port berjalan di 5000"));