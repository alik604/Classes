/**
 * Created by kali on 2/16/2017.
 */
function short(data) {
    var len = data.length;
    do {
        for (var i = 0; i < len - 1; i++) {

            if (data[i] > data[i + 1]) {
                swap(data, i, i + 1);

            }
        }
    } while (len--);
    return data;
}
function swap(arr, a, b) {
    var tmp = arr[a]
    arr[a] = arr[b];
    arr[b] = tmp;
}
console.log(short([3, 100, 10, -1, 2, 7, 1]))
