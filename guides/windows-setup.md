# Set up Windows 10 for installations

* Set up Windows normally during blue setup screens, but turn off non-essential services

* Uninstall non-essential apps & bloatware
  * Delete Program Files (x86) -> ASUS -> APRP
  * Right-click Start Menu -> Control Panel -> Programs / Uninstall a program
  * Settings -> System -> Apps & Features
    * Microsoft OneDrive
  * Settings -> System -> Default Apps
    * Set Web Browser to Chrome

* Clean up Windows UI
  * System Taskbar
    * Unpin apps
    * Hide Cortana
  * Click notification icon in taskbar and "clear all" up top
  * Start menu
    * Unpin live tiles and collapse width of start menu
  * Change background image

* Run Windows system updates:
  * Settings -> Update & Security -> Windows Update -> Check for Updates

* Update video card drivers

* Enable network sharing

* Prevent interruptions
  * Energy preferences:
    * Settings -> System -> Power & Sleep
      * When plugged in, turn off screen & PC sleep: Never
      * Click Additional Power settings button:
        * Show additional plans -> High Performance -> Change plan settings -> Change advanced power settings
          * Select "High Performance" from dropdown
          * USB settings -> Selective suspend -> Plugged in -> Disabled
  * Turn off screensaver: Control Panel -> Appearance and Personalization -> Change Screen Saver
  * Settings -> Personalization -> Home -> Start -> Turn off: "Occasionally show suggestions in Start"
  * Turn off system sounds:
    * Settings -> Personalization -> Themes -> Sounds -> Sound Scheme -> No Sounds
  * Turn off notifications:
    * Settings -> System -> Notifications & actions
    * Settings -> Privacy -> Notifications -> Turn off "Let apps access my notifications"
    * Settings -> Network & Internet -> Windows Firewall -> Change notification settings
      * Uncheck both "Notify me" boxes
    * Control Panel -> System & Security -> Security & Maintenance -> Change Security & Maintenance settings
      * Uncheck all boxes
  * Turn on Windows Defender
    * Settings -> Update & Security -> Windows Defender -> Open Windows Defender
    * Settings -> Update & Security -> Windows Defender -> Enhanced notifications -> Off
    * But turn off notifications
      * Task Manager -> Startup -> Disable Windows Defender Notifications
  * Turn off power management for wifi connection: 
    * Control Panel -> Network and Internet -> Network and Sharing Center -> Change Adapter Settings -> Right-click Wifi -> Properties -> Configure -> Power Management -> Uncheck "Allow ... turn off power:

* Performance boost:
  * Defrag Hard drive:
    * Control Panel -> System & Security -> Administrative tools -> Defrag and optimize your drives
  * Control Panel -> System & Security -> System -> Advanced System settings -> Advanced tab -> Performance Settings -> Visual Effects -> Adjust for Best Performance
    * But re-check:
      * Show shadows under Mouse pointer
      * Show window contents while dragging
      * Smooth edges of screen fonts

* Windows settings:
  * Control Panel -> Hardware and Sound -> Power Options -> System Settings
    * Power button should shut down
    * Laptop should do nothing on lid close
  * Settings -> System -> Display -> Change the size of text, apps... -> 100%
  * Settings -> Personalization -> Multiple Displays -> Show taskbars on all displays -> Off
  * Show file name extensions: In a system Explorer window, click "View" in the toolbar, and check "File name extensions" and "Hidden files"
  * Set to Developer mode:
    * Settings -> Update & Security -> For developers -> Developer Mode option
    * Click "Apply" under the "Windows Explorer", "Remote Desktop" and "Powershell" section checkboxes
  * Rename PC (requires restart):
    * Settings -> System -> About -> Rename PC
  * Remove login screen:
    * Windows button + "R" -> "netplwiz" + Run
      * Uncheck "Users must enter..." -> Apply -> Type password twice

* Download essential apps & pin to taskbar
  * Chrome
  * Atom
  * Github Desktop
  * Java latest version
  * Eclipse
  * Microsoft Kinect SDK
  * Latest GeForce drivers
  * Processing

* Disable Java updates:
  * Find the Java icon in the system taskbar from the up arrow on the right and disable
  * Open Java app from Start Menu and disable from there
  * Open the system Task Manager (ctrl + alt + delete), go to the Startup tab, and disable java updater on startup

* Set a static [IP address](https://portforward.com/networking/static-ip-windows-10.htm)

Additional steps:
  * BIOS settings to resume after power loss
  * http://www.evsc.net/tech/prep-windows-machine-for-fulltime-exhibition-setup
  * https://github.com/morphogencc/ofxWindowsSetup
  * https://github.com/brangerbriz/up-4evr-windows-10

* Scripting help:
  * Set a timeout: `timeout 15 > NUL`
  * Set a timeout with a visible countdown: `timeout 15`
  * Reference User directory: `C:%HOMEPATH%\Documents\`
  * Remove Apple files from current dir: `del /s /q *.DS_Store`
  * List running tasks: `tasklist`
  * Kill a task by .exe: `Taskkill /IM javaw.exe /F`
  * Kill a task by id: `Taskkill /PID 26356 /F`
  * Add script to system startup:
    * Windows Key + R
    * `shell:startup`
    * Copy alias into Startup folder
  * Add custom icon to script:
    * Make a shortcut -> right-click -> Properties -> Change Icon...
