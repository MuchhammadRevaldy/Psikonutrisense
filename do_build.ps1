$proc = Start-Process -FilePath "cmd.exe" -ArgumentList "/c gradlew.bat :app:assembleDebug --console=plain > build.log 2>&1" -NoNewWindow -PassThru -Wait
Write-Host "Exit Code:" $proc.ExitCode
