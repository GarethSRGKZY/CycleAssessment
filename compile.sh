mkdir bin
mkdir build
mkdir doc

javadoc -d doc/ src/cycling/*.java

javac -d bin/ src/cycling/*.java

jar cvf build/cycling.jar -C bin .
jar uvf build/cycling.jar -C src .
jar uvf build/cycling.jar doc
jar uvf build/cycling.jar res