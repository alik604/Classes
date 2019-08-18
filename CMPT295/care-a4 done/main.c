
#include <stdio.h>

void conv_arr(char *, int, char *, int, char *);
// void conv_arr(char *x, int n, char *h, int m, char *result);


#define N 15
#define M 3

char signal[N] = { 0, 4, 8, 6, 5, 6, 7, 4, 1, -2, -5, -7, -4, -2, 0 }; 
char h[M] = { 1, 2, 3 };

void main () {
    int i;
    char result[N+M+7];
    for (i = 0; i < N+M+7; i++) {
        result[i] = -128;
    }

    puts("Original signal:  ");
    for (i = 0; i < N; i++) {
        printf("%4d", signal[i]);
    }
    putchar('\n');
    putchar('\n');

    //conv_arr(h, M, signal, N, result+4);
    conv_arr(signal, N, h, M, result+4);
    puts("Convolved signal: ");
    for (i = 0; i < N+M+7; i++) {
        printf("%4d", result[i]);
    }
    putchar('\n');
    putchar('\n');

    return;
}

