<a style="cursor:not-allowed;opacity:0.5;text-decoration:none"><img src="https://user-images.githubusercontent.com/10174519/74126700-9ada5e00-4bfe-11ea-8e99-68f6eaeb64ea.png" alt="Grape Bowl Tool" width="155px" height="120px"></a></br> Grape Bowl
====
<table style="width:100%">
 <tr>
    <td><img src="https://user-images.githubusercontent.com/10174519/70904857-714eed00-2028-11ea-908b-e457c0dbcca7.png" alt="Grape Bowl" style="width:150px;height:150px;" width="380px" height="350px"></td>
    <td><img src="https://user-images.githubusercontent.com/10174519/70904952-a9563000-2028-11ea-8494-374deb19fe6b.png" alt="Grape Bowl" style="width:150px;height:150px;" width="380px" height="350px"></td>
  <td><img src="https://user-images.githubusercontent.com/10174519/70905182-431ddd00-2029-11ea-915c-ff2a35966be8.png" alt="Grape Bowl" style="width:150px;height:150px;" width="380px" height="350px"></td>
    <td><img src="https://user-images.githubusercontent.com/10174519/70905022-d7d40b00-2028-11ea-841c-65a2f8e6c3cd.png" alt="Grape Bowl" style="width:150px;height:150px;" width="380px" height="350px"></td>
  <td><img src="https://user-images.githubusercontent.com/10174519/70905171-3f8a5600-2029-11ea-829e-b675fffd6183.png" alt="Grape Bowl" style="width:150px;height:150px;" width="380px" height="350px"></td>
  <td><img src="https://user-images.githubusercontent.com/10174519/70905153-326d6700-2029-11ea-99a7-a2ca5d9b89d8.png" alt="Grape Bowl" style="width:150px;height:150px;" width="380px" height="350px"></td>
  </tr>
</table>

</br>

The core objective of this application is to reduce manual intervention of executing key functionalities. This application handles all the functionalities that include, allowing the user to install/uninstall applications across multiple devices in Android & iOS platforms, which currently, are being operated manually.

This application also saves time by eradicating tedious manual efforts in operating on multiple devices sequentially.

Where do we get this application: https://github.com/ssirekumar/GrapeBowl/releases

