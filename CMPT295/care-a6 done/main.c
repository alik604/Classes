// khizr ali pardhan 301314376
int lsearch_1(int *A, int n, int target);

#include "lsearch_2.h"
#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/times.h>
#include <sys/time.h>
#include <sys/resource.h>

#define N 5000000 
#define NTESTS 400

int A[N];

void main () {
    int last;
    int second_last;
    int (*searches[2])(int *, int, int) = { lsearch_1, lsearch_2 };
    int results[2][NTESTS];
    int i, j;
    int pos;
    int tmp;
    struct rusage start;
    struct rusage end;


    // Init array
    puts("Initializing array . . .");
    A[i] = 0;
    srand(time(NULL));
    for (i = 1; i < N; i++) {
        A[i] = 3*i + ((i % 3) == 0);
        pos = (((rand() % 0x7fff) << 16) | (rand() % 0xffff)) % (i+1);
        tmp = A[i];
        A[i] = A[pos];
        A[pos] = tmp;
    }
    last = A[N];
    second_last = A[N-1];
    puts("Array initialized. . .");


    // Test two functions
    for (j = 0; j < 2; j++) {
        printf("Starting test of function %d . . .\n", j+1);
        getrusage(RUSAGE_SELF, &start);
        for (i = 0; i < NTESTS; i++) {
            results[j][i] = searches[j](A, N, i);
            if (last != A[N]) {
                perror("Error: A[N] not consistent.\n"); return;
            } else if (second_last != A[N-1]) {
                perror("Error: A[N-1] not consistent.\n"); return;
            }
        }
        getrusage(RUSAGE_SELF, &end);
        printf("It took %ld microseconds to run function %d.\n", (end.ru_utime.tv_sec - start.ru_utime.tv_sec) * 1000000 + (end.ru_utime.tv_usec - start.ru_utime.tv_usec), j+1);
    }


    // Display aggregate stats
    printf("Totalling work %d . . .\n", j+1);
    long total_hit[2] = {0, 0};
    long total_miss[2] = {0, 0};
    for (i = 0; i < NTESTS; i++) {
        for (j = 0; j < 2; j++) {
            if (results[j][i] == -1) {
                total_miss[j] += 1;
                total_hit[j] += N;
            } else {
                total_hit[j] += results[j][i];
            }
        }
        if (results[0][i] != results[1][i]) {
            perror("Error: Results of searches disagree.\n"); return;
        }
    }
    printf("Total misses = %ld, %ld\n", total_miss[0], total_miss[1]);
    printf("Total of positions searched = %ld, %ld\n", total_hit[0], total_hit[1]);

    return;
}


int lsearch_1(int *A, int n, int target) {
    int i;
    for (i = 0; i < n; i++) {
        if (A[i] == target) {
            return i;
        }
    }
    return -1;
}  // lsearch_1


