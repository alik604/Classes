
all:	x	
	
x:	main.o lsearch_2.o
	gcc -o x main.o lsearch_2.o

main.o:	main.s
	gcc -c main.s

main.s:	main.c lsearch_2.h
	gcc -S -O2 -fno-builtin main.c

lsearch_2.o:	lsearch_2.s
	gcc -c lsearch_2.s

lsearch_2.s:	lsearch_2.c lsearch_2.h
	gcc -S -O2 lsearch_2.c

clean:	
	rm -f *.o *.s x
