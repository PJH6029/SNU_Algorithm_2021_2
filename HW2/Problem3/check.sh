#!/usr/bin/ bash
echo "-compile start-"

# Compile
javac Solution3.java -encoding UTF8

start=$SECONDS

echo "-execute program-"
timeout 2 java Solution3

echo "Execution time : $((SECONDS - start)) seconds"

time java Solution3

cmp answer.txt output.txt
