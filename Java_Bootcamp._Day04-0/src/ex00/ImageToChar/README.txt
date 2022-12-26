# deleting the directory
rm -rf target

# creating new dir
mkdir target

# set destination directory for class files
javac -d ./target -sourcepath src/java src/java/edu/school21/printer/app/Program.java

# specify where to find user class files
java -classpath target edu.school21.printer.app.Program . 0 /Users/lymondgl/Desktop/Java_Bootcamp._Day04-0/src/ex00/it.bmp
