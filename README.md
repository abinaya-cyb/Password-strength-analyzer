# Password Strength Analyzer

A desktop password-strength checker built with Java 25 and Swing. It uses no
external libraries and evaluates passwords locally without storing them.

## Run in VS Code

1. Open this project folder in VS Code with the Java extensions installed.
2. Open `src\main\java\com\passwordstrengthanalyzer\PasswordStrengthAnalyzerApp.java`.
3. Select **Run** above the `main` method, or choose **Launch Password Strength Analyzer**
   from the Run and Debug panel.

## Share the project

Send the project as a ZIP. After extracting it, the recipient can double-click
`Run Password Strength Analyzer.bat` to launch the included runnable JAR. Java
25 or later must be installed on their computer. The ZIP also includes the
source files, so they can open the project in VS Code to inspect or edit it.

## Run from a terminal

From the project folder, compile and launch with Java 25:

```powershell
javac -d out src\main\java\com\passwordstrengthanalyzer\PasswordStrengthAnalyzer.java src\main\java\com\passwordstrengthanalyzer\PasswordStrengthAnalyzerApp.java
java -cp out com.passwordstrengthanalyzer.PasswordStrengthAnalyzerApp
```

The score considers password length and whether it contains uppercase letters,
lowercase letters, numbers, and special characters. Scores are classified as
Weak (0-49), Medium (50-84), or Strong (85-100).
