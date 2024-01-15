const express = require('express')
const path = require('path')
const app = express()

const cors = require('cors')

const iotClient = require('./IoTClient.js')

const iotService = require('./IoTService.js')(iotClient)

const iotController = require('./IoTController.js')(
  express.Router(),
  iotService
)

app.use(express.json())
app.use(express.static(path.join(__dirname, '/public')))
app.use(express.urlencoded({ extended: false }))


app.use(
  cors({
    /*    origin: ['http://localhost:3000', 'https://www.google.com/']  */
    origin: '*',
    methods: ['GET', 'POST', 'DELETE', 'UPDATE', 'PUT', 'PATCH']
  }),
  iotController
)

app.set('views', path.join(__dirname, 'views'))

app.listen(8001, () => console.log('Listening...'))
