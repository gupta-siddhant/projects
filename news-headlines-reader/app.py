from flask import Flask, render_template, request, redirect, url_for, session
import requests
from gtts import gTTS
import os
import mysql.connector

app = Flask(__name__)
app.secret_key = '<your-secret-key>'

# News API endpoint and API key
NEWS_API_URL = "https://newsapi.org/v2/top-headlines"
API_KEY = "1e807f2ca7124c5eb25b2028c9561ccd"

CATEGORIES = ["general", "business", "entertainment", "health", "sports", "technology"]

db_config = {
    'host': '<host>',
    'user': '<username>',
    'password': '<password>',
    'database': '<db>'
}

@app.route('/', methods=['GET', 'POST'])
@app.route('/login', methods=['GET', 'POST'])
def login():
    if request.method == 'POST':
        username = request.form['username']
        password = request.form['password']

        try:
            connection = mysql.connector.connect(**db_config)
            cursor = connection.cursor()

            query = "SELECT password FROM users WHERE username = %s"
            cursor.execute(query, (username,))
            row = cursor.fetchone()

            if row and row[0] == password:
                session['username'] = username  # Store username in session
                return redirect(url_for('index'))  # Redirect to index page

            else:
                return 'Invalid username or password'

        except mysql.connector.Error as err:
            print("Error: ", err)
            return 'Database error'

        finally:
            if connection.is_connected():
                cursor.close()
                connection.close()

    return render_template('login.html')

@app.route('/register', methods=['POST'])
def register():
    new_username = request.form['new_username']
    new_password = request.form['new_password']

    try:
        connection = mysql.connector.connect(**db_config)
        cursor = connection.cursor()

        query = "INSERT INTO users (username, password) VALUES (%s, %s)"
        cursor.execute(query, (new_username, new_password))
        connection.commit()

        return 'User registered successfully. You can now login.'

    except mysql.connector.Error as err:
        print("Error: ", err)
        return 'Registration failed'

    finally:
        if connection.is_connected():
            cursor.close()
            connection.close()


@app.route("/index")
def index():
    if 'username' in session:
        # Parameters for the API request
        params = {
            "country": "us",  # Specify the country for the news (e.g., US)
            "apiKey": API_KEY
        }
    
        # Make the API request and get the response
        response = requests.get(NEWS_API_URL, params=params)
        data = response.json()
    
        # Extract news articles from the response
        articles = data.get("articles", [])
    
        # Render the HTML template with the news data
        return render_template("index.html", articles=articles, categories=CATEGORIES)
    else:
        return redirect(url_for('login'))
    

@app.route("/get_news")
def get_news():
    # Get the category parameter from the URL
    category = request.args.get("category", "general")  # Default to "general" if no category provided

    # Parameters for the API request
    params = {
        "country": "us",  # Specify the country for the news (e.g., US)
        "category": category,  # Specify the category for the news
        "apiKey": API_KEY
    }

    # Make the API request and get the response
    response = requests.get(NEWS_API_URL, params=params)
    data = response.json()

    # Extract news articles from the response
    articles = data.get("articles", [])

    speak_headlines(articles)

    # Render the HTML template with the news data
    return render_template("index.html", articles=articles, categories=CATEGORIES)

def speak_headlines(articles):
    for article in articles:
        title = article.get("title", "")
        tts = gTTS(text=title, lang='en')
        tts.save("temp.mp3")
        os.system("mpg321 temp.mp3")


if __name__ == "__main__":
    app.run(debug=True)
