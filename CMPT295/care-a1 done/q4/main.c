
#include <stdio.h>

unsigned times(unsigned, unsigned);

void main () {
    unsigned a = 10;
    unsigned b = 12;
    printf("The product of %u and %u is %u.\n", a, b, times(a,b));
    return;
}

