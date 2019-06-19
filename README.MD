# What is this?

This is the source code of a kik bot that I wrote from scratch that used to administrate multiple programming groups on Kik.

I wrote this because I got tired of managing the groups by hand and because Kik's API is very limited as far as what bots can do.

To get this code working, you must do the following:

1. Compile the source code into a dummy apk. You then use apktool to decompile the dummy apk. You will see .smali files inside the folder somewhere.

2. You take the .smali files and put them all inside the Kik APK itself. The Kik APK that you are modifying should already have been edited by hand to jump to the MachineBot.smali file. It's up to you on how you want to do that.

3. Use apktool to re-compile all the smali files into an APk.

4. Sign the APK.

5. Run and pray that you did not mess anything up. If so, then the app will crash. I recommend using Android Studio for all of this.

It can be annoying to do all of this by hand all the time so I recommend that you write a script or something that can automate this process for you.

The Kik.smali file contains functions from Kik's original compiled source code that I had to find in the kik APk. I managed to isolate the functions that I need to actually make the bot. These functions are called by using the static functions inside one of the Java files.

As of a year ago, everything works except the logic of the bot itself which I never finished because I lost interest in the project because Telegram is a much better instant messaging platform.

# Where does the name come from?

The bot is named after The Machine in the TV show "Person of Interest". It's a really good TV show that is worth watching.
