
#include <stdio.h>

unsigned int sqrt(unsigned int);

void main () {
    unsigned int i;
    for (i = 0; i < 30; i++) {
        printf("The integer square root of %u is %u.\n", i, sqrt(i));
    }
    return;
}

