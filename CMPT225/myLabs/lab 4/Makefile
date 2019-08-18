all: a4 

a4: a4.o 
	g++ -o a4 a4.o 

a4.o: a4.cpp heap.h queue.h
	g++ -c a4.cpp 

clean:
	rm -f a4 *.o 