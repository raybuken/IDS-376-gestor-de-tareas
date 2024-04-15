const { DataTypes } = require('sequelize')

module.exports = (sequelize) => {
  const TaskStatus = sequelize.define('task_status', {
    id: {
      type: DataTypes.UUID,
      defaultValue: DataTypes.UUIDV1,
      primaryKey: true
    },
    name: {
        type: DataTypes.STRING,
        allowNull: false,
    }
  })

  return TaskStatus
}