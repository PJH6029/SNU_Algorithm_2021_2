#!/usr/bin/ bash
echo "-compile start-"

# Compile
javac Solution1.java -encoding UTF8

start=$SECONDS

echo "-execute program-"
timeout 1 java Solution1

echo "Execution time : $((SECONDS - start)) seconds"

time java Solution1

cmp answer.txt output.txt
