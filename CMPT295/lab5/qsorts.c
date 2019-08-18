
#include "qsorts.h"

int partition_1(int *A, int n);
int partition_2(int *A, int n);


void qsort295_1(int *A, int n) {
    int q;
    if (n <= 1) return;
    q = partition_1(A, n);
    qsort295_1(A, q);
    qsort295_1(A + q + 1, n - q - 1);
}  // qsort295_1  (Hoare Partition)


void qsort295_2(int *A, int n) {
    int q;
    if (n <= 1) return;
    q = partition_2(A, n);
    qsort295_2(A, q);
    qsort295_2(A + q + 1, n - q - 1);
}  // qsort295_2  (Lomuto Partition)


void swap(int *x, int *y) {
    int tmp = *x;
    *x = *y;
    *y = tmp;
} // swap


int partition_1(int *A, int n) {
    swap(A, A+(n/2));
    int x = A[0];
    int i = 0;
    int j = n;
    while (1) {
        while (i < n && A[++i] < x);
        while (A[--j] > x);
        if (i < j) swap(A+i, A+j); else break;
    } // while

    swap(A, A+j);
    return j;
}  // partition_1


int partition_2(int *A, int n) {
    int x = A[--n];
    int i = 0;
    int j;
    for (j = 0; j < n; j++) 
        if (A[j] <= x) 
            swap(A + i++, A+j);
    swap(A+i, A+n);
    return i;
}  // partition_2


