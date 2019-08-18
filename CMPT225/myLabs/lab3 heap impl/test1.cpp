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

/*
void Show( Heap & H, string s ){
      cout << s << ".capacity=" << H.capacity() << endl;
      cout << s << ".size=" << H.size() << endl;
      cout << s << "=" ; H.display(); cout << endl;
      cout << "-----------------------\n";
} 
*/
 
void heapTest(){

      // Test default constructor and basic functions 
      Heap H;
 
      H.insert(91,7);
      H.insert(92,6);
      H.insert(93,8);
      H.insert(94,5);
      H.insert(95,9);
      while( !H.empty() ){
        cout << "min priority: " << H.peekMinPriority() << endl;
        cout << "min priority element: " << H.peekMin() << endl;
        H.extractMin();
      }
 
}


