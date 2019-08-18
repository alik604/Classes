
all:	x	
	
x:	main.o LL.o
	gcc -o x main.o LL.o

main.o:	main.s
	gcc -c main.s

main.s:	main.c LL.h 
	gcc -S -O2 -fno-builtin main.c

LL.o:	LL.s
	gcc -c LL.s

LL.s:	LL.c LL.h
	gcc -S -O2 LL.c

clean:	
	rm -f *.o main.s x
