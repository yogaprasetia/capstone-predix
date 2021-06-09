from flask import Flask, json, request, render_template
import os
import pymysql

app = Flask(__name__)

username = os.environ.get('CLOUD_SQL_USERNAME')
password = os.environ.get('CLOUD_SQL_PASSWORD')
name = os.environ.get('CLOUD_SQL_DATABASE_NAME')
conn_name = os.environ.get('CLOUD_SQL_CONNECTION_NAME')

def open_connection():
    unix_socket = '/cloudsql/{}'.format(conn_name)
    conn = pymysql.connect(user=username, password=password,unix_socket=unix_socket, db=name,cursorclass=pymysql.cursors.DictCursor)
    return conn

@app.route('/', methods=['POST', 'GET'])
def index():
    return render_template('index.html')

def get_article():
    conn = open_connection()
    cursor = conn.cursor()
    result = cursor.execute('SELECT * FROM articles;')
    article = cursor.fetchall()
    got_article = json.dumps(article) if result > 0 else 'No data'
    conn.close()
    return got_article

@app.route('/add_article', methods=['POST', 'GET'])
def add_article():
    conn = open_connection()
    images = request.form['images']
    title = request.form['title']
    content = request.form['content']
    try:
        cursor = conn.cursor()
        data_sql = "INSERT INTO articles (images,title, content) VALUES (%s, %s, %s)"
        cursor.execute(data_sql, (images,title, content))
        conn.commit()
    finally:
        conn.close()
        return "Article Saved"

@app.route('/api', methods=['POST', 'GET'])
def article():
    if request.method != 'POST':
        return get_article()
    elif request.method == 'POST':
        return add_article()
    else:
        json.dumps({"msg": "Missing JSON in request"}), 400

if __name__ == '__main__':
    app.run()