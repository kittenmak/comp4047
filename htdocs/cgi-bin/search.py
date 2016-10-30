class_dir = '../bin'
search_engine = 'SearchServer'

# enable cgi traceback manager
import cgitb
cgitb.enable()

# get form data
import cgi
form = cgi.FieldStorage()
if 'query' in form:
    query = form['query'].value
else:
    query = ''

# change working dir
import os
os.chdir(class_dir)

# invoke java search engine
os.system('java ' + search_engine + ' ' + query)
