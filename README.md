# Uppy SDK for Android

## Usage

Add the Internet Permission to your manifest:

```xml
<manifest ... >

    <uses-permission android:name="android.permission.INTERNET" />

    <application ...>

```

Initialize the library inside the  `onCreate()` method of your Application class:

```kotlin

class Application: Application() {

    override fun onCreate() {
        super.onCreate()

        Uppy.init("https://www.example.com")
    }
}

```

#### Check if the installed version is up to date:

```java
Uppy.isLatestVersion()
```

// TODO all the rest

