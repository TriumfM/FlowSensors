def sensorData( modelBoard, inputPIN , statusPIN, outputPIN, rpt_int, sensorID):
    #import library
    import RPi.GPIO as GPIO
    import time, sys
    import function, json
    from time import gmtime, strftime

    #parameters
    global rate_cnt, tot_cnt, flag
    flag = 0
    rate_cnt = 0
    tot_cnt = 0
    minutes = 0
    constant = 0.00255
    time_new = 0.0
    rpt_init = 10
    flagInt = 0

    #interrupt 
    def Pulse_cnt(inputPIN):
        global rate_cnt, tot_cnt, flag

        if(flag == 0):
            rate_cnt=0
            flag = 1
        if (flag == 1):
            rate_cnt +=1
            tot_cnt +=1
            
            
    #Set model of Board GPIO.BOARD or GPIO.BCM
    GPIO.setmode(modelBoard)

    #Set up pin for sensor
    GPIO.setup(outputPIN, GPIO.OUT) #For LED to show status     
    GPIO.setup(inputPIN, GPIO.IN, pull_up_down=GPIO.PUD_UP) #For data of sensor
    GPIO.setup(statusPIN, GPIO.IN, pull_up_down=GPIO.PUD_DOWN) #To detect the status of sensor
    
                                                    
    print('Water Flow - Approximate',
                    str(time.asctime(time.localtime(time.time()))))
    print('Reports eveny ', rpt_int,' seconds')
    print('Control C to exit')

    
    while True:
        
            if(GPIO.input(statusPIN) == GPIO.HIGH):
                GPIO.output(outputPIN, GPIO.HIGH)
                if(flagInt== 0):     
                    GPIO.add_event_detect(inputPIN, GPIO.FALLING, callback=Pulse_cnt, bouncetime=10)
                    flagInt = 1
            else:
                GPIO.output(outputPIN, GPIO.LOW)
                GPIO.remove_event_detect(inputPIN)
                flagInt = 0         
            
            if(flag == 1 and GPIO.input(statusPIN) == GPIO.HIGH):
                time_new = time.time()+rpt_int
                    
                while time.time() <= time_new:
                    try:
                        None
                        print(GPIO.input(inputPIN), end = ' - ')
                    except KeyboardInterrupt:
                        print('\nCTRL C - Exiting nicely')
                        GPIO.cleanup()
                        print('Done')
                        sys.exit()
                                    
                flag = 2
                    
                LperM = round(((rate_cnt*constant)/(rpt_int/60)),2)
                TotLit = round(tot_cnt * constant,1)
                    
                now = time.asctime(time.localtime(time.time()))

                if(LperM != 0.0):
                    print('\nLiters / min' ,LperM,'(',rpt_int,'second sample)')
                    print('Total Liters ',TotLit)
                    print('Time (min & clock) \t',
                            time.asctime(time.localtime(time.time())),'\n')
                    function.InserDataSensor( sensorID, TotLit, LperM, strftime("%Y-%m-%d %H-%M-%S"))
                    print ('Insert in table!')
                flag = 0
                                         
                            
    GPIO.cleanup()
    print(' Done ')
