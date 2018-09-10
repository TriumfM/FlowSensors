#import library and file sensor and function
import sensor__
import function
import RPi.GPIO as GPIO
import time, json

GPIO.setwarnings(False)

while True:
    sensor__.sensorData( GPIO.BOARD, 13, 16, 15, 4, 1)

    
    
    
