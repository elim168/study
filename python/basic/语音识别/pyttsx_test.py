# pip3 install pyttsx3
# pyttsx3可以把文字转换为语音
# 使用pyttsx3还需要安装一个语言环境espeak，sudo apt-get update && sudo apt-get install espeak

import pyttsx3 as pyttsx

engine = pyttsx.init()
engine.setProperty('voice', 'zh')
engine.say('Hello Python! ')
engine.say('中国')
engine.runAndWait()
