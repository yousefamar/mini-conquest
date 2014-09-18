all:
	javac -d bin -sourcepath src src/core/MiniConquest.java

clean :
	find . -name "*.class" -type f -delete
