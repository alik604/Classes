
all:	x	
	
x:	main.o mul.o matrix.o
	gcc -o x main.o mul.o matrix.o

main.o:	main.s
	gcc -c main.s

main.s:	main.c matrix.h mul.h
	gcc -S -O2 -fno-builtin main.c

matrix.o:	matrix.s
	gcc -c matrix.s

matrix.s:	matrix.c matrix.h
	gcc -S -O2 matrix.c

mul.o:	mul.s
	gcc -c mul.s

mul.s:	mul.c mul.h matrix.h
	gcc -S -O2 mul.c

clean:	
	rm -f *.o *.s x
