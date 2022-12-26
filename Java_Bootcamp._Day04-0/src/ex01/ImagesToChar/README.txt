# deleting the directory
rm -rf target

# creating new dir
mkdir target

# set destination directory for class files
javac -d ./target -sourcepath src/java src/java/edu/school21/printer/app/Program.java

# copy resources from src to target
cp -R src/resources target/.

# create archive
jar cfm ./target/images-to-chars-printer.jar src/manifest.txt -C target .

# access to archive
chmod u+x target/images-to-chars-printer.jar

# run archive
java -jar target/images-to-chars-printer.jar . 0