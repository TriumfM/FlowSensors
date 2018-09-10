import RPi.GPIO as GPIO
import time, sys

GPIO.setmode(GPIO.BOARD)
inpt=13
GPIO.setup(inpt, GPIO.IN)

rate_cnt = 0
tot_cnt = 0

time_new = 0.0
time_start = 0.0
time_end = 0.0

gpio_last = 0
pulses = 0
constant = 1.79

print('Water Flow - Approximate')
print('Control C to exit')

time_zero = time.time()
while True:
	rate_cnt = 0
	pulses = 0
	time_start = time.time()

	print(GPIO.input(inpt))
	
	
			
GPIO.cleanup()
print(' Done ')
