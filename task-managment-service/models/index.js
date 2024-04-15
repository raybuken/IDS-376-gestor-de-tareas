const {DATABASE_NAME, SERVER_CONNECTION_USERNAME, SERVER_CONNECTION_PASSWORD} = process.env
const Sequelize = require('sequelize');
const UserModel = require('./User');
const TaskModel = require('./Task')
const TaskStatusModel = require('./TaskStatus')

const sequelize = new Sequelize(DATABASE_NAME, SERVER_CONNECTION_USERNAME, SERVER_CONNECTION_PASSWORD,{
  host: "localhost",
  dialect: "mssql",
  port: 3500,
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