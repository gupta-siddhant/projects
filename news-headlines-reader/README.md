# Text-to-Speech Project with gtts

This project is a simple implementation of a Text-to-Speech (TTS) application using the `gtts` library in Python. It allows users to enter text, convert it to speech, and play the generated audio.

## Installation

To run this project, you need to have Python 3.x installed on your system. If you don't have Python installed, you can download it from the official Python website: [https://www.python.org/downloads/](https://www.python.org/downloads/)

### Install gtts

1. Open your terminal or command prompt.

2. Use the following command to install `gtts` via pip:

   ```bash
   pip install gtts
   ```

3. After installation, you should be ready to run the Text-to-Speech application.

### Install mpg321 (macOS)

For macOS users, the `gtts` library requires `mpg321`, a command-line player for MPEG audio files. If you encounter the "zsh: command not found: mpg321" error, you need to install `mpg321` using Homebrew:

1. Install Homebrew (if you don't have it):

   ```bash
   /bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
   ```

2. Install mpg321 using Homebrew:

   ```bash
   brew install mpg321
   ```

3. After installing `mpg321`, the TTS application should work without any issues.

## How to Run

1. Clone this repository to your local machine.

   ```bash
   git clone <repository-url>
   cd text-to-speech-gtts
   ```

2. Run the Flask app:

   ```bash
   python app.py
   ```

3. Open your web browser and go to [http://localhost:5000/](http://localhost:5000/).

4. Enter the text you want to convert to speech and click the "Play" button.

5. The audio will start playing, and you should be able to hear the speech.

## Troubleshooting

If you encounter any issues while running the application, ensure that you have followed the installation steps correctly. Check the terminal or command prompt for any error messages or warnings.

For macOS users, make sure that `mpg321` is correctly installed and added to your PATH. If you continue to face issues, consider checking the Homebrew documentation or community forums for assistance.

If you have any questions or need further support, please don't hesitate to reach out to us.

Enjoy using the Text-to-Speech application with `gtts`! Happy listening! 🎧