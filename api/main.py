from flask import Flask, json, request
import os
import pymysql

app = Flask(__name__)

username = os.environ.get('CLOUD_SQL_USERNAME')
pass = os.environ.get('CLOUD_SQL_PASSWORD')
name = os.environ.get('CLOUD_SQL_DATABASE_NAME')
conn_name = os.environ.get('CLOUD_SQL_CONNECTION_NAME')

def open_connection():
    unix_socket = '/cloudsql/{}'.format(db_connection_name)
    conn = pymysql.connect(user=username, password=pass,unix_socket=unix_socket, db=name,cursorclass=pymysql.cursors.DictCursor)
    return conn

def get_article():
    conn = open_connection()
    cursor = conn.cursor()
    result = cursor.execute('SELECT * FROM article;')
    article = cursor.fetchall()
    got_article = json.dumps(article) if result > 0 else 'No data'
    conn.close()
    return got_article

def add_article(articles):
    conn = open_connection()
    cursor = conn.cursor()
    cursor.execute('INSERT INTO article (images, title, content) VALUES(%s, %s, %s)', (articles["images"], articles["title"], articles["content"]))
    conn.commit()
    conn.close()

@app.route('/api', methods=['POST', 'GET'])
def article():
    if request.method != 'POST':
        return get_article()
    else:
        return json.dumps({"msg": "Missing JSON in request"}), 400  
    add_article(request.get_json())
    return 'Article Added'

if __name__ == '__main__':
    app.run()
