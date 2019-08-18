
long *new_42(void) {
   static  long x;
    long *ret;

    x = 42;
    ret = &x;

    return ret;
} // new_42

long N = 10;

void donotmuchofanything(long *a) {
    if (N--) {
        donotmuchofanything(a);
        N++;
    }
} // donotmuchofanything

