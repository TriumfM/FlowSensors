#import packet
from flask import Flask
from flask import request
import json
import sys
import RPi.GPIO as GPIO

#import file from main path
sys.path.insert(0, '/home/pi/Desktop/Flow_sensor')
import function
import sensor

#Init flask 
app = Flask(__name__)

#Add new Sensor
@app.route('/sensors', methods=['POST'])
def addSensor():

        sensor_ = request.get_json(silent=True)

        json_str = json.dumps(sensor_)

        resp = json.loads(json_str)
                
        sensor = function.addSensor(
                int(resp['inputPin']),
                resp['name'],
                resp['type'],
                resp['diameter'],
                resp['description']
                )

        return sensor
        

#Find by Id Sensor
@app.route('/sensors/<sensorId>', methods=['GET'])
def getSensor(sensorId):
        sensor = function.getSensor(int(sensorId))

        return sensor


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
