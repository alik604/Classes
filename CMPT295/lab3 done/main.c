
#include <stdio.h>

int get_byte_by_addr(int *, int i);

int get_byte_by_order(int x, int i) {
    // complete this in Part 2.
   
	//int lowest_byte = x & 0xff;
	//	int byte1 = x & 0xff;
	//	printf(lowest_byte);
	//	printf(byte1);

	//int byte1 = (x>>i*8) & 0xff;


    return (x>>i*8) & 0xff;
    // end of Part 2.
}

/* done program's output
x = 0x0DF0AD8B
x = 233876875
y = 0xad5be1fe
y = 2908479998
x = 8badf00d
x = 8badf00d
y = fee15bad
y = fee15bad
*/
int x = 0x01234567;
unsigned int y =0xffffffd6;

void main () {
    // complete this in Part 1.
    printf("x = %#0.08X\n", x);//"%04x"
    printf("x = %d\n", x);
    printf("y = %#0.08x\n", y);
    printf("y = %u\n", y); // u for unsigned... NOT d 
    // end of Part 1

    int i;

    printf("x = ");
    for (i = 0; i < 4; i++) {
        printf("%02x", get_byte_by_order(x, i));
    }
    putchar('\n');

    printf("x = ");
    for (i = 0; i < 4; i++) {
        printf("%02x", get_byte_by_addr(&x, i));
    }
    putchar('\n');

    printf("y = ");
    for (i = 0; i < 4; i++) {
        printf("%02x", get_byte_by_order(y, i));
    }
    putchar('\n');

    printf("y = ");
    for (i = 0; i < 4; i++) {
        printf("%02x", get_byte_by_addr(&y, i));
    }
    putchar('\n');

    return;
}   