# Availity
### LISP Syntax Checker
1. Compile both SyntaxChecker and FileLoader using javac ./com/availity/*.java
2. Run syntax checker using java -cp . com.availity.SyntaxChecker
3. Choose how you want to validate LISP code:
   * Pass in an argument: "java -cp . com.availity.SyntaxChecker stringToCheckHere"
   * Press 1 to run all test cases placed in the Availity/TestFiles/LISP directory.
   * Press any other key, then input a string into the command line.
### CSV Enrollment
1. Compile EnrollmentHelper, Enrollee, and FileLoader using javac -cp ".;your/full/path/to/lib/commons-csv-1.6.jar" ./com/availity/*.java
2. Run EnrollmentHelper using java -cp ".;your/full/path/to/lib/commons-csv-1.6.jar" com.availity.EnrollmentHelper
   * This will run test cases located in the Availity/TestFiles/CSV directory.
   * Output will be stored in Availity/TestFiles/out/CSV folder.
   * EnrollmentHelper currently processes Excel formatted CSVs with headers.
