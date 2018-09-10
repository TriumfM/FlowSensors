#import packet
from flask import Flask
from flask import request
import json
import sys

#import file from main path
sys.path.insert(0, '/home/pi/Desktop/Flow_sensor')
import function
import sensor

#Init flask 
app = Flask(__name__)

#Add new User
@app.route('/users', methods=['POST'])
def addUser():
        user_ = request.get_json(silent=True)

        json_str = json.dumps(user_)

        resp = json.loads(json_str)
                
        user = function.addUser(
                resp['first_name'],
                resp['last_name'],
                resp['email'],
                resp['password']
                )

        return user


#Find by Id User
@app.route('/users/<userId>', methods=['GET'])
def getUser(userId):
        user = function.getUser(int(userId))

        return user


#Find all Users
@app.route('/users', methods=['GET'])
def getUsers():
        users = function.getUsers()

        return users


#Update by Id User
@app.route('/users/<userId>', methods=['PUT'])
def updateUser(userId):

        user_ = request.get_json(silent=True)

        json_str = json.dumps(user_)

        resp = json.loads(json_str)
                
        user = function.updateUser(
                int(userId),
                resp['first_name'],
                resp['last_name'],
                resp['email'],
                resp['password']
                )

        return getUser(userId)


#Delete User
@app.route('/users/<userId>', methods=['DELETE'])
def deleteUser(userId):
        user = function.deleteUser(int(userId))

        return user
