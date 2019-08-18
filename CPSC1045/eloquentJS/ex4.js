/*jslint browser:true, es6 */
//http://powellong.com/data/wallpapers/144/WDF_1857791.jpg
var canvas = document.getElementById("myCanvas");
var ctx = canvas.getContext("2d");

//'http://www.sawyoo.com/postpic/2014/09/printable-graph-paper-x-y-axis_242803.png';
/** notes for marking and review********************
 * disable audio by commenting out new audio obj
 * max ball height is capped
 * score is just for fun. not meant to be legit measure of skillZZZ
 */

/**
 * acceralation
 * wallpaper
 * better looking models
 * title screen, exit screen
 * game modes
 * other platforms
 * partial effects when bounce
 * add speed and difficulty
 */

/*
 notes for myself*
 gameOBJ.push own is for generating new platforms
 */

//document.getElementById('a').play(); // song
//var audio = new Audio('Cartoon Bounce Sound Effect.mp3'); // bounce sound

const height = 800;
const bottom = 800;
const width = 800;
const mid = width / 2;
var level = 1;
var score = -5;
var pushDown = 0;
var platYArray;
var offSetPlayY;
var ran = [];
var platYArray = [1000, 800, 600, 400, 200, 1, -200, -400, -600];
var getNewXandY = function () {
    for (var i = 0; i < 10; i++) {
        var rand = Math.floor(Math.random() * width);
        ran.push(rand);
    }
    platYArray = [1000, 800, 600, 400, 200, 1, -200, -400, -600];
    offSetPlayY = Math.random() * 100 - 50;
    for (var x in platYArray) {
        offSetPlayY = Math.random() * 100 - 50;
        platYArray[x] += offSetPlayY;
    }
    score += 5;
};
getNewXandY();

var ball = {
    Ypos: 700,
    Xpos: mid,
    // start at bottom,middle
    bounceMotion: -20,
    bounceHeight: 350,
    radi: 30,
    goingDown: false,
    accountMotion: function () {
        if (ball.Ypos < 100) {
            console.log("!!!!!")
            this.bounceMotion *= -1;
        }
        ball.Ypos += ball.bounceMotion;
    },
    draw: function () {
        ctx.fillStyle = "yellow";
        ctx.beginPath();
        ctx.arc(ball.Xpos, ball.Ypos, ball.radi, 0, 2 * Math.PI); // mousePos.x instead of mid
        ctx.fill();
    }
};


var plat = {
    objXpos: null,
    objYpos: null,
    make: function (objXpos, objYpos) {

        this.objXpos = objXpos;
        this.objYpos = objYpos;
        this.hit();
        ctx.beginPath();
        ctx.fillStyle = "green";
        ctx.rect(objXpos - 25, objYpos - 10, 50, 20);
        ctx.arc(objXpos + 25, objYpos, 10, 0, 2 * Math.PI);
        ctx.fill();
        ctx.arc(objXpos - 25, objYpos, 10, 0, 2 * Math.PI);
        ctx.fill();

    },
    hit: function () {
        //check X
        if (( ball.Xpos > this.objXpos - 50 && ball.Xpos < this.objXpos + 50 ))
        //check Y
            if (ball.goingDown && this.objYpos - ball.radi - 20 >= ball.Ypos - 10 && ball.goingDown && this.objYpos - ball.radi - 20 <= ball.Ypos + 10) {/////////////////////////
                score += Math.floor(Math.random() * 3 + 1);
                ball.bounceMotion = -20;
                ball.bounceHeight = 350 + ((height - this.objYpos) / 3 * 2);
                //  console.log(ball.bounceHeight)
                //if plat is higher then 40px from bottom
                if (this.objYpos < height - 40) {
                    try {
                        audio.play();
                    }
                    catch (err) {
                    }
                    if (ball.Ypos < 200)
                        pushDown -= 100;
                    else
                        pushDown -= 60;
                    // console.log(pushDown)
                }
            } else {
                //   ball.bounceHeight = 350
            }
    }
};

