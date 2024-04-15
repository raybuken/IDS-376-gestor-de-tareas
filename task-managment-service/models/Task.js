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
      defaultValue: "1"
    },

  })

  return Task
}