#include <iostream>
#include <math.h> 
#include <boost/lexical_cast.hpp>
using namespace std;

bool isInt (double numb);

int main (void) {
  //  cout <<"Hello world!";
  
//string y ="";
//	cin >>y;
// convert to double and call it x 
/*try {
     x = boost::lexical_cast<int>( y );
} catch( boost::bad_lexical_cast const& ) {
    out << "nonint";
}
*/
	//int x;
	double x;
	cin >> x;
if(isInt(x)){
	//if(!cin.fail()){
	int xx=(int)x;

		if (xx %2 ==0)
			{ cout << "Even";}
		else if (xx % 2 ==1)
			{ cout << "Odd";}
	}else {
	cout << "Nonint";
	}
	cout<<endl;
return 0;
} 

	bool isInt (double numb) {
	// double z = abs(numb); //neg values
	return numb == int(numb);
	}


  
