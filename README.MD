# What is this?

This is the source code of a kik bot that I wrote from scratch that used to administrate multiple programming groups on Kik.

I wrote this because I got tired of managing the groups by hand and because Kik's API is very limited as far as what bots can do.

This code only works by modifying a Kik APK to jump to the start of this source code. After all the code is executed, then it will go back to whatever the code is in the APK.

The Kik.smali file contains functions from Kik's original compiled source code that I had to find in the kik APk. I managed to isolate the functions that I need to actually make the bot. These functions are called by using the static functions inside one of the Java files.

As of a year ago, everything works except the logic of the bot itself which I never finished because I lost interest in the project because Telegram is a much better instant messaging platform.
