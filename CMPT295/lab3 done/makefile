
all:	x

x:	main.o get_byte_by_addr.o
	gcc -o x get_byte_by_addr.o main.o

main.o:	main.s
	gcc -c main.s

get_byte_by_addr.o:	get_byte_by_addr.s
	gcc -g -c get_byte_by_addr.s

main.s:	main.c
	gcc -fno-builtin -O2 -S main.c

clean:	
	rm -f x *.o main.s
