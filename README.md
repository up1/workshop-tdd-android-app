# Workshop TDD with Android App
* Monitoring # of methods
* Unit testing with [JUnit](https://github.com/junit-team/junit4/wiki)
* Unit testing for UI with [Robolectric](http://robolectric.org/)
* UI testing with [Espresso](https://developer.android.com/training/testing/espresso)
* Code coverage with [Jacoco](https://github.com/jacoco/jacoco) (Hacking)
* Snapshot UI with [Fastlane screengrab](https://docs.fastlane.tools/actions/screengrab/)
* [More Workshop](https://github.com/up1/workshop-tdd-android-app/wiki/Workshop-Food)

### Enable dex count in project
File build.gradle
```
dependencies {
        ...
        classpath 'com.getkeepsafe.dexcount:dexcount-gradle-plugin:0.8.6'
    }
```
File /app/build.gradle
```
apply plugin: 'com.getkeepsafe.dexcount'
```

### Add library of espresso (Bug in Android Studio)
```
dependencies {
    ...
    androidTestImplementation 'com.android.support.test:runner:1.0.2'
    androidTestImplementation 'com.android.support.test:rules:1.0.2'
    androidTestImplementation 'com.android.support.test.espresso:espresso-core:3.0.2'
}
```

### Enable code coverage in project
```
apply plugin: 'jacoco'

jacoco {
    toolVersion = '0.8.4' //Use latest version
}

android {
    
    buildTypes {

        debug {
            testCoverageEnabled true
        }
    }
}
```

### Create task=jacocoTestReport in file /app/build.gradle
```
task jacocoTestReport(type: JacocoReport, dependsOn: ['testDebugUnitTest', 'createDebugCoverageReport']) {

    reports {
        xml.enabled = true
        html.enabled = true
    }

    def fileFilter = [ '**/R.class', '**/R$*.class', '**/BuildConfig.*', '**/Manifest*.*', '**/*Test*.*', 'android/**/*.*', 'androidx/**/*.*' ]
    // Java
    def debugTree1 = fileTree(dir: "$project.buildDir/intermediates/javac/debug/compileDebugJavaWithJavac/classes", excludes: fileFilter)
    // Kotlin
    def debugTree2 = fileTree(dir: "$project.buildDir/tmp/kotlin-classes/debug", excludes: fileFilter)
    def mainSrc = "$project.projectDir/src/main/java"

    sourceDirectories = files([mainSrc])
    classDirectories = files([debugTree1, debugTree2])
    executionData = fileTree(dir: project.buildDir, includes: [
            'jacoco/testDebugUnitTest.exec', 'outputs/code_coverage/debugAndroidTest/connected/*coverage.ec'
    ])
}
```

### Working with [Robolectric](http://robolectric.org/)
File /app/build.gradle
```
android {
   ...
   testOptions {
        unitTests {
            includeAndroidResources = true
        }
    }
}

dependencies {
    testImplementation "org.robolectric:robolectric:4.3"
}
```

Test case in /src/test
```
@RunWith(RobolectricTestRunner::class)
class RobolectricMainActivityTest {

    @Test fun `check data in first page`() {
        // Arrange
        val activity = buildActivity(MainActivity::class.java)
            .create()
            .resume()
            .get()

        // Act

        // Assert
        val show = activity.findViewById(R.id.show) as TextView
        assertEquals("Hello World!", show.text);
    }

}
```

### Working with fastlane screengrab
/app/build.gradle
```
android {
   testOptions {
        unitTests {
            includeAndroidResources = true
        }
    }
}

dependencies {
    androidTestImplementation 'tools.fastlane:screengrab:1.2.0'
}
```

Create file /src/debug/AndroidManefest.xml
```
<!-- Allows unlocking your device and activating its screen so UI tests can succeed -->
    <uses-permission android:name="android.permission.DISABLE_KEYGUARD"/>
    <uses-permission android:name="android.permission.WAKE_LOCK"/>

    <!-- Allows for storing and retrieving screenshots -->
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />

    <!-- Allows changing locales -->
    <uses-permission android:name="android.permission.CHANGE_CONFIGURATION" />
```

Test case
```
class MainActivityWithScreengrabTest {

    @get:ClassRule
    @JvmField
    val localeTestRule = LocaleTestRule()

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java)

    @Test fun open_main_page() {
        Screengrab.setDefaultScreenshotStrategy(UiAutomatorScreenshotStrategy())
        Screengrab.screenshot("step_01")
        onView(withText("Hello World!"))
            .check(matches(isDisplayed()))
    }

}
```

How to run ?
```
$fastlane screengrab init

[✔] 🚀
[09:01:51]: Successfully created new Screengrabfile at './Screengrabfile'
```

Edit file ./Screengrabfile
```
app_package_name('com.example.demo01')
app_apk_path('app/build/output/apk/debug/app-debug.apk')
tests_apk_path('app/build/output/apk/androidTest/app-debug-androidTest.apk')
locales(['en-US'])
clear_previous_screenshots(true)
```

Run
```
$./gradlew assembleDebug assembleAndroidTest
$fastlane screengrab
```
```
