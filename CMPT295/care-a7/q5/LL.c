
#include "LL.h"
#include <stdio.h>
#include <stdlib.h>

typedef struct __llnode {
    int el;
    struct __llnode *next;
} llnode;



List *newLL(void) {
    List *ret = malloc(sizeof(List));
    if (ret) {
        ret->len = 0;
        ret->head = NULL;
        ret->tail = NULL;
    }
    return ret;
} // newLL



void freeLL(List *L) {
    if (!L) return;
    llnode *curr = (llnode *)(L->head);
    llnode *next;
    while (curr) {
        next = curr->next;
        free(curr);
        curr = next;
    }
    free(L);
} // freeLL



void appendLL(List *L, int el) {
    if (!L) return;

    llnode *newnode = malloc(sizeof(llnode));
    if (!newnode) return;  // fail
    newnode->el = el;
    newnode->next = NULL;
    L->len++;
    if (L->head == NULL && L->tail == NULL) {
        L->head = (void *)newnode;
    } else {
        ((llnode *)(L->tail))->next = newnode;
    }
    L->tail = (void *)newnode;
} // appendLL



int LLsearch(List *L, int target) {
    int pos = 0;
    llnode *curr = (llnode *)(L->head);

    while (curr && curr->el != target) {
        curr = curr->next;
        pos++;
    }
    if (curr) return pos;
    return -1;
} // LLsearch



void LLdump(List *L) {
    if (!L) { putchar('\n'); return; } 
    llnode *curr = (llnode *)(L->head);
    while (curr) {
        printf(" %d", curr->el);
        curr = curr->next;
    }
    putchar('\n');
} // LLdump



int lsearch(int *A, int n, int target) {
    int i;
    for (i = 0; i < n; i++) {
        if (A[i] == target) return i;
    }
    return -1;
} // lsearch

