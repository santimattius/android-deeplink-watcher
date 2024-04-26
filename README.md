[![](https://jitpack.io/v/santimattius/android-deeplink-watcher.svg)](https://jitpack.io/#santimattius/android-deeplink-watcher)
# Android Deeplink Watcher

DeepLink Watcher offers a mechanism to capture the deeplinks that are executed in our application, simplifying their visualization in a simple way.

## Screenshot
<p align="center">
  <img width="270" src="https://github.com/santimattius/android-deeplink-watcher/blob/master/screenshots/deeplink-viewer.png?raw=true"/>
  <img width="270" src="https://github.com/santimattius/android-deeplink-watcher/blob/master/screenshots/deeplink-detail.png?raw=true"/>
</p>

## Features

- Captures the deep links, both internal and external, that are executed in our application.
- Includes a viewer to see the details of the deep links that have been executed.

<p align="center">
  <img width="270" src="https://github.com/santimattius/android-deeplink-watcher/blob/master/screenshots/deeplink-watcher-demo.gif"/>
</p>

## Installation

You can add this library to your Android project using Gradle. Make sure to add the repository in your `build.gradle` file at the project level:

```groovy
dependencyResolutionManagement {
   repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
   repositories {
	mavenCentral()
	maven { url 'https://jitpack.io' }
   }
}
```

Next, add the dependency to your `build.gradle` file at the application level:

```groovy
dependencies {
   implementation "com.github.santimattius:android-deeplink-watcher:${version}"
}
```

Replace `version` with the version of the library you want to use.

## Usage

Once you've added the dependency to your project, you can start using the utilities and components provided by the library.

Deeplink Watcher uses [App Startup](https://developer.android.com/topic/libraries/app-startup). This means that, once the dependency is added, when starting your application, it will start capturing the deeplinks as they are executed. To visualize them, Deeplink Watcher offers a method to invoke the viewer.

```kotlin
// show deeplink viewer screen
DeeplinkWatcher.showViewer(context)
```

# Manual Initialization

To disable all automatic initialization, remove the entire entry for `InitializationProvider` from the manifest.

```xml
<provider
    android:name="androidx.startup.InitializationProvider"
    android:authorities="${applicationId}.androidx-startup"
    tools:node="remove" />
```

In the event that you are using App Startup for other initializers, you can simply remove the DeepLink Watcher initializer in the following way:

```xml
<provider
    android:name="androidx.startup.InitializationProvider"
    android:authorities="${applicationId}.androidx-startup"
    android:exported="false"
    tools:node="merge">
    <meta-data android:name="io.github.santimattius.android.deeplink.watcher.internal.initializer.DeeplinkWatcherInitializer"
              tools:node="remove" />
</provider>
```

If automatic initialization is disabled, you can use `DeepLinkWatcher.attach(context)` to manually start DeepLink Watcher.

```kotlin
class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        DeeplinkWatcher.attach(this)
    }
}
```

# Listen to Captured DeepLinks

With DeepLink Watcher, you can listen to the deeplinks executed through `DeepLinkWatcher.watch`.

```kotlin
DeepLinkWatcher.watch(coroutineScope: CoroutineScope, block: suspend (DeepLinkInfo) -> Unit)
```

For example, if you need to capture this information from your application, you can register in the following way:

```kotlin
class MainApplication : Application() {

    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    override fun onCreate() {
        super.onCreate()

        DeeplinkWatcher.watch(scope) {
            Log.d("Subscriber", "Deeplink captured: $it")
        }
    }
}
```

# Contributions

Contributions are welcome! If you want to contribute to this library, please follow the following steps:

1. Fork the repository.
2. Create a new branch for your contribution (`git checkout -b feature/new-feature`).
3. Make your changes and make sure to follow the style guides and coding conventions.
4. Commit your changes (`git commit -am 'Add new feature'`).
5. Upload your changes to your repository on GitHub (`git push origin feature/new-feature`).
6. Create a new pull request and describe your changes in detail.

## Contact

If you have questions, issues or suggestions related to this library, feel free to [open a new issue](https://github.com/yourusername/AndroidXYZ/issues) on GitHub. We are here to help you!
