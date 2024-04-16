const {DATABASE_NAME, SERVER_CONNECTION_USERNAME, SERVER_CONNECTION_PASSWORD, SERVER_HOST, SERVER_PORT: BD_SERVER_PORT} = process.env
const Sequelize = require('sequelize');
const UserModel = require('./User');
const TaskModel = require('./Task')
const TaskStatusModel = require('./TaskStatus')

const sequelize = new Sequelize(DATABASE_NAME, SERVER_CONNECTION_USERNAME, SERVER_CONNECTION_PASSWORD,{
  host: SERVER_HOST || "localhost",
  dialect: "mssql",
  port: BD_SERVER_PORT,
  options: {
    instanceName: 'SQLEXPRESS',
  }});

const User = UserModel(sequelize)
const Task = TaskModel(sequelize)
const TaskStatus = TaskStatusModel(sequelize)

//relationship
User.hasMany(Task, {
    foreignKey: 'userId'
})
Task.belongsTo(User)

Task.hasOne(TaskStatus)
TaskStatus.belongsTo(Task)

module.exports = {
    sequelize,
    User, 
    Task
}