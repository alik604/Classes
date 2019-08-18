
all:	x

x:	main.o sum_float.o output.o
	gcc -o x sum_float.o output.o main.o

main.o:	main.s
	gcc -c main.s

main.s:	main.c
	gcc -O2 -S main.c

output.o:	output.s
	gcc -c output.s

output.s:	output.c
	gcc -O2 -S output.c

sum_float.o:	sum_float.s
	gcc -g -c sum_float.s

clean:	
	rm -f x *.o main.s output.s
