
all:	x	
	
x:	main.o rank.o
	gcc -o x main.o rank.o

main.o:	main.s
	gcc -c main.s

main.s:	main.c 
	gcc -S -O2 main.c

rank.o:	rank.s
	gcc -c rank.s

rank.s:	rank.c 
	gcc -S -O2 rank.c

clean:	
	rm -f *.o *.s x
