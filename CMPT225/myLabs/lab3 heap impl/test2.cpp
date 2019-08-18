/*************************************************************
   Test Program for Basic Heap Class
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
 
      bool OK ;

      // Test TrickleUp 
      // Use: default constructor, insert, peekMin, and peekMinPriority.
      Heap H;
      OK = true ;

      H.insert(91,7);
      if( H.peekMin() != 91 || H.peekMinPriority() != 7 ) OK = false ;
      H.insert(92,6);
      if( H.peekMin() != 92 || H.peekMinPriority() != 6 ) OK = false ;
      H.insert(94,5);
      if( H.peekMin() != 94 || H.peekMinPriority() != 5 ) OK = false ;
      H.insert(93,8);
      H.insert(95,9);
      H.insert(85,10);
      H.insert(84,12);
      if( H.peekMin() != 94 || H.peekMinPriority() != 5 ) OK = false ;
      H.insert(83,4);
      if( H.peekMin() != 83 || H.peekMinPriority() != 4 ) OK = false ;
      H.insert(82,6);
      H.insert(81,3);
      if( H.peekMin() != 81 || H.peekMinPriority() != 3 ) OK = false ;

      if( H.size() != 10 ) OK = false ;

      cout << OK << endl ;

      // Test extractMin
      // Use: insert, peekMin, peekMinPriority, extractMin
      Heap * HH = new Heap();
      OK = true ;
      int x ;

      HH->insert(91,7);
      HH->insert(92,6);
      HH->insert(94,5);
      HH->insert(93,8);
      HH->insert(95,9);
      HH->insert(85,10);
      HH->insert(84,12);
      HH->insert(83,4);
      HH->insert(82,6);
      HH->insert(81,3);

      // 3
      if( HH->peekMin() != 81 || HH->peekMinPriority() != 3 ) OK = false ;
      x = HH->extractMin();      
      if( x != 81 ) OK = false ;
      if( HH->size() != 9 ) OK = false ;

      // 4
      if( HH->peekMin() != 83 || HH->peekMinPriority() != 4 ) OK = false ;
      x = HH->extractMin();      
      if( x != 83 ) OK = false ;
      if( HH->size() != 8 ) OK = false ;

      // 5
      if( HH->peekMin() != 94 || HH->peekMinPriority() != 5 ) OK = false ;
      x = HH->extractMin();      
      if( x != 94 ) OK = false ;
      if( HH->size() != 7 ) OK = false ;

      // 6, 6
      if( HH->peekMinPriority() != 6 ) OK = false ;
      x = HH->extractMin();      
      if( HH->size() != 6 ) OK = false ;
      x = HH->extractMin();      
      if( HH->size() != 5 ) OK = false ;

      // 7
      if( HH->peekMin() != 91 || HH->peekMinPriority() != 7 ) OK = false ;
      x = HH->extractMin();      
      if( x != 91 ) OK = false ;
      if( HH->size() != 4 ) OK = false ;

      // 8 
      if( HH->peekMin() != 93 || HH->peekMinPriority() != 8 ) OK = false ;
      x = HH->extractMin();      
      if( x != 93 ) OK = false ;
      if( HH->size() != 3 ) OK = false ;

      // 9
      if( HH->peekMin() != 95 || HH->peekMinPriority() != 9 ) OK = false ;
      x = HH->extractMin();      
      if( x != 95 ) OK = false ;
      if( HH->size() != 2 ) OK = false ;

      // 10 
      if( HH->peekMin() != 85 || HH->peekMinPriority() != 10 ) OK = false ;
      x = HH->extractMin();      
      if( x != 85 ) OK = false ;
      if( HH->size() != 1 ) OK = false ;

      // 12 
      if( HH->peekMin() != 84 || HH->peekMinPriority() != 12 ) OK = false ;
      x = HH->extractMin();      
      if( x != 84 ) OK = false ;
      if( HH->size() != 0 ) OK = false ;

      cout << OK << endl ;

      // Test Heap(c) constructor.
      OK = true ;      
      Heap HHH(25);
      if( HHH.capacity() != 25 ) OK = false ;
      if( HHH.size() != 0 ) OK = false ;

      for( int i = 0 ; i < 25 ; i++ ){
          HHH.insert(i,i);
      }
      if( HHH.size() != 25 ) OK = false ;
      for( int i = 0 ; i < 25 ; i++ ){
          HHH.extractMin();
      }
      if( HHH.size() != 0 ) OK = false ;

      cout << OK << endl ;
}


