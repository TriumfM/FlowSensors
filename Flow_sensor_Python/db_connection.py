import pymysql

print ("Using pymysql…")

conn = pymysql.connect(
	host = 'localhost',
	port = 3306,
	user = 'USER#',
	passwd = 'PASS#',
	db = 'Water_Flow_Sensor',
	autocommit = True
)
