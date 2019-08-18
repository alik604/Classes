//'http://www.sawyoo.com/postpic/2014/09/printable-graph-paper-x-y-axis_242803.png';
/** notes for marking and review********************
 * disable audio by commenting out new audio obj
 * max ball height is capped
 * score is just for fun. not meant to be consistent measure of skill
 * gameOBJ.push is for generating new platforms // not a good name
 * the .button CSS is not 100% my work. im assuming its ok,since we where allowed to use the website pixel(or something like that) i used http://css3buttongenerator.com/ for most of it
 * speed up seems not work work, likly not possiable
 */

//document.getElementById('a').play(); // song
//var audio = window.Audio != null  ? new Audio('Cartoon Bounce Sound Effect.mp3') : undefined; // bounce sound// checks if audio != null or undefined

/* to add
 * partial effects fall into lava
 */


var layer1 = document.getElementById("layer1");
//creates the background on the canvas underneath

var bg = new Image();
//bg.src = "paper_cut.png";
bg.src = "lava.png";

var footer = new Image();
footer.src = "fire.png";

bg.onload = function () {
    var backGround = layer1.getContext("2d");
    backGround.drawImage(bg, 0, 0);
    backGround.drawImage(footer, -10, 465);
};
//** ^^^ background and footer(fire image)
//footer.onload = function () {
//  var bottom = layer1.getContext("2d");
//};

var canvas = document.getElementById("myCanvas");
var ctx = canvas.getContext("2d");
//var x = 0;

const height = canvas.height,
    bottom = canvas.height,
    width = canvas.width,
    mid = width / 2;

//var gameOn=false;
var level = 1;
var score = -5; // <- please dont ask :P
var pushDown = 0; // pushing plateforms down
var offSetPlayY;//off set
var ran = [];// array to store off set
var platYArray = [1000, 800, 600, 400, 200, 1, -200, -400, -600];
var speed = 90;
var speededUp = false;
//var shootArry = [];
document.body.style.backgroundColor = "gray";
/* my tarriable attempt on starting game in pause state
 var gameOn = false;
 document.body.onkeyup = function(e) {
 if (e.keyCode == 32) {
 gameOn = true;
 console.log("sdsfsdfdsfds")
 }
 }
 var fun = function() {
 setTimeout(function() {
 if (gameOn == true) {
 break;
 } else {

 fun();
 }
 }, 1000);
 }


 */

var particles = {};
var numberOfParticle = 0;
var particleNumb = 30;


