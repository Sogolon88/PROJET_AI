# Makefile - La Percée (Breakthrough)
# Binôme : KEITA Fodé Laye / CRISOSTO Jordan

SRC_DIR  = src
BIN_DIR  = bin
LIB_DIR  = lib
TEST_DIR = test
JAR_NAME = Keita_Crisosto.jar

MAIN    = main.AppMain
TOURNOI = tournoi_ai.Tournoi

JAVAC = javac
JAVA  = java
JAR   = jar

CLASSPATH = $(LIB_DIR)/*

.PHONY: all compile jar run tournoi test clean

## Par défaut : compiler et générer le JAR
all: jar

## Compilation des sources
compile:
	$(JAVAC) -d $(BIN_DIR) -cp "$(CLASSPATH)" -sourcepath $(SRC_DIR) $(SRC_DIR)/main/AppMain.java

## Générer le JAR exécutable
jar: compile
	$(JAR) cfm $(JAR_NAME) MANIFEST.MF -C $(BIN_DIR) .
	@echo "JAR généré : $(JAR_NAME)"
	@echo "Lancer avec : java -jar $(JAR_NAME)"

## Lancer le jeu
run: jar
	$(JAVA) -jar $(JAR_NAME)

## Lancer le tournoi IA
tournoi: compile
	$(JAVA) -cp "$(BIN_DIR)" $(TOURNOI)

## Compiler et lancer les tests JUnit
test:
	$(JAVAC) -d $(BIN_DIR) -cp "$(CLASSPATH)" -sourcepath "$(SRC_DIR);$(TEST_DIR)" $(SRC_DIR)/main/AppMain.java $(TEST_DIR)/*.java
	$(JAVA) -cp "$(BIN_DIR);$(CLASSPATH)" org.junit.platform.console.ConsoleLauncher execute --select-package test

## Nettoyer
clean:
	rm -rf $(BIN_DIR)/main $(BIN_DIR)/algorithmes $(BIN_DIR)/tournoi_ai $(BIN_DIR)/test $(JAR_NAME)
