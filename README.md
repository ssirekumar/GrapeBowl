<img src="https://user-images.githubusercontent.com/10174519/67888016-602e4780-fb72-11e9-8ac9-188bf25c3c8d.png" alt="Kiwee - Ui" style="width:150px;height:150px;" width="85px" height="100px"></br> Kiwee v1.0
====

<table style="width:100%">
 <tr>
    <td><img src="https://cloud.githubusercontent.com/assets/10174519/23055566/8caa62a4-f50c-11e6-9601-91ce3ef0c898.png" alt="Kiwee - Ui" style="width:150px;height:150px;" width="280px" height="350px"></td>
    <td><img src="https://cloud.githubusercontent.com/assets/10174519/23056026/3026f710-f50f-11e6-9d9e-e1f23a6e7979.png" alt="Kiwee - Ui" style="width:150px;height:150px;" width="280px" height="350px"></td>
    <td><img src="https://cloud.githubusercontent.com/assets/10174519/23178277/d0334816-f88f-11e6-885a-a5e0931fce12.png" alt="Kiwee - Ui" style="width:150px;height:150px;" width="280px" height="350px"></td>
  </tr>
</table>

</br>

The core objective of this application is to reduce manual intervention of executing key functionalities. This application handles all the functionalities that include, allowing the user to install/uninstall applications across multiple devices in Android & iOS platforms, which currently, are being operated manually.

This application also saves time by eradicating tedious manual efforts in operating on multiple devices sequentially.

<h3><b>Install App:</b></h3></br>
<ol type="A" style="display: inline;">
  <li>As soon as we launch the application, the first tab among the available tabs that shows up is the Install App Tab, contains a drop down menu named “Device OS type” that will let us choose between Android or iOS onto which the application has to be installed.</li>
  <li>Once the platform is chosen, we come across 2 radio buttons that gives us choice whether to pick the APK either from <b>System path / JFrog Repository.</b></li>
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
</ol></br>

<h3><b>Uninstall app:</b></h3></br>
     <ol type="A" style="display: inline;">
  <li>The second tab that the user sees is the Uninstall app. As the name suggests, the user is given the option to uninstall the application across multiple devices
</li>
  <li>The user can select the OS type, whether it is Android or iOS.</li>
  <li>The user may chose uninstall option provided.</li>
  <li>The user can manually enter the application package name (Android) or bundle identifier (iOS) to be uninstalled or can choose the list of application names to be uninstalled.</li>
  <li>After chosen app package name (Android) or bundle identifier (iOS) to uninstall, Kiwee will search all connceted devices is there any application containing this package name (Android) or bundle identifier (iOS), if it is it will return that device and add it to device list controller.</li>
  <li>Once the contains applications devices are found, has to press “Uninstall App’s” button to be able to uninstall the selected apps.</li>    
</ol>  
   
<h3><b>Clear App Data & Cache: </b></h3></br>
     <ol type="A" style="display: inline;">
  <li>This feature provides the user an option to clear the app data & cache from the list of connected devices.</li>
  <li>After choosen app package name (Android) or bundle identifier (iOS) to clear app data & cache, Kiwee will search all connceted devices is there any application containing this package name (Android) or bundle identifier (iOS), if it is it will return that device and add it to device list controller.</li>   
</ol>  


















Note: Soon will update the code and execution file.


























BSD 3-Clause License

Software License Agreement (<a href="https://opensource.org/licenses/BSD-2-Clause">BSD License</a>)</br>
<b>Copyright (c) 2017, Ssire Kumar
All rights reserved.</b>

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
3. Neither the name of the copyright holder nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
