# Uppy SDK for Android

The companion backend project for this SDK can found here [Uppy](https://github.com/everli/Uppy)

## SDK Installation

### Gradle using the JitPack Maven dependency

[![](https://jitpack.io/v/Everli/uppy-android-sdk.svg)](https://jitpack.io/#Everli/uppy-android-sdk)

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
      implementation 'com.github.everli:uppy-android-sdk:Tag'
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

        Uppy.init("https://www.example.com/api/", "example-slug")
    }
}

```

* **slug**: The unique identifier for updates to your application.
* **cluster**: The unique cluster group within the slug of your application for updates. e.g. "beta", "alpha", "country" etc.

From version `0.0.4` you can also have a way to track the number of installations for each version of an application. 
To enable this feature you need to pass a unique `deviceId` of the single installation, to the Uppy init method:

```kotlin

class Application: Application() {

    override fun onCreate() {
        super.onCreate()

        Uppy.init("https://www.example.com/api/", "example-slug", "your-unique-installation-identifier")
    }
}

```

#### Check if the installed app version is up to date:

```kotlin
    Uppy.checkForUpdates(context, lifecycleOwner)
```
This function should be called at all possible entry points to your application. A common solution is to call it from a base `BaseActivity` or `BaseFragment`. When calling this method in-built view elements of the Uppy SDK for Android are shown.

A callback variant of the function above also exists.

```kotlin
    Uppy.checkForUpdates(context, lifecycleOwner, callback)
```

This function should also be called at all possible entry points to your application. A common solution is to call it from a base `BaseActivity` or `BaseFragment`. When calling this method customized views and dialogs are shown.


