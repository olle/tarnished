##
## Stay cheap. Greedy. Stay hungry. There's plenty of time later to bulk up on
## heap space.
##
MVN_OPTS  := -Xms100m -Xmx200m
MVN       ?= $(shell pwd)/mvnw

##
## What can I say. I do enjoy that one-command feel, as I swiftly type `make`
## and press the Enter key. A rush and warm feeling spreading through by body,
## as the terminal scroll by and the familiar startup ASCII logo rolls by...
##
## ..starting!
##
## Just run `make`.
##
.PHONY: start
start:
	$(MVN) spring-boot:run

##
## We never, and I mean NEVER do `mvn clean install`, there's just no real
## reason - since we're building a deployable app. Duh!
##
.PHONY: test
test:
	$(MVN) clean verify
