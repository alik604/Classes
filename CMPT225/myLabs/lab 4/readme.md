# 301314376 kpardhan khizr pardhan

## CMPT 225 Assignment 4: List Difference Application

Given: Two lists of integers L1 and L2;
Output: The largest number that occurs in L1 but does not occur in L2, or "None" if there is no such number.



###algorithm:

used heap and queue.

###explanation: 

while heap is not empty
	let x be max elem
	if queue is empty, print x
	otherwise, if queue does not contain x, print x

it's not of the above cases.... print "NONE"  (in caps) 

### usage:

g++ a4.cpp
./a.out myTestFile.txt

---

make a4
./a.out myTestFile.txt
