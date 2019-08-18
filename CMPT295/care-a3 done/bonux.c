
#include <stdio.h>

char conv(char *, char *, int);

#define N 20
#define M 5

char signal[N] = { 0, 0, 0, 40, 80, 60, 50, 60, 70, 40, 10, -20, -50, -70, -40, -20, 0, 0, 0, 0 };

char h[M] = { -5, 2, 6, 2, -5 };

void main () {
    int i;

    puts("Original signal:  ");
    for (i = 0; i < N; i++) {
        printf("%4d", signal[i]);
    }
    putchar('\n');

    puts("Convolved signal: ");
    for (i = 0; i < N; i++) {
        int adj = (i < M-1) ? i + 1 : M;
        int res;
        int OF;

        res = conv(signal+(i-adj+1), h, adj);
        asm volatile (
            "movl %%edx, %0\n\t"
            : "=r" (OF)
            :
            : "rdx"
            );

        if (OF) {
            printf("   OF");
        } else {
            printf(" %4d", res);
        }
    }
    putchar('\n');
    return;
}

