# delete directories
rm -rf target && rm -rf lib
# create directories
mkdir target && mkdir lib

# download libraries
curl https://repo1.maven.org/maven2/com/beust/jcommander/1.72/jcommander-1.72.jar -o lib/jcommander-1.72.jar
curl https://repo1.maven.org/maven2/com/diogonunes/JCDP/4.0.0/JCDP-4.0.0.jar -o lib/JCDP-4.0.0.jar

# extract files and replace to directory target
cd target
jar xf ../lib/jcommander-1.72.jar
jar xf ../lib/JCDP-4.0.0.jar
rm -rf META-INF
cd ..

#compile files to target directory
#javac -d target -cp target src/java/edu/school21/printer/app/Program.java src/java/edu/school21/printer/logic/*.java
javac -d target -sourcepath src/java -cp lib/\* src/java/edu/school21/printer/app/Program.java src/java/edu/school21/printer/logic/*.java
# copy resources
cp -r src/resources ./target/resources

# create jar file
jar cfm target/images-to-chars-printer.jar src/manifest.txt -C target .

# run archive
java -jar target/images-to-chars-printer.jar --white=RED --black=GREEN
