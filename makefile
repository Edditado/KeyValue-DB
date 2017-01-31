compileCPath = ":libs/thrift.jar:libs/slf4j.jar:libs/log4j.jar:libs/slflog.jar"
runCPath = ":build:libs/thrift.jar:libs/slf4j.jar:libs/log4j.jar:libs/slflog.jar"

compile:
	javac -cp $(compileCPath) -d build/ *.java

server:
	java -cp $(runCPath) Server $(filter-out $@,$(MAKECMDGOALS))

client:
	java -cp $(runCPath) Client $(filter-out $@,$(MAKECMDGOALS))