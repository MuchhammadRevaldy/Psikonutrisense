$env:JAVA_HOME = 'C:\Program Files\Android\Android Studio\jbr'
Set-Location 'C:\Users\Sandy\Psikonutrisense'
.\gradlew.bat assembleDebug 2>&1 | Out-File -FilePath 'C:\Users\Sandy\Psikonutrisense\build_log.txt' -Encoding utf8
