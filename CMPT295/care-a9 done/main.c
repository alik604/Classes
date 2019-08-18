
#include "matrix.h"
#include "mul.h"
#include <stdio.h>
#include <stdlib.h>


 // 512 640 768 896 1024 
#define M 1024   
#define N 1024

#define NTESTS 10


long total;

void main () {
    srand(time(NULL));

    int **A = newMatrix(M, N);
    int **B = newMatrix(N, M);

    int i, j;
    long trace = 0;


    // Build matrix
    for (i = 0; i < M; i++) {
        for (j = 0; j < N; j++) {
            A[i][j] = rand() % 10 - 5;
            B[j][i] = A[i][j];
        }
    }


    //printMatrix(A, M, N);
    //printMatrix(B, N, M);
    

    for (j = 0; j < NTESTS; j++) {
        int **C = mul(A, M, N, B, N, M);

        //printMatrix(C, M, M);

        for (i = 0; i < M; i++) {
            trace += C[i][i];
        }
        freeMatrix(C);
    }
    printf("The trace is %ld.\n", trace);

    freeMatrix(A);
    freeMatrix(B);
    return;
}

