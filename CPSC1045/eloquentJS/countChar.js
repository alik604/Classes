/**
 * Created by kali on 2/4/2017.
 */
var counter = 0;
var countBs = function (str) {

    for (var x = 0; x < str.length - 1; x++) {

        if (str.charAt(x) == "B")
            counter++;
    }
    return counter;
}
console.log(countBs("BBbb"));