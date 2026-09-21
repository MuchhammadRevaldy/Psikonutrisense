@echo off
set "JAVA_HOME=C:\Program Files\Android\Android Studio\jbr"
cd /d C:\Users\Sandy\Psikonutrisense
call C:\Users\Sandy\Psikonutrisense\gradlew.bat assembleDebug --no-daemon --console=plain
