
all:	x

x:	main.o conv.o min.o conv_arr.o
	gcc -o x main.o conv.o min.o conv_arr.o

main.o:	main.s
	gcc -c main.s

main.s:	main.c
	gcc -O2 -S -fno-builtin main.c

conv_arr.o:	conv_arr.s
	gcc -g -c conv_arr.s

conv.o:	conv.s
	gcc -c conv.s

conv.s:	conv.c
	gcc -O2 -S -fno-builtin conv.c

min.o:	min.s
	gcc -c min.s

min.s:	min.c
	gcc -O2 -S -fno-builtin min.c

clean:	
	rm -f x *.o conv.s main.s min.s
