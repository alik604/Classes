
#ifndef _MATRIX_H_
#define _MATRIX_H_

// make an empty matrix with r rows and c cols
int **newMatrix(int r, int c);


// print a Matrix to stdout
void printMatrix(int **M, int r, int c);


// reduce-reuse-recycle
void freeMatrix(int **M);


#endif // _MATRIX_H_

