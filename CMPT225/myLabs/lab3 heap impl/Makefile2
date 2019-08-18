
all: test2 test3 test4

test2: test2.o 
	g++ -o test2 test2.o 

test3: test3.o
	g++ -o test3 test3.o 

test4: test4.o
	g++ -o test4 test4.o 

test2.o: test2.cpp heap.h
	g++ -c test2.cpp 

test3.o: test3.cpp heap.h
	g++ -c test3.cpp 

test4.o: test4.cpp heap.h
	g++ -c test4.cpp 

clean:
	rm -f test2 test3 test4 *.o  

