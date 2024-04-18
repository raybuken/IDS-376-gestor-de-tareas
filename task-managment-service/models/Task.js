const { DataTypes } = require('sequelize')

module.exports = (sequelize) => {
  const Task = sequelize.define('task', {
    id: {
      type: DataTypes.UUID,
      defaultValue: DataTypes.UUIDV1,
      primaryKey: true
    },
    title: {
      type: DataTypes.STRING
    },
    description: {
      type: DataTypes.STRING
    },
    statusId: {
      type: DataTypes.STRING,
    },
    date: {
      type: DataTypes.DATE
    },

  })

  return Task
}