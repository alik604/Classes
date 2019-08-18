#include <cstdlib>
#include <iostream>
#include "StringLinkedList.h"
#include <vector>



using namespace std;

int main() {
    cout << "khizr ali pardhan" << endl;
    cout << "kpardhan" << endl;
    cout << "301314376" << endl;

    StringLinkedList myList;


    string str = "rand";
	vector<string> array;
    string history ="";



    while (!str.empty()) {
        getline(cin,str);

         if(str.empty()){ //prevent 1 extra count
            break;
         }
      
        myList.addFront(str);
       history += myList.front(); 

        

    }
        cout << endl;
        cout << myList.getSize() << endl;// white board does not include this
        cout<< history <<endl;
        cout << endl;

    vector<string> v;

    while(!myList.empty()){
        v.push_back(myList.front());
        myList.removeFront();
    }
    for(int i=0; i< v.size(); i++){
        cout<<v[i]<<endl;
    }
            cout << endl;

    cout << "goodbye";//not on whit board


    return 0;
}