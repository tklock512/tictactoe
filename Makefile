#Makefile for tictactoe server and game

#Compiler info	
JFLAGS = -g
JC = javac

.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	userClient.java \
	Server.java \
	AIClient.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class
