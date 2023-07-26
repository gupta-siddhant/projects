# Text-to-Speech Project with gtts

This project is a simple implementation of a Text-to-Speech (TTS) application using the `gtts` library in Python. It allows users to enter text, convert it to speech, and play the generated audio.

## Installation

To run this project, you need to have Python 3.x installed on your system. If you don't have Python installed, you can download it from the official Python website: [https://www.python.org/downloads/](https://www.python.org/downloads/)

### Install Required Python Packages

1. Open your terminal or command prompt.

2. Use the following command to install Flask and requests:

   ```bash
   pip install Flask requests
   pip install gtts
   ```

### Generate News API Key

To fetch news from the News API, you will need an API key. Follow these steps to get your API key:

1. Go to the News API website: [https://newsapi.org/](https://newsapi.org/)

2. Click on the "Get API Key" button and sign up for a free account.

3. Once signed up, you will receive your API key. Keep this key secure, as it will be used in the application.

### Install mpg321 (Windows)

For Windows users, the `gtts` library requires the `mpg321` command-line player for MPEG audio files. To install it, follow these steps:

1. Download the `mpg321` executable from the following link: [https://www.mpg123.de/download/win64/1.28.0/](https://www.mpg123.de/download/win64/1.28.0/)

2. Extract the downloaded zip file and copy the `mpg321.exe` file to a directory included in your system's PATH. For example, you can copy it to the `C:\Windows\System32` folder.

3. After installing `mpg321`, the TTS application should work without any issues.

## How to Run

1. Clone this repository to your local machine.

   ```bash
   git clone <repository-url>
   cd text-to-speech-gtts
   ```

2. Open the `app.py` file in a text editor.

3. Replace `'YOUR_API_KEY'` with your actual News API key obtained earlier.

4. Save the file and close the text editor.

5. Run the Flask app:

   ```bash
   python app.py
   ```

6. Open your web browser and go to [http://localhost:5000/](http://localhost:5000/).

7. Enter the text you want to convert to speech and click the "Play" button.

8. The audio will start playing, and you should be able to hear the speech.

## Troubleshooting

If you encounter any issues while running the application, ensure that you have followed the installation steps correctly. Check the terminal or command prompt for any error messages or warnings.

For Windows users, make sure that `mpg321` is correctly installed and included in your system's PATH. If you continue to face issues, consider checking the `mpg321` documentation or community forums for assistance.

If you have any questions or need further support, please don't hesitate to reach out to us.

Enjoy using the Text-to-Speech application with `gtts`! Happy listening! 🎧