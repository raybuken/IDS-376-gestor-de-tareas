const express = require('express')
const cors = require('cors')
const bodyParser = require('body-parser');
const userRoutes = require('./routes/userRoutes')
const taskRoutes = require('./routes/taskRoutes')
const app = express()
const sequelize = require('./models').sequelize

const PORT = process.env?.PORT || 3000

app.use(cors())

app.use(bodyParser.json())
app.use(bodyParser.urlencoded({extended: true}))

app.use('/api/users', userRoutes);
app.use('/api/task', taskRoutes)

sequelize.sync().then(() => {
    app.listen(PORT, () => {
        console.log('Port on ' + PORT)
    })
})
