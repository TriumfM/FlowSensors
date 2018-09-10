from flask import Flask
from flask import Response, request
import json
import sys


#import file from main path


sys.path.insert(0, '/home/pi/Desktop/Flow_sensor')
import function
import db_connection


app = Flask(__name__)


@app.route('/')
def index():   
        return 'Flow Sensor API! '


#Get sensor data
@app.route('/sensorData/<sensorId>', methods=['GET'])
def getSensorData(sensorId):
        sensor = function.getSensor(int(sensorId))
        sensorData = json.loads(sensor)
        sensorName = sensorData['name']
        
        
        fileName = sensorName + sensorId
        sensorData_ = function.file("/home/pi/Desktop/Flow_sensor/json/" + fileName + ".json")
        return sensorData_ 

#Add new Sensor
@app.route('/sensors', methods=['POST'])
def addSensor():

        sensor_ = request.get_json(silent=True)

        json_str = json.dumps(sensor_)

        resp = json.loads(json_str)
                
        sensor = function.addSensor(
                resp['name'],
                resp['type'],
                resp['diameter'],
                resp['description'],
                int(resp['input']),
                int(resp['output']),
                int(resp['status']),
                )

        return sensor
        

#Find by Id Sensor
@app.route('/sensors/<sensorId>', methods=['GET'])
def getSensor(sensorId):
       
        sensor_ = function.getSensor(int(sensorId))

        return sensor_


#Find All Sensors
@app.route('/sensors', methods=['GET'])
def getSensors():
        sensors = function.getSensors()

        return sensors


#Update by Id Sensor
@app.route('/sensors/<sensorId>', methods=['PUT'])
def updateSensors(sensorId):
        sensor_ = request.get_json(silent=True)

        json_str = json.dumps(sensor_)

        resp = json.loads(json_str)
                
        sensor = function.updateSensor(
                int(resp['inputPin']),
                resp['name'],
                resp['type'],
                resp['diameter'],
                resp['description']
                )

        return getSensor(sensorId)


#Delete Sensor
@app.route('/sensors/<sensorId>', methods=['DELETE'])
def deleteSensors(sensorId):
        sensors = function.deleteSensor(int(sensorId))

        return sensors


#Find by Pin number
@app.route('/pinSensor/<pin>', methods=['GET'])
def pinSensorId(pin):
        sensor = function.getSensor(int(pin))

        return sensor

#Login user
@app.route('/login', methods=['POST'])
def loginUser():
        user_ = request.get_json(silent=True)

        json_str = json.dumps(user_)

        resp = json.loads(json_str)
                
        user = function.loginUser(
                resp['email'],
                resp['password'])

        return user

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



if __name__ == '__main__':
        app.run(debug=True, host='192.168.2.200')

