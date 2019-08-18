
#include <stdio.h>

char conv(char *, char *, int);
// char conv(char *x, char *h, int n);

#define N 20
#define M 3

char signal[N] = { 0, 0, 0, 4, 8, 6, 5, 6, 7, 4, 1, -2, -5, -7, -4, -2, 0, 0, 0, 0 };

char h[M] = { 1, -2, 1 };

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
        printf("%4d", conv(signal+(i-adj+1), h, adj));
    }
    putchar('\n');
    return;
}

