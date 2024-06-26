const express = require('express')
const router = express.Router();
const {Task} = require('../models');
const authenticateUser = require('../middlewares/authMiddleware');
const jwt = require('jsonwebtoken');
const dateHelpers = require('../helpers/dateHelpers')

router.use(authenticateUser)

router.get('/', async (req, res) => {
    const token = req.header('authorization').replace("Bearer ", "");
    const decoded = jwt.decode(token, "secretkey")
    const {id} = decoded
    try {
      const tasks = await Task.findAll({ where: { userId: id } });
      res.json({success: true, items: tasks});
    } catch (error) {
      console.error(error);
      res.status(500).json({success: false, message: 'Internal server error' });
    }
});

router.get('/:id', async(req, res) => {
    const { id } = req.params

    try{
        const task = await Task.findOne({where: {id}})

        if(!task){
            return res.status(404).json({ message: 'Task not found' });
        }

        res.json(task)
    }catch(err){
        res.status(500).json({
            message: "Interval server error"
        })
    }
})

router.post('/', async(req, res) => {
    const token = req.header('authorization').replace("Bearer ", "");
    const decoded = jwt.decode(token, "secretkey")
    const userId = decoded.id
    const {title, description, date} = req.body

    try{
        const utcDate = dateHelpers.parseStringToDate(date)
        const task = await Task.create({title, description, date: utcDate, userId})
        res.status(201).json({success: true, task})
    }catch(err){
        res.status(500).json({
            success: false,
            message: "Interval server error"
        })
    }

})

router.put('/:id', async(req, res) => {
    const { id } = req.params
    const {title, description, date} = req.body

    try{
        const task = await Task.findOne({where: {id}})
        const stringDate = dateHelpers.parseStringToDate(date)

        if(!task){
            return res.status(404).json({success: false, message: 'Task not found' });
        }

        task.title = title
        task.description = description
        task.date = stringDate

        await task.save();

        res.json({
            title: task.title,
            description: task.description,
            date: task.date
        })
    }catch(err){
        res.status(500).json({
            message: "Interval server error",
            success: false,
        })
    }
})

router.delete('/:id', async (req, res) => {
    const token = req.header('authorization').replace("Bearer ", "");
    const decoded = jwt.decode(token, "secretkey")
    const userId = decoded.id
    
    try {
        const { id } = req.params;
        const todo = await Task.findOne({ where: { id, userId } });

        if (!todo) {
            return res.status(404).json({ 
                message: 'Task not found', 
                success: false 
            });
        }
        await todo.destroy();
        res.json({ 
            success: true, 
            message: 'Task deleted successfully' 
        });
    } catch (error) {
        console.error(error);
        res.status(500).json({ 
            success: false, 
            message: 'Internal server error' 
        });
    }
});

module.exports = router