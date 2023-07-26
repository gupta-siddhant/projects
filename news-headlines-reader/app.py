from flask import Flask, render_template, request
import requests
from gtts import gTTS
import os

app = Flask(__name__)

# News API endpoint and API key
NEWS_API_URL = "https://newsapi.org/v2/top-headlines"
API_KEY = "1e807f2ca7124c5eb25b2028c9561ccd"

CATEGORIES = ["general", "business", "entertainment", "health", "sports", "technology"]

@app.route("/")
def index():
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
