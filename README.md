# Workshop TDD with Android App
* Monitoring # of methods
* Unit testing with [JUnit](https://github.com/junit-team/junit4/wiki)
* Unit testing for UI with [Robolectric](http://robolectric.org/)
* UI testing with [Espresso](https://developer.android.com/training/testing/espresso)
* Code coverage with [Jacoco](https://github.com/jacoco/jacoco) (Hacking)
* Snapshot UI with [Fastlane screengrab](https://docs.fastlane.tools/actions/screengrab/)

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
