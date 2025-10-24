#!/bin/bash

echo "=== COMPILATION ET EXECUTION DES TESTS ==="
echo

echo "1. Compilation du projet..."
mvn clean compile

if [ $? -ne 0 ]; then
    echo "ERREUR: La compilation a échoué!"
    exit 1
fi

echo
echo "2. Exécution des tests..."
echo

echo "=== Test simple (TestApp) ==="
mvn exec:java -Dexec.mainClass="mon.projet1.test.TestApp"

echo
echo "=== Test complet (TestGestionProjets) ==="
mvn exec:java -Dexec.mainClass="mon.projet1.test.TestGestionProjets"

echo
echo "=== TESTS TERMINÉS ==="


