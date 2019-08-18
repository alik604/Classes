/*************************************************************
   Test Program for Basic Heap Class - Preliminiary Version.
**************************************************************/
#include <iostream>
#include "heap.h"
using namespace std;

void heapTest();
 
int main(){
      heapTest();
      return 0;
}

void heapTest(){


// Test Heap(E,P,s,c)
//
//
bool OK = true;

int * ElementArr = new int[10];
int * PriorityArr = new int[10];

// Some priorities.
// (In an order that is not a heap, to help spot bugs.)
// The 999 is supposed to not end up in the heap.
PriorityArr[0] = 9;
PriorityArr[1] = 1;
PriorityArr[2] = 7;
PriorityArr[3] = 3;
PriorityArr[4] = 2;
PriorityArr[5] = 8;
PriorityArr[6] = 999;

// Some elements.
// (Numbered so that the last digit is the corresponding 
// priority, and the first digit is the order they appear,
// as a debugging aid.)
// The 999 is supposed to not end up in the heap.
ElementArr[0] = 109;
ElementArr[1] = 201;
ElementArr[2] = 307;
ElementArr[3] = 403;
ElementArr[4] = 502;
ElementArr[5] = 608;
ElementArr[6] = 999;

// A heap made from the first 6 elements of the two arrays.
Heap * H = new Heap( PriorityArr, ElementArr, 6, 10 );

if( H->size() != 6 )  OK = false ;
if( H->capacity() != 16 ) OK = false ;
if( H->peekMin() != 201 || H->peekMinPriority() != 1 ) OK = false ;
while( H->size() > 1 ){
   H->extractMin();
}
if( H->peekMin() != 109 || H->peekMinPriority() != 9 ) OK = false ;

cout << OK << endl ;


// Test Heap(H1,H2,c)
//

OK = true ;

// A heap.
Heap * H1 = new Heap(7);
H1->insert(92,2);
H1->insert(91,1);
H1->insert(94,4);
H1->insert(94,5);
H1->insert(93,3);

// A second heap.  Some priorities are distinct from 
// those in the first, and some are duplicates.
Heap * H2 = new Heap(8);
H2->insert(84,4);
H2->insert(86,6);
H2->insert(80,0);
H2->insert(88,8);
H2->insert(82,2);

// A heap containing the union of pairs from the first two heaps.
Heap * H3 = new Heap( *H1, *H2, 3 );

if( H3->size() != 10 ) OK = false ;
if( H3->capacity() != 13 ) OK = false ;
if( H3->peekMin() != 80 ) OK = false ;
if( H3->peekMinPriority() != 0 ) OK = false ;

cout << OK << endl ;
 
}

