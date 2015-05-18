#!/usr/bin/env python
import ConfigParser
import os
import time

# On Debian, /etc/mysql/debian.cnf contains 'root' a like login and password.
config = ConfigParser.ConfigParser()
config.read("/etc/mysql/debian.cnf")
username = config.get('client', 'user')
password = config.get('client', 'password')
hostname = config.get('client', 'host')

filestamp = time.strftime('%Y-%m-%d')

# Get a list of databases with :
database_list_command="mysql -u %s -p%s -h %s --silent -N -e 'show databases'" % (username, password, hostname)
for database in os.popen(database_list_command).readlines():
    database = database.strip()
    if database == 'information_schema':
        continue
    if database == 'performance_schema':
        continue
    if database == 'mysql':
        continue
    if database == 'phpmyadmin':
        continue

    # Creation of the daily backup folder 
    newpath = r'/home/vladk/backups/mysql/%s' % (filestamp)
    if not os.path.exists(newpath) : os.makedirs(newpath)

    filename = "/home/vladk/backups/mysql/%s/%s-%s.sql" % (filestamp, database, filestamp)
    os.popen("mysqldump -u %s -p%s -h %s -e --opt -c %s | gzip -c > %s.gz" % (username, password, hostname, database, filename))