function speedUp() {
    speed = 60;
    var speededUp = true;
}
var getNewXandY = function () {
    for (var i = 0; i < 10; i++) {
        var rand = Math.floor(Math.random() * (width - 40) + 20);
        ran.push(rand);
    }
    platYArray = [1000, 800, 600, 400, 200, 1, -200, -400, -600];
    offSetPlayY = Math.random() * 100 - 50;
    for (var x in platYArray) {
        offSetPlayY = Math.random() * 100 - 70;
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
    radi: 50,
    goingDown: false,
    isLookingLeft: true,
    dedAsDodo: false,
    unused: true,
    accountMotion: function () {
        if (this.dedAsDodo) return;

        ball.Ypos += ball.bounceMotion;
        if (ball.Ypos < 100) {
            // console.log("!!!!!");
            this.bounceMotion *= -1;
            ball.bounceHeight = 350
        }///////////////////////////////////////////////////////////////

    },
    draw: function () {


        //head
        ctx.fillStyle = "#0ef247";
        ctx.StrokeStyle = "#0ef247";
        ctx.beginPath();
        ctx.arc(ball.Xpos + 10, ball.Ypos, 30, 0, 2 * Math.PI); // mousePos.x instead of mid
        ctx.fill();

        //eyes
        ctx.beginPath();
        ctx.fillStyle = "yellow";
        ctx.strokeStyle = "blue";
        ctx.arc(ball.Xpos + 23, ball.Ypos - 12, 5, 0, 2 * Math.PI); //left eye
        ctx.arc(ball.Xpos, ball.Ypos - 12, 5, 0, 2 * Math.PI); //right eye
        ctx.fill();
        //     ctx.stroke(); // gives glasses... (-_-)

        //set up for noes
        ctx.beginPath();
        ctx.strokeStyle = "white";
        ctx.fillStyle = "blue";
        if (ball.isLookingLeft) {

            //nose
            ctx.rect(ball.Xpos + 10, ball.Ypos - 10, -40, 10); // mousePos.x instead of mid
            ctx.fill();
            ctx.beginPath();
            ctx.strokeStyle = "black";
            ctx.fillStyle = "blue";
            ctx.arc(ball.Xpos - 30, ball.Ypos - 5, 5, 0, 2 * Math.PI);
            ctx.fill();
            ctx.stroke();

            ctx.beginPath();
            ctx.strokeStyle = "white";
            ctx.fillStyle = "blue";
            ctx.arc(ball.Xpos - 30, ball.Ypos - 5, 2, 0, 2 * Math.PI);
            ctx.fill();
            ctx.stroke();
        } else {
            //nose
            ctx.rect(ball.Xpos + 10, ball.Ypos - 10, 40, 10); // mousePos.x instead of mid
            ctx.fill();
            ctx.beginPath();
            ctx.strokeStyle = "black";
            ctx.fillStyle = "blue";
            ctx.arc(ball.Xpos + 50, ball.Ypos - 5, 5, 0, 2 * Math.PI);
            ctx.fill();
            ctx.stroke();
            // ctx.fill();

            ctx.beginPath();
            ctx.strokeStyle = "white";
            ctx.fillStyle = "blue";
            ctx.arc(ball.Xpos + 50, ball.Ypos - 5, 2, 0, 2 * Math.PI);
            ctx.fill();
            ctx.stroke();
        }

        //body
        ctx.beginPath();
        ctx.fillStyle = "yellow";
        ctx.strokeStyle = "black";
        ctx.rect(this.Xpos - 14, this.Ypos + (this.radi / 3), 50, 30);
        ctx.fill();
        //belt
        //ctx.fillStyle = "yellow";
        //ctx.beginPath();

        ctx.strokeStyle = "pink";
        ctx.moveTo(this.Xpos - 15, this.Ypos + 45);
        ctx.lineTo(this.Xpos + 35, this.Ypos + 45);

        ctx.moveTo(this.Xpos - 15, this.Ypos + 39);
        ctx.lineTo(this.Xpos + 35, this.Ypos + 39);

        ctx.moveTo(this.Xpos - 15, this.Ypos + 32);
        ctx.lineTo(this.Xpos + 35, this.Ypos + 32);

        ctx.moveTo(this.Xpos - 15, this.Ypos + 25);
        ctx.lineTo(this.Xpos + 35, this.Ypos + 25);

        ctx.moveTo(this.Xpos - 15, this.Ypos + 17);
        ctx.lineTo(this.Xpos + 35, this.Ypos + 17);
        //legs (5 left to right)
        ctx.moveTo(this.Xpos - 10, this.Ypos + 45);
        ctx.lineTo(this.Xpos - 10, this.Ypos + 60);

        ctx.moveTo(this.Xpos, this.Ypos + 45);
        ctx.lineTo(this.Xpos, this.Ypos + 60);

        ctx.moveTo(this.Xpos + 10, this.Ypos + 45);
        ctx.lineTo(this.Xpos + 10, this.Ypos + 60);

        ctx.moveTo(this.Xpos + 20, this.Ypos + 45);
        ctx.lineTo(this.Xpos + 20, this.Ypos + 60);

        ctx.moveTo(this.Xpos + 33, this.Ypos + 45);
        ctx.lineTo(this.Xpos + 33, this.Ypos + 60);

        ctx.stroke();

    }
};

var plat = {
    objXpos: 0,
    objYpos: height,
    moveSide: 0,
    goingLeft: true,
    makeReg: function (objXpos, objYpos) {

        this.objXpos = objXpos;
        this.objYpos = objYpos;
        this.hitCheck();
        ctx.beginPath();
        ctx.fillStyle = "blue";
        ctx.rect(objXpos - 25, objYpos - 10, 50, 20);
        ctx.arc(objXpos + 25, objYpos, 10, 0, 2 * Math.PI);
        ctx.fill();
        ctx.arc(objXpos - 25, objYpos, 10, 0, 2 * Math.PI);
        ctx.fill();

    },
    makeVertMoving: function (objXpos, objYpos) {
        if (this.moveSide <= -250)
            this.goingLeft = false;
        if (this.moveSide >= width - 300)
            this.goingLeft = true;
        if (this.goingLeft)
            this.moveSide -= 10
        if (this.goingLeft == false)
            this.moveSide += 10


        this.objXpos = objXpos += this.moveSide;// <- I should get an A++ for that :)
        // if (this.objYpos < 200) {

        this.objYpos = objYpos;

        plat.hitCheck();
        ctx.beginPath();
        ctx.fillStyle = "yellow";
        ctx.rect(objXpos - 25, objYpos - 10, 50, 20);
        ctx.arc(objXpos + 25, objYpos, 10, 0, 2 * Math.PI);
        ctx.fill();
        ctx.arc(objXpos - 25, objYpos, 10, 0, 2 * Math.PI);
        ctx.fill();
        //  this.moveUpY -= 10;

    },
    hitCheck: function () {
        //check X
        if ((ball.Xpos > this.objXpos - 57 && ball.Xpos < this.objXpos + 57))
        //check Y
            if (ball.goingDown && this.objYpos - ball.radi - 20 >= ball.Ypos - 10 && this.objYpos - ball.radi - 20 <= ball.Ypos + 10) { /////////////////////////
                score += Math.floor(Math.random() * 3 + 1);
                ball.bounceMotion = -20;
                var temp = ((height - this.objYpos) / 3 * 2);
                if (temp < 0)
                    temp = 0;

                ball.bounceHeight = 370 + temp;
                try {
                    audio.play();
                } catch (err) {
                }//catch
                //console.log(ball.bounceHeight)
                //if plat is higher then 40px from bottom
                if (this.objYpos < height - 110) {

                    if (ball.Ypos > 200)//in top 3/4 area
                        pushDown -= 125;
                    else
                        pushDown -= 75;
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

        if (ball.Ypos >= 695 + ball.radi && ball.Ypos <= 800) { // if hits bottom,end game
            function particle() {

                this.x = ball.Xpos;
                this.y = ball.Ypos + 40;
                this.vx = Math.random() * 15 - 5;
                this.vy = Math.random() * -10;
                this.gravity = -.20;
                numberOfParticle++;
                particles[numberOfParticle] = this;
                this.id = numberOfParticle;
                this.life = 0;
                this.maxlife = Math.random() * 10 + 5//15;
            }

            particle.prototype.draw = function () {
                this.x += this.vx;
                this.y += this.vy;
                this.vy += this.gravity;
                this.life++;
                if (this.life >= this.maxlife) {
                    delete particles[this.id];
                }
                ctx.beginPath(); // <- needed!!!
                ctx.fillStyle = "red";
                // ctx.fillRect(this.x, this.y, 10, 10);

                ctx.arc(this.x, this.y, 5, 0, 2 * Math.PI);
                ctx.fill();
                // ctx.beginPath();
            };
            for (var i = 0; i < particleNumb; i++) {
                new particle();
                //var parti = new particle();
            }
            for (var i in particles)
                particles[i].draw(); // draw partial effects

            setTimeout(function () {
                document.getElementById("lvlID").innerHTML = "***game over*** score is: " + score;
                if (ball.unused) {
                    ball.unused = false;
                    setTimeout(function () {
                        if (confirm("play again")) {
                            location.reload();
                        }
                    }, 3500);
                }
                clearInterval(myVar);
            }, 3000);
            //        setTimeout(function () { }, 2000);

            ball.dedAsDodo = true;
        }
    }
};

var shoot = {// not used, was going to add shooting. its would over complicate the game
    construct: function (xPos, yPos) {
        this.x = xPos;
        this.y = yPos;
        this.x++;
        ctx.beginPath();
        ctx.fillStyle = "#0ef247";
        //use isfaceing left to use direction of shooting. -/+ this.x to make shooting
        ctx.arc(this.x - 10, this.y, 5, 0, 2 * Math.PI);
        ctx.fill();
    }
};

var game = function () {

    document.onkeydown = function (e) {
        switch (e.keyCode) {
            case 37: //left
                if (ball.Xpos <= 0)
                    ball.Xpos = 790;
                ball.Xpos -= 40;
                if (speededUp)
                    ball.Xpos -= 20;//if fast mode is on, mode ball(doodle) 50% more
                ball.isLookingLeft = true;
                break;
            case 39: //right
                if (ball.Xpos >= 800)
                    ball.Xpos = 30;
                ball.Xpos += 40;
                if (speededUp)
                    ball.Xpos += 20;
                ball.isLookingLeft = false;
                break;
            case 38: //up
                //  var obj = new shoot.construct(ball.Xpos, ball.Ypos);
                //  shootArry.push(obj);
                break;
            case 40: //down
                break;

        }
    };

    if (level == 10) {
        document.getElementById("lvlID").innerHTML = "The martian successfully escaped mount doom!!... JK :)"
    }
    ctx.clearRect(0, 0, height, width);


    ball.accountMotion(); //ball.Ypos += ball.bounceMotion

    gameOBJ.downWards();
    gameOBJ.pushDown();
    for (var i = 0; i < platYArray.length; i++) {
        plat.makeReg(ran[i], platYArray[i] - pushDown);

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
    plat.makeVertMoving(300, 300 - pushDown);
    ball.draw();
    gameOBJ.testGameOver();//flamming ball over "ball"

    // console.log(score)
    //  for (var i = 0; i < shootArry.length; i++) {
    //      shootArry[i];
    //  }
};
//game();


var myVar = setInterval(game, speed); //less is faster


//window.requestAnimationFrame(game);



