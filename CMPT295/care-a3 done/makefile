
all:	x bonux

x:	main.o conv.o 
	gcc -o x main.o conv.o

bonux:	bonux.o conv.o 
	gcc -o bonux bonux.o conv.o

main.o:	main.s
	gcc -c main.s

main.s:	main.c
	gcc -O2 -S main.c

conv.o:	conv.s
	gcc -g -c conv.s

bonux.o:	bonux.s
	gcc -c bonux.s

bonux.s:	bonux.c
	gcc -O2 -S bonux.c

clean:	
	rm -f x bonux *.o main.s bonux.s
