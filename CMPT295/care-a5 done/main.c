
#include "output.h"
#include <stdio.h>
#include <stdlib.h>

float sum_float(float *, int);



// bitwise convert unsigned(32) to float(32)
float u2f(unsigned x) {  
    return *((float *)&x);
} // u2f



// 3(a):  insert supporting code for qsort(.., .., .., ..) here:
int qsortFunc(const void * a, const void * b) {
// https://www.tutorialspoint.com/c_standard_library/c_function_qsort.htm
    return ( *(int*)a - *(int*)b ); 
}
   // https://stackoverflow.com/questions/27284185/how-does-the-compare-function-in-qsort-work
   // qsort() expects the comparison function to return:
   // 
   //    a negative result if val1 < val2
   //    0 if val1 == val2
   //    a positive result if val1 > val2




void main () {
    float arr1[24], arr2[50];
    float tot1, tot2;
    int i;

    //.-.-.-.-.-.-.-.-.-.-.-.-.-.//
    //. . .   Test case 1   . . .//
    //.-.-.-.-.-.-.-.-.-.-.-.-.-.//
    puts("Test Case 1:\n");
    arr1[0] = u2f(0x5060ffff);      // 3(b):  change this value
    tot1 = arr1[0];
    for (i = 1; i < 24; i++) {
        arr1[i] = u2f(0x46180000);  // 3(b):  change this value
        tot1 += arr1[i];
    }
    printf("The total before sorting: ");
    f_printbits(tot1); putchar('\n');

    printf(" The total after sorting: ");
    // 3(a):  insert code for sorting of arr1[] here:
    qsort(arr1, 24, sizeof(float), qsortFunc);//<---------------
    // qsort(arr1, .., .., ..);
    f_printbits(sum_float(arr1, 24)); putchar('\n');


    //.-.-.-.-.-.-.-.-.-.-.-.-.-.//
    //. . .   Test case 2   . . .//
    //.-.-.-.-.-.-.-.-.-.-.-.-.-.//
    puts("\nTest Case 2:\n");
    tot2 = 0.0;
    for (i = 0; i < 50; i++) {
        arr2[i] = 0.1 * ((float)(1 + (i % 3 == 0) + (i % 7 == 0)));
        tot2 += arr2[i];
    }
    printf("The total before sorting: ");
    f_printbits(tot2); putchar('\n');

    printf(" The total after sorting: ");
    // 3(a):  insert code for sorting of arr2[] here:
    // qsort(arr2, .., .., ..);
        qsort(arr2, 50, sizeof(float), qsortFunc);
    f_printbits(sum_float(arr2, 50)); putchar('\n');

    puts("");
    return;
}

