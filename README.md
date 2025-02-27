# 🎧 Audio Iconography

_A minimal Android app for detecting wired headphones and displaying an icon in the status bar._

## Overview

Audio Iconography is a lightweight Android application designed to detect when wired headphones are connected and to display a persistent icon in the status bar. Originally built as a favour for a friend who needed a simple, efficient solution, this app has been refined into a fully functional, production-ready tool.

## Features

- **Persistent Notification:**  
  The app displays a status bar icon when wired headphones are connected, ensuring you always know your audio output mode.

- **Automatic Detection:**  
  It continuously monitors the headphone connection state, updating the notification as needed without unnecessary battery drain.


## How It Works

**Headphone Detection:**  
   A background service checks if wired headphones (or USB headsets) are connected. If they are, the app starts a foreground service that displays a notification, and when the headphones are disconnected the notification is removed.



## Contributing

Contributions are welcome! If you have suggestions, bug reports, or feature improvements, please open an issue or submit a pull request.

## Licence

This project is licensed under the [GNU General Public Licence v3.0 (GPLv3)](https://www.gnu.org/licenses/gpl-3.0.html). By releasing Audio Iconography under GPLv3, all improvements remain open-source and available to the community.

Enjoy using Audio Iconography!