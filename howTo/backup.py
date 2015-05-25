#!/usr/bin/env python
import ConfigParser
import os
import time
import ftplib

# On Debian, /etc/mysql/debian.cnf contains 'root' a like login and password.
config = ConfigParser.ConfigParser()
config.read("/etc/mysql/debian.cnf")
username = config.get('client', 'user')
password = config.get('client', 'password')
hostname = config.get('client', 'host')

filestamp = time.strftime('%Y-%m-%d')

# Init ftp
ftpHost = "********"
ftpUser = "********"
ftpPassword = "********"
remoteDirectory = "********"

ftp = ftplib.FTP(ftpHost)
ftp.login(ftpUser, ftpPassword)
ftp.cwd(remoteDirectory)

# Change directories - create if it doesn't exist
def chdir(dir): 
    if directory_exists(dir) is False: # (or negate, whatever you prefer for readability)
        ftp.mkd(dir)
    ftp.cwd(dir)

# Check if directory exists (in current location)
def directory_exists(dir):
    filelist = []
    ftp.retrlines('LIST',filelist.append)
    for f in filelist:
        if f.split()[-1] == dir and f.upper().startswith('D'):
            return True
    return False

# Create good directories in order to store files
chdir(filestamp[:-3])
chdir(filestamp)

# Get a list of databases with :
database_list_command="mysql -u %s -p%s -h %s --silent -N -e 'show databases'" % (username, password, hostname)
for database in os.popen(database_list_command).readlines():
    database = database.strip()
    if database != 'help_parebrise_test' and database != "help_parebrise":
        continue

    # Creation of the monthly backup folder 
    newpath = r'/home/vladk/backups/mysql/%s' % (filestamp[:-3])
    if not os.path.exists(newpath) : os.makedirs(newpath)

    # Creation of the daily backup folder 
    newpath = r'/home/vladk/backups/mysql/%s/%s' % (filestamp[:-3], filestamp)
    if not os.path.exists(newpath) : os.makedirs(newpath)

    filename = "/home/vladk/backups/mysql/%s/%s/%s-%s.sql" % (filestamp[:-3], filestamp, database, filestamp)
    os.popen("mysqldump -u %s -p%s -h %s -e --opt -c %s | gzip -c > %s.gz" % (username, password, hostname, database, filename))

    # Store on the backup ftp
    ftpFilename = "%s-%s.sql.gz" % (database, filestamp)
    filename = filename + ".gz"   

    with open(filename, "rb") as file:
    ftp.storbinary("STOR " + ftpFilename, file)

ftp.quit();
