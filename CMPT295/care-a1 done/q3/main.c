
#include <stdio.h>

int mystery(char *, int);

char *str = "Good mood food.";

void main () {
    for(int n=0;n<16;n++)
    printf(" n= %d:%d\n",n, mystery(str,n));
    return;
}