var gameOBJ = {
    nextLVL: function () {
        document.getElementById("lvlID").innerHTML = "Level " + ++level;
    },
    downWards: function () {
        if (ball.Ypos <= height - ball.bounceHeight) { //downwards
            ball.bounceMotion = 20;
            ball.goingDown = true;
            // console.log(ball.goingDown);
        }
    },
    pushDown: function () {
        if (pushDown <= -1300) {
            pushDown = 0;
            gameOBJ.nextLVL();
            getNewXandY();
        }
    },
    testGameOver: function () {
        if (ball.Ypos >= 750 + ball.radi) { // if hits bottom,end game
            document.getElementById("lvlID").innerHTML = "***game over*** score is: " + score;
            clearInterval(myVar);
        }
    }
};

var game = function () {
    document.onkeydown = function (e) {
        switch (e.keyCode) {
            case 37://left
                if (ball.Xpos <= 0)
                    ball.Xpos = 790;
                ball.Xpos -= 40;
                break;
            case 39://right
                if (ball.Xpos >= 800)
                    ball.Xpos = 30;
                ball.Xpos += 40;
                break;
            case 38://up

                break;
            case 40://down
                break;
        }
    };

    ctx.clearRect(0, 0, height, width);
    ball.accountMotion(); //ball.Ypos += ball.bounceMotion

    gameOBJ.downWards();
    gameOBJ.pushDown();
    gameOBJ.testGameOver();

    for (var i = 0; i < platYArray.length; i++) {
        plat.make(ran[i], platYArray[i] - pushDown);
        // console.log(platYArray[i])
    }

    // plat.make(400, 500);// <- need to fix this shit bounceheiught + objY?
    /**
     plat.make(400, 1000 - pushDown);
     plat.make(400, 800 - pushDown);
     plat.make(400, 600 - pushDown);
     plat.make(400, 400 - pushDown);
     plat.make(400, 200 - pushDown);
     plat.make(400, 0 - pushDown);
     plat.make(400, -200 - pushDown);
     plat.make(400, -400 - pushDown);
     plat.make(400, -600 - pushDown);
     */
    ball.draw();

    // console.log(score)
};
//game();


var myVar = setInterval(game, 80);//less is faster


//window.requestAnimationFrame(game);


/*
 ctx.fillStyle = "blue";
 ctx.fillRect(0, GROUND, width, 100); // ground

 ctx.fillStyle = "yellow";
 ctx.beginPath();

 ctx.fill();

 ctx.fillStyle = "white";
 ctx.beginPath();
 ctx.arc(MID, GROUND - 265, 40, 0, 2 * Math.PI); // head
 ctx.fill();
 ctx.beginPath();
 ctx.arc(MID, GROUND - 160, 70, 0, 2 * Math.PI); // upper torso
 ctx.fill();
 ctx.beginPath();
 ctx.arc(MID, GROUND, 100, 0, 2 * Math.PI); // lower torso
 ctx.fill();

 ctx.fillStyle = "black";
 ctx.beginPath();
 ctx.arc(MID - 15, GROUND - 275, 5, 0, 2 * Math.PI); // left eye
 ctx.arc(MID + 15, GROUND - 275, 5, 0, 2 * Math.PI); // right eye
 ctx.fill();

 ctx.beginPath();
 ctx.arc(MID, GROUND - 260, 20, 0, Math.PI); // smile
 ctx.stroke();

 ctx.moveTo(MID - 50, GROUND - 160);
 ctx.lineTo(MID - 140, GROUND - 160); // left arm
 ctx.stroke();


 ctx.moveTo(MID + 50, GROUND - 160);
 ctx.lineTo(MID + 140, GROUND - 200); // right arm
 ctx.stroke();

 ctx.moveTo(MID - 50, GROUND - 300);
 ctx.lineTo(MID + 50, GROUND - 300); // brim of hat
 ctx.stroke();

 ctx.fillRect(MID - 30, GROUND - 340, 60, 40);
 */
