const jwt = require('jsonwebtoken');

const authenticateUser = (req, res, next) => {
  const token = req.header('authorization').replace("Bearer ", "");
  if (!token) {
    return res.status(401).json({ success: false, type: "INVALID_TOKEN", message: 'Unauthorized' });
  }

  try {
    const decoded = jwt.verify(token, 'secretkey');
    req.user = decoded;
    next();
  } catch (error) {
    console.error(error);
    res.status(403).json({ success: false, type: "INVALID_TOKEN", message: 'Invalid token' });
  }
};

module.exports = authenticateUser;