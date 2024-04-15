const sequelize = require('./models').sequelize

const updateDatabase = async() => {
    try{
        await sequelize.sync({alter: true}).then(() => {
            console.log("database updated!")
        })
        
    }catch(e){
        console.error('Unexpected error updading the database ' + e)
    }
}

updateDatabase()