#import packet
import db_connection
import json
import sensor__
import RPi.GPIO as GPIO
import time, os 
from flask import Response

#Sensor add 
def addSensor(name, type, diameter, description, input, output, status):
    
    cur = db_connection.conn.cursor()
    cur.execute("INSERT INTO sen_sensors (name, type, diameter, description, input, output, status) VALUES ( %s, %s, %s, %s, %s, %s, %s)", (name, type, diameter, description, input, output, status))

    sensor = getSensor(cur.lastrowid)

    createJsonFile(cur.lastrowid)
    return Response( sensor, status=200, mimetype='application/json')

#Sensor find by id 
def getSensor(sensorID):
    sensor_ = None
    cur = db_connection.conn.cursor()
    cur.execute("Select * from sen_sensors where sid='%s'", (sensorID))
    sensor_ = cur.fetchone()

    if(sensor_ == None):
        return "404 Not Found"
    else:
        sensor = {}
        
        sensor['sid'] = sensor_[0]
        sensor['name'] = sensor_[1]
        sensor['type'] = sensor_[2]
        sensor['diameter'] = sensor_[3]
        sensor['description'] = sensor_[4]
        sensor['input'] = sensor_[5]
        sensor['output'] = sensor_[6]
        sensor['status'] = sensor_[7]
        sensor['state'] = sensor_[8]
        
        json_sensor = json.dumps(sensor)
        return json_sensor

#Sensor find by pin input data
def getSensorId(pinGPIO):
    
    cur = db_connection.conn.cursor()

    cur.execute("Select * from sen_sensors where pin='%s'", (pinGPIO))
    sensor_ = cur.fetchone()

    if(sensor_ == None):
        return Response( "404 Not Found", status=404, mimetype='application/json')
    else:
        sensor = {}
        
        sensor['id'] = sensor_[0]
        sensor['name'] = sensor_[1]
        sensor['type'] = sensor_[2]
        sensor['diameter'] = sensor_[3]
        sensor['description'] = sensor_[4]
        sensor['inputPin'] = sensor_[5]

        json_sensor = json.dumps(sensor)         
        return Response( json_sensor, status=200, mimetype='application/json')

#Sensor find all    
def getSensors():
    cur = db_connection.conn.cursor()

    cur.execute("Select * from sen_sensors as ss ")

    row_headers=[x[0] for x in cur.description]
    rv = cur.fetchall()
    sensors_ = []
    for result in rv:
        sensors_.append(dict(zip(row_headers,result)))

    sensors = json.dumps(sensors_, indent=5, sort_keys=True, default=str)

    return Response( sensors, status=200, mimetype='application/json')



#Sensor find all    
def getSensorsState():
    cur = db_connection.conn.cursor()

    cur.execute("Select sid,state,input,output,status from sen_sensors as ss ")

    row_headers=[x[0] for x in cur.description]
    rv = cur.fetchall()
    sensors_ = []
    for result in rv:
        sensors_.append(dict(zip(row_headers,result)))

    sensors = json.dumps(sensors_, indent=5, sort_keys=True, default=str)

    return sensors


#Sensor delete
def deleteSensor(sensorId):
    cur = db_connection.conn.cursor()

    if(getSensor(sensorId) != "404 Not Found"):
        cur.execute("Delete from sen_sensors where sid='%s'", (sensorId))
        sensor_ = cur.fetchone()
        return Response("oki", status=200)
    else:
        return Response("404 Not Found", status=404, mimetype='application/json')


#User update by id
def updateUser( uid, first_name, last_name, email, password):
    cur = db_connection.conn.cursor()
    cur.execute("UPDATE usr_users set first_name= ''%s'', last_name= ''%s'', email= ''%s'', password= ''%s'' where uid= '%s'", ( first_name, last_name, email, password, uid) )

    user = {}
    user['first_name'] = first_name
    user['last_name'] = last_name
    user['email'] = email
    user['password'] = password

    
    json_user = json.dumps(user)
    return Response( json_user, status=200, mimetype='application/json')

#User add 
def addUser( first_name, last_name, email, password):
    cur = db_connection.conn.cursor()
    cur.execute("INSERT INTO usr_users ( first_name, last_name, email, password) VALUES ( %s, %s, %s, %s)", ( first_name, last_name, email, password))

    user = getUser(cur.lastrowid)

    json_user = json.dumps(user)
    
    return Response( user, status=200, mimetype='application/json')



#User find by id
def getUser(userId):
    
    cur = db_connection.conn.cursor()
    
    cur.execute("Select * from usr_users where uid='%s'", (userId))
    user_ = cur.fetchone()

    if(user_ == None):
        return "404 Not Found"
    else:
        user = {}
        user['id'] = user_[0]
        user['first_name'] = user_[1]
        user['last_name'] = user_[2]
        user['email'] = user_[3]
    
        json_user = json.dumps(user)
        return json_user

#User find all
def getUsers():
    cur = db_connection.conn.cursor()

    cur.execute("Select * from usr_users")

    row_headers=[x[0] for x in cur.description]
    rv = cur.fetchall()
    users_ = []
    for result in rv:
        users_.append(dict(zip(row_headers,result)))

    users = json.dumps(users_, indent=4, sort_keys=True, default=str)

    return Response( users, status=200, mimetype='application/json')

#User delete
def deleteUser(userId):
    cur = db_connection.conn.cursor()
    if(getUser(userId) != "404 Not Found"):
        cur.execute("Delete from usr_users where uid='%s'", (userId))
        user_ = cur.fetchone()
        return Response("oki", status=200, mimetype='application/json')
    else:
        print( Response("404 Not Found", status=404, mimetype='application/json'))


#Login user
def loginUser(email, password):
    cur = db_connection.conn.cursor()
    
    cur.execute("Select uid from usr_users where email=%s and password=%s", (email, password))
    user_ = cur.fetchone()

    if(user_ == None):
        return Response("oki", status=200, mimetype='application/json')
    else:
        return Response("okiii", status=200, mimetype='application/json')
    

#Add data of sensor 
def InserDataSensor( sensorID, total, literPerMin, time ) :
    cur = db_connection.conn.cursor()
    
    cur.execute("INSERT INTO sen_data ( sid, total, literMin, dataTime) VALUES ( %s , %s , %s , %s)", ( sensorID, total, literPerMin,time ))
    createJsonFile(sensorID)


def getDataSensor(sensorID):
    cur = db_connection.conn.cursor()

    cur.execute("Select * from sen_data where sid = '%s' Order by did DESC", (sensorID))

    row_headers=[x[0] for x in cur.description]
    rv = cur.fetchall()
    json_data = []
    for result in rv:
        json_data.append(dict(zip(row_headers,result)))

    sensorData = json.dumps( json_data, indent=4, sort_keys=True, default=str)
    return sensorData

def createJsonFile(sId):
    sensor = getSensor(sId)
    sensorData =json.loads(sensor)
    fileName = sensorData['name']
    sensorId = sensorData['sid']
    path = "/home/pi/Desktop/Flow_sensor/json/" + fileName + str(sensorId) + ".json"
    with open( path , "w") as f:
        f.write(getDataSensor(sId))
        

def file(filee):
    if os.path.exists(filee):
        fp = open (filee, "r")
        content = fp.read()
        fp.close()
        
        return(content)

