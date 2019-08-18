/**
 * Created by kali on 3/12/2017.
 */
var c = document.getElementById("myCanvas");
var ctx = c.getContext("2d");

var color;
//var color = "white";
// line 15 in html, onchange is issue, what would be more ideal?
var radius = -1;

var setRadius = function () {
    radius = document.getElementById("getRadius").value;
}
var setColor = function () {
    color = document.getElementById("getColor").value;
    console.log(color);
}
var draw = function () {
    ctx.beginPath();
    ctx.rect(0, 0, c.height, c.width);
    ctx.fillStyle = "white";
    ctx.fill();
    if (radius < 0 || radius >= 150) {
        alert("error: invalid size, setting size to 50");
        radius = 50;
        document.getElementById("getRadius").value = 50;
    }
    ctx.beginPath();
    ctx.fillStyle = color;
    ctx.arc(c.width / 2, c.height / 2, radius, 0, Math.PI * 2);
    ctx.fill();

    document.getElementById("drawing_is").innerHTML = "your drawing is: is a " + color + " circle with a radius of " + radius + "px";
}
