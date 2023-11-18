#!/usr/bin/ bash
echo "-compile start-"

# Compile
javac Solution4.java -encoding UTF8

start=$SECONDS

echo "-execute program-"
timeout 2 java Solution4

echo "Execution time : $((SECONDS - start)) seconds"

time java Solution4

cmp answer.txt output.txt