## Menu List
 [Dependencies](#dependencies) </br>
 [JFrog Credentials](#jfrog-credentials)</br>
 [Path of ADB on macOS machines](#path-of-adb-on-macos-machines)</br>
 [Path of libimobiledevice on macOS machines](#path-of-libimobiledevice-on-macos-machines)</br>
 [Path of ios-deploy on macOS machines](#path-of-ios-deploy-on-macos-machines)</br>
 [Install App (Android & iOS)](#install-app)</br>
 [Uninstall app (Android & iOS)](#uninstall-app)</br>
 [Clear App Data and Cache (Android)](#clear-app-data-and-cache)</br>
 [Device Info (Android)](#device-info)</br>
 [Bug Report (Android)](#bug-report)</br>
 [Execute ADB Commands (Android)](#execute-adb-commands)</br>

## Dependencies:

1. On first launch Kiwee need parameter values, this we have to provide from Kiwee preferences (File —> Preferences). All values are mandatory.
 ```
Android ADB installed path*
iOS-libimobiledevice installed path*
Android Application Package names*`
iOS-ios-deploy installed path*
```
#### JFrog Credentials
```
JFrog Artifactory base URL
JFrog Artifactory user name
JFrog Artifactory api key
JFrog Artifactory Android build path
JFrog Artifactory iOS build path
```
2. Setup Homebrew on a Mac <a href='https://docs.brew.sh/Installation'>https://docs.brew.sh/Installation</a>
```
1. Install Homebrew on a Mac (https://docs.brew.sh/Installation, https://brew.sh/)
2. xcode-select --install (Xcode Command Line Tools https://railsapps.github.io/xcode-command-line-tools.html)
    a. Check if the full Xcode package is already installed:
        a.1 $ xcode-select -p
    b. If you see:
        /Applications/Xcode.app/Contents/Developer
        the full Xcode package is already installed.
3. Change "Command Line Tools" iOS version > 10.x.x 
     3.1 You can do this in two ways:
          a. $ sudo xcode-select -s /Applications/Xcode-beta.app 
          b. Or via Xcode
              b. 1 Click the "Xcode" button in the top left of the screen near the Apple logo, then click "Preferences"
              b. 2 Click on Locations -> select Command Line Tools
4. Install libimobiledevice (https://github.com/libimobiledevice/libimobiledevice)
    4.1 $ brew install --HEAD libimobiledevice
    4.2 $ brew link --overwrite libimobiledevice
    4.3 $ brew install --HEAD  ideviceinstaller
    4.4 $ brew link --overwrite ideviceinstaller
    4.4 $ sudo chmod -R 777 /var/db/lockdown/ (This step is optional)
5. Install ios-deploy (https://github.com/ios-control/ios-deploy) either from homebrew OR npm
     a. ios-deploy installation is made simple using the node.js package manager. If you use Homebrew, install node.js:
          a.1 $ brew install node
          a.2 $ npm install -g ios-deploy
     b. ios-deploy with Homebrew
          b.1 $ brew install ios-deploy

```
#### Path of ADB on macOS machines

3. Path of ADB on <b>macOS</b> machines if it installed from <a href='https://developer.android.com/studio/install'>Android studio</a>
```
/Users/<your username>/Library/Android/sdk/platform-tools/adb
```
#### Path of libimobiledevice on macOS machines

4. iOS <a href='https://github.com/libimobiledevice/libimobiledevice'>libimobiledevice</a> installed path <b>macOS</b>

```
/usr/local/Cellar/libimobiledevice/1.2.0_4/bin/

Version will vary at the time of new installation
```
#### Path of ios-deploy on macOS machines

5. iOS <a href='https://github.com/ios-control/ios-deploy'>ios-deploy</a> installed path on <b>macOS</b>
```
/usr/local/lib/node_modules/ios-deploy/build/Release/
```

# Key Features: The core functionalities of this application include
   
## Install App:
<ol type="A" style="display: inline;">
  <li>As soon as we launch the application, the first tab among the available tabs that shows up is the Install App Tab, contains a drop down menu named “Device OS type” that will let us choose between Android or iOS onto which the application has to be installed.</li>
  <li>Once the platform is chosen, we come across 2 radio buttons that gives us choice whether to pick the APK either from <b>System path / <a href='https://jfrog.com/artifactory/'>JFrog Repository.</a></b></li>
  <ol type="a">
     <li>When we select System Path, a window opens asking us to choose the location where the apk has been stored to be able to select for installation.</li>
     <li>Similarly, when we choose JFrog, we will be redirected to the repository that contains the apk to be chosen.</li>
     <li>After locating the apk to be installed, we have various installation options, to be appended to the adb command, whether to update the existing apk, over ride the apk and other options.
</li>
     <li>The user is provided with a button named “Get Devices”, tapping on which lists out all the connected devices. The user can choose which device / devices in which the application has to be installed.</li>
     <li>Once the device(s) selection is done, the user can press the button “Install App” that will install the application across the chosen devices.</li>
     <li>The Action Log container shows the user, the ongoing execution like, the installation percentage, bug report capture status etc.</li>
     <li>Also, the user is provided with 2 options to perform on the Action Log, whether to Copy the log or to clear the log.</li>
</ol>  
</ol>

## Uninstall app:
<ol type="A" style="display: inline;">
  <li>The second tab that the user sees is the Uninstall app. As the name suggests, the user is given the option to uninstall the application across multiple devices
</li>
  <li>The user can select the OS type, whether it is Android or iOS.</li>
  <li>The user may chose uninstall option provided.</li>
  <li>The user can manually enter the application package name (Android) or bundle identifier (iOS) to be uninstalled or can choose the list of application names to be uninstalled.(Multiple package/bundle names can be added with | delimiter in Kiwee Preferences)</li>
  <li>After chosen app package name (Android) or bundle identifier (iOS) to uninstall, Kiwee will search all connceted devices is there any application containing this package name (Android) or bundle identifier (iOS), if it is it will return that device and add it to device list controller.</li>
  <li>Once the contains applications devices are found, has to press “Uninstall App’s” button to be able to uninstall the selected apps.</li>    
</ol>  
   
## Clear App Data and Cache:
<ol type="A" style="display: inline;">
  <li>This feature provides the user an option to clear the app data & cache from the list of connected devices.</li>
  <li>After choosen app package name (Android) or bundle identifier (iOS) to clear app data & cache, Kiwee will search all connceted devices is there any application containing this package name (Android) or bundle identifier (iOS), if it is it will return that device and add it to device list controller.</li>   
</ol>  

## Device Info:
<ol type="A" style="display: inline;">
  <li>This tab provides the user to see all the details or the specific details of the connected device.</li>
  <li>Selecting the radio button All, will list out all the basic details of the device selected which can be in turn saved onto CSV.</li>  
  <li>If the user wishes to see only specific details of the connected device, he can select the radio button “Attribute” which shows up the details to be chosen to be shown.</li>
</ol>  

## Bug Report:
<ol type="A" style="display: inline;">
  <li>This option provides the user to generate bug reports across multiple devices, and also gives the location to which the bug report can be saved.</li>
</ol>  

## Execute ADB Commands:
<ol type="A" style="display: inline;">
  <li>Under this tab, the user is provided with two options, predefined and Custom.</li>
  <li>Selecting predefined, shows the user, the list of adb commands that are already incorporated in the application which the user and pick and execute.</li>  
  <li>Alternatively, if the user selects Custom, the user can manually enter the command which is not part of existing commands, may store and save to this application, and execute it.</li>
</ol>  
</br>
</br>



BSD 3-Clause License

Software License Agreement (<a href="https://opensource.org/licenses/BSD-2-Clause">BSD License</a>)</br>
<b>Copyright (c) 2017, Ssire Kumar
All rights reserved.</b>

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
3. Neither the name of the copyright holder nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
