const express = require('express')
const router = express.Router();
const bcrypt = require('bcryptjs')
const jwt = require('jsonwebtoken')
const {User} = require('../models/index')

router.post('/register', async(req, res) => {
    try{
        const {email, password, fullName} = req.body
        const user = await User.create({email, password, fullName})
        res.status(201).json(user)
    } catch(err){
        res.status(500).json({
            message: "Interval server error"
        })
    }
})

router.post('/login', async (req, res) => {
    try {
      const { email, password } = req.body;
      const user = await User.findOne({ where: { email } });
      if (!user) {
        return res.status(404).json({ success: false, message: 'User not found' });
      }
      const isPasswordValid = await bcrypt.compare(password, user.password);
      if (!isPasswordValid) {
        return res.status(401).json({ success: false, message: 'Invalid password' });
      }
      const token = jwt.sign({ id: user.id, name: user.fullName }, 'secretkey', { expiresIn: '24h' });
      res.status(200).json({ success: true, value: token });
    } catch (error) {
      console.error(error);
      res.status(500).json({ success: false, message: 'Internal server error' });
    }
});

router.post('/token', async(req, res) => {
  const {id} = req.body;

  try{
    const verifiedToken = jwt.verify(id, "secretkey")
    console.log(verifiedToken)

      return res.status(200).json({
        success: true,
        value: id
      })

  }catch(err){
    console.log(err)
    return res.status(403).json({
      success: false,
      message: "Invalid token"
    })
  }



})



module.exports = router;