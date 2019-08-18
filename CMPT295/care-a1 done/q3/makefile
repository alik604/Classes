
x:	main.o mystery.o
	gcc -o x mystery.o main.o

main.o:	main.s
	gcc -c main.s

mystery.o:	mystery.s
	gcc -c mystery.s

main.s:	main.c
	gcc -O2 -S main.c

clean:	
	rm -f x *.o main.s
