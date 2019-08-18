/**
 * Created by kali on 2/4/2017.
 */
var size =8;
var white= true;
var str="";
for ( var x=0; x < size*size;x++) {

    white = !white;
    if (x%size ==0&&x!=0) {
        str+="\n";
        white = !white;
    }

    if(white==true)
        str+="#";
    else
        str +="_";
}

console.log(str);

