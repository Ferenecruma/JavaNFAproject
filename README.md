# Java CLU (NFA to DFA converter)

Usage:  java fileread fileIn fileOut

Input: absolute path to text file with NFA representation.

Example:
10 - size of the alphabet 
10 - number of states
0 - start state
2 - final state

Transitions in the following format:
0 a 0 1
0 b 0
1 b 2

Output: absolute path to text file with DFA representation.

Example:
10
10
0
2
0 a 0 1
0 b 0
1 b 2
