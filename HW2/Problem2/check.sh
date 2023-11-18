#!/usr/bin/ bash
echo "-compile start-"

# Compile
javac Solution2.java -encoding UTF8

start=$SECONDS

echo "-execute program-"
timeout 10 java Solution2

echo "Execution time : $((SECONDS - start)) seconds"

time java Solution2

cmp answer.txt output.txt
