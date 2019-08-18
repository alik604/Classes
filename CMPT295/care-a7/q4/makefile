
all:	x

x:	main.o dynamic.o
	gcc -o x main.o dynamic.o

main.s:	main.c
	gcc -S main.c

main.o:	main.s
	gcc -c main.s

dynamic.s:	dynamic.c
	gcc -S dynamic.c

dynamic.o:	dynamic.s
	gcc -c dynamic.s

clean:	
	rm -f *.o *.s x
