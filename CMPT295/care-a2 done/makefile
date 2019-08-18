
all:	x bonux

x:	main.o sqrt.o
	gcc -o x sqrt.o main.o

bonux:	main.o sqrtrd.o
	gcc -o bonux sqrtrd.o main.o

main.o:	main.s
	gcc -c main.s

sqrt.o:	sqrt.s
	gcc -g -c sqrt.s

sqrtrd.o:	sqrtrd.s
	gcc -g -c sqrtrd.s

main.s:	main.c
	gcc -fno-builtin -O2 -S main.c

clean:	
	rm -f x bonux *.o main.s
