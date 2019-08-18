/**
 * Created by kali on 2/12/2017.
 */
var Xpoint = [0, 1, 2, 3, 4, 5];
var Ypoint = [];
var usersdemands = prompt("give me your demands");
   // "Math.pow(Xpoint[pass], 2) + 1";


console.log("X:Y");
//the math y = x^2 +1
for (var pass = 0; pass < Xpoint.length; pass++) {
    Ypoint[pass] = eval(usersdemands);
    console.log(Xpoint[pass] + "," + Ypoint[pass]);
}

/*
 //print x array
 for (var x = 0; x < Xpoint.length; x++)
 console.log(Xpoint[x] + " ");

 console.log("////////////////////")
 //print y array
 for (var y = 0; y < Ypoint.length; y++)
 console.log(Ypoint[y] + " ");


 */