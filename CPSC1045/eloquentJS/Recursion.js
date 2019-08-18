/**
 * Created by kali on 2/4/2017.
 */

var isEven = function (n) {
    if (n < 0)
        n *= -1;
    if (n == 1)
        return false;
    if (n == 0)
        return true;

    if (n >= 2)
        return isEven(n - 2)
}


console.log(isEven(80));