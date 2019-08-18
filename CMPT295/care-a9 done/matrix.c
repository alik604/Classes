
#include "matrix.h"
#include <stdio.h>
#include <stdlib.h>


int **newMatrix(int r, int c) {
    int **ret = (int **)malloc(r * sizeof(int*));
    if (!ret) return NULL;
    
    int *data = (int *)malloc(r * c * sizeof(int));
    if (!data) { free(ret); return NULL; }

    int i;
    for (i = 0; i < r; i++) {
        ret[i] = data + (i * c);
    }

    return ret;
} // newMatrix



void printMatrix(int **M, int r, int c) {
    int i, j;

    for (i = 0; i < r; i++) {
        for (j = 0; j < c; j++) {
            printf(" %3d", M[i][j]);
        }
        putchar('\n');
    }
    putchar('\n');
} // printMatrix



void freeMatrix(int **M) {
    free(*M);
    free(M);
} // freeMatrix


