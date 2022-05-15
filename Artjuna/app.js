const express = require('express');
const { path } = require('express/lib/application');
const app = express();
const { sequelize } = require('./model');
require('dotenv').config();
const swaggerUI = require('swagger-ui-express');
const swaggerJsDoc = require('swagger-jsdoc');

const options = {
    definition: {
        openapi: "3.0.0",
        info: {
            title: "Library API",
            version: "1.0.0",
            description: "Artjuna API"
        },
        servers: [
            {
                url: "http://localhost:5000"
            }
        ]
    },
    apis: ["./routes/Artjuna.js"]
}

const specs = swaggerJsDoc(options);

app.use("/api-docs", swaggerUI.serve, swaggerUI.setup(specs))

sequelize.authenticate().then(() => console.log("respon nodejs berhasil"));
app.use(express.urlencoded({extended: true}));
app.use(express.json())

//routers
const router = require('./routes/Artjuna')
app.use('/api', router)

app.get("/", (req, res) => res.send("respoin nodeJS berhasil"));

app.listen(5000, () => console.log("port berjalan di 5000"));