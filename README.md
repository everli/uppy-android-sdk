# Uppy SDK for Android

The companion backend project for this SDK can found here [Uppy]()


## SDK Installation

### Gradle using the JCenter dependency
[ ![Download](https://api.bintray.com/packages/everli/maven/uppy/images/download.svg) ](https://bintray.com/everli/maven/uppy/_latestVersion)
```groovy
implementation 'com.everli.uppy:uppy:1.0'
```

### Gradle using the JitPack Maven dependency

[![](https://jitpack.io/v/Everli/uppy.svg)](https://jitpack.io/#Everli/uppy)

Add the following repository to your root gradle file:

 ```groovy
   allprojects {
     repositories {
       maven { url "https://jitpack.io" }
     }
   }
 ````

Add the following dependency to your app gradle file:

  ```groovy
    dependencies {
      implementation 'com.everli.uppy:uppy:v1.0'
    }
  ```

## Usage

Add the Internet Permission to your manifest:

```xml
<manifest ... >

    <uses-permission android:name="android.permission.INTERNET" />

    <application ...>

```

Initialize the library inside the  `onCreate()` method of your `Application` class:

```kotlin

class Application: Application() {

    override fun onCreate() {
        super.onCreate()

        Uppy.init("https://www.example.com", mode)
    }
}

```
* **mode**: an enum of type `UppyMode` used during initialization phase to specify the update modes. There are two possible types of modes:
  * DEFAULT: This mode is used to show the in-built view elements of the Uppy SDK for Android.
  * CUSTOM: This mode is used to present customized views and dialogs for the Uppy SDK for Android.

#### Check if the installed app version is up to date:

```java
Uppy.checkForUpdates()
```
This function should be called at all possible entry points to your application. A common solution is to call it from a base `BaseActivity` or `BaseFragment`.
