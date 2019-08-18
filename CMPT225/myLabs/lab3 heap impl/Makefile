all: test1 

test1: test1.o 
	g++ -o test1 test1.o 

test1.o: test1.cpp heap.h
	g++ -c test1.cpp 

clean:
	rm -f test1 *.o  

