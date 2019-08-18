
all:	x	
	
x:	main.o qsorts.o 
	gcc -o x main.o qsorts.o

main.o:	main.s
	gcc -c main.s

main.s:	main.c qsorts.h
	gcc -S -O2 -fno-builtin main.c

qsorts.o:	qsorts.s
	gcc -c qsorts.s

qsorts.s:	qsorts.c qsorts.h
	gcc -S -O2 qsorts.c

clean:	
	rm -f *.o *.s x
