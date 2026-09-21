$env:JAVA_HOME = "C:\Program Files\Android\Android Studio\jbr"
Set-Location -Path "C:\Users\Sandy\Psikonutrisense"
& "C:\Users\Sandy\Psikonutrisense\gradlew.bat" assembleDebug --console=plain 2>&1 | Set-Content -Path "C:\Users\Sandy\Psikonutrisense\gradle_run.log"
