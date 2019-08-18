
#include "output.h"
#include <stdio.h>


char *bitstr(unsigned long x, char *buf, int n) {
    buf[n] = 0;
    while (n--) {
        buf[n] = '0' + x%2;
        x = x/2;
    }
    return buf;
} // bitstr


void f_printbits(float f) {
    unsigned equiv = *((int *)&f);
    printf("%8x", equiv);
} // f_printbits


void f_print(float f) {
    char mant_buf[24];
    char sign_char;
    unsigned equiv = *((int *)&f);
    unsigned sign, exp, mant;

    // unpack number
    sign = equiv >> 31; sign_char = sign ? '-' : '+';
    exp = (equiv >> 23) & 0xff;
    mant = equiv & 0x7fffff;

    // display
    if (exp == 0xff) {  // special values
        printf("%e\n", f);
    } else if (exp == 0) { // denormalized values
        printf("%e = %c 0.%s x 2^-126\n", f, sign_char, bitstr(mant, mant_buf, 23));
    } else { // normalized values
        printf("%e = %c 1.%s x 2^%d\n", f, sign_char, bitstr(mant, mant_buf, 23), ((int)exp) - 127);
    }
} // f_print

