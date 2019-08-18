
all:	x bonux

x:	main.o mul.o
	gcc -o x mul.o main.o

bonux:	main.o mul-bonus.o
	gcc -o bonux mul-bonus.o main.o

main.o:	main.s
	gcc -c main.s

mul.o:	mul.s
	gcc -c mul.s

mul-bonus.o:	mul-bonus.s
	gcc -c mul-bonus.s

main.s:	main.c
	gcc -O2 -S main.c

clean:	
	rm -f x bonux *.o main.s
