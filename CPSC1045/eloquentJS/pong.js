var canvas = document.getElementById("myCanvas");
var ctx = canvas.getContext("2d");
var imageObj = new Image();
imageObj.src = 'http://powellong.com/data/wallpapers/144/WDF_1857791.jpg';
var width = canvas.width;
var height = canvas.height;

/** TO_DO
 * add backgorund img (if and only if successful ping to google)
 * explosions method
 * display hit counter
 * remove blurrRRRR
 * can make ishit function not strict with >= / <=  but will need to end game
 */


// center this
var aiY = 400;
var playerY = 400;
//^^
var aiCounter = 0;
var playerCounter = 0;
const blockY = 150;
var centerofplayerY = playerY + (blockY / 2);
var ballVy = 0;
const ballVx = 25;
var ballX = width / 2;
var ballY = centerofplayerY; // height / 2;
const distanceFromEdge = 25;
var leftBound = true;
var radi = 50;

//check player hit
var isPlayerHit = function () {
    return ballX == width - distanceFromEdge - radi && (ballY <= centerofplayerY + 175 && ballY >= centerofplayerY - 175);
    //  return ballX == width - distanceFromEdge - radi && (ballY <= playerY + 175 && ballY >= playerY - 175)? true:false;
}
//check ai hit
var isAiHit = function () { //Ai or ai... in other words... readability or consistency
    return (ballX == distanceFromEdge + radi && ( ballY <= aiY + 1000 && ballY >= aiY - 1000 ));

}

document.onkeydown = function (e) {
    switch (e.keyCode) {

        case 38://up
            if (playerY == 0)
                playerY = height;
            else
                playerY -= 20;
            break;
        case 40://down
            if (playerY == height)
                playerY = 0;
            else
                playerY += 20;
            break;
    }
};
//draw = function () {
setInterval(function () {
    centerofplayerY = playerY + (blockY / 2);
    //  ctx.drawImage(imageObj, 0, 0);

//Background
    ctx.beginPath();
    ctx.fillStyle = "black";
    ctx.rect(0, 0, 900, 900);
    ctx.fill();
    //counters
    ctx.color = "white";
    ctx.fillText("aiCounter", 30, 70);
    ctx.fillText("playerCounter", width - 30, 30);

//Player
    ctx.beginPath();
    ctx.fillStyle = "gray";
    ctx.rect(width - distanceFromEdge, playerY, 20, blockY);
    ctx.fill();
//Ai
    ctx.beginPath();
    ctx.fillStyle = "gray";
    ctx.rect(5, aiY, 20, blockY);
    ctx.fill();
//ball
    ctx.beginPath();
    ctx.fillStyle = "lightGray";
    ctx.arc(ballX, ballY, radi, 0, 2 * Math.PI);
    ctx.fill();
//check ai hit
    if (isAiHit()) {
        //    ballV = 25;
        leftBound = true;
        aiCounter++;
        // add explosion
    } else
//check play hit
    if (isPlayerHit()) {
        //     ballV = -25;
        leftBound = false;
        playerCounter++;
        // add explosion
    }

    aiY = ballY - 20;
//ball X axis movement
    if (leftBound)
        ballX += ballVx;
    else ballX -= ballVx;
//ball Y axis movement
    if (isPlayerHit()) {
        // if (ballY <= centerofplayerY - 125 && ballY > centerofplayerY - 150)            ballVy = 8;
        //  else
        if (ballY <= centerofplayerY - 100 && ballY > centerofplayerY - 120)
            ballVy = 4;
        else if (ballY <= centerofplayerY - 50 && ballY > centerofplayerY - 100)
            ballVy = 2;
        else if (ballY < centerofplayerY && ballY > centerofplayerY - 50)
            ballVy = 1;
        else if (ballY == centerofplayerY)
            ballVy = 0;
        else if (ballY >= centerofplayerY && ballY < centerofplayerY + 50)
            ballVy = -1;
        else if (ballY >= centerofplayerY + 50 && ballY < centerofplayerY + 100)
            ballVy = -2;
        else if (ballY >= centerofplayerY + 100 && ballY < centerofplayerY + 120)
            ballVy = -4;
        // else if (ballY >= centerofplayerY + 125 && ballY < centerofplayerY + 1050)
        //     ballVy = -8;
        else ballVy = 0;
    }

    if (Math.round(ballY) >= height - radi || Math.round(ballY) <= 0 + radi) {
        ballVy *= -1;
        console.log("ttttt");
    }
    ballY -= ballVy;
// ballX acceleration (speed boost)
    if (ballX > 0 && ballX < width / 2 && leftBound)
        ballX += 10;
    else if (ballX < width && ballX > width / 2 && !leftBound)
        ballX -= 10;
    // else
    //ballX-=25;

//winner winner chicken dinner
    if (ballX < 0) {
        console.log("*you win*");

    } else if (ballX >= width) {
        console.log("*AI wins*")

    }

    console.log("center of playerY: " + centerofplayerY);
    console.log("ballY: " + ballY);
    console.log("ballVy: " + ballVy);
}, 40);//set interval
//}
//draw();
//window.requestAnimationFrame(draw);
