
all:	x	
	
x:	main.o sum_plus.o
	gcc -o x main.o sum_plus.o

main.o:	main.s
	gcc -c main.s

main.s:	main.c 
	gcc -S -O2 -fno-builtin main.c

sum_plus.o:	sum_plus.s
	gcc -c sum_plus.s

clean:	
	rm -f *.o main.s x
