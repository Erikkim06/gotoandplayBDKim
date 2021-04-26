
var canvasLogo  = document.getElementById('canvasLogo');
if(canvasLogo.getContext){
    var layoutLogo = canvasLogo.getContext("2d");
    var imgLogo = new Image();
    imgLogo.src = "items/logo.png";/*gotoAndPlay logo oli saadud googlest, GIMP-is võtsin tausta ära lihtsalt*/
    imgLogo.onload = function(){
        layoutLogo.drawImage(imgLogo, 0, 0);
    }
}
/*https://codepen.io/whqet/pen/Auzwindow.innerHeight?editors=0010*/
window.requestAnimFrame = ( function() {
    return window.requestAnimationFrame ||
        function( callback ) {
            window.setTimeout( callback, 1000 / 60 );
        };
})();
var canvas = document.getElementById( 'canvas' ),
    context = canvas.getContext( '2d' ),
    fireworks = [],
    particles = [],
    limiterTotal = 10,
    limiterTick = 0,
    timerTotal = 60,
    timerTick = 0,
    mousedown = false,
    mx,
    my;

canvas.width = window.innerWidth;
canvas.height = window.innerHeight;


function random( min, max ) {
    return Math.random() * ( max - min ) + min;
}

function calculateDistance( beginX, beginY, endX, endY ) {
    var x = beginX - endX,
        y = beginY - endY;
    return Math.sqrt( Math.pow( x, 2 ) + Math.pow( y, 2 ) );
}

function Firework( sx, sy, tx, ty ) {
    this.x = sx;
    this.y = sy;
    this.sx = sx;
    this.sy = sy;
    this.tx = tx;
    this.ty = ty;
    this.distanceToTarget = calculateDistance( sx, sy, tx, ty );
    this.distanceTraveled = 0;
    this.coordinates = [];
    this.coordinateCount = 3;
    while( this.coordinateCount-- ) {
        this.coordinates.push( [ this.x, this.y ] );
    }
    this.angle = Math.atan2( ty - sy, tx - sx );
    this.speed = 2;
    this.acceleration = 1.05;
    this.brightness = random( 50, 70 );
    this.targetRadius = 1;
}

Firework.prototype.update = function( index ) {
    this.coordinates.pop();
    this.coordinates.unshift( [ this.x, this.y ] );

    if( this.targetRadius < 8 ) {
        this.targetRadius += 0.3;
    } else {
        this.targetRadius = 1;
    }

    this.speed *= this.acceleration;

    var vx = Math.cos( this.angle ) * this.speed,
        vy = Math.sin( this.angle ) * this.speed;
    this.distanceTraveled = calculateDistance( this.sx, this.sy, this.x , this.y);

    if( this.distanceTraveled >= this.distanceToTarget ) {
        createParticles( this.tx, this.ty );
        fireworks.splice( index, 1 );
    } else {
        this.x += vx;
        this.y += vy;
    }
}

Firework.prototype.draw = function() {
    context.beginPath();
    context.moveTo( this.coordinates[ this.coordinates.length - 1][ 0 ], this.coordinates[ this.coordinates.length - 1][ 1 ] );
    context.lineTo( this.x, this.y );
    context.strokeStyle = 'hsl(' + hue + ', 100%, ' + this.brightness + '%)';
    context.stroke();

    context.beginPath();

    context.stroke();
}

function Particle( x, y ) {
    this.x = x;
    this.y = y;
    this.coordinates = [];
    this.coordinateCount = 5;
    while( this.coordinateCount-- ) {
        this.coordinates.push( [ this.x, this.y ] );
    }
    this.angle = random( 0, Math.PI * 2 );
    this.speed = random( 1, 10 );
    this.friction = 0.95;
    this.gravity = 1;
    this.hue = random( hue - 50, hue + 50 );
    this.brightness = random( 50, 80 );
    this.alpha = 1;
    this.decay = random( 0.015, 0.03 );
}

Particle.prototype.update = function( index ) {
    var imgX = this.x;
    var imgY = this.y;
    this.coordinates.pop();
    this.coordinates.unshift( [ this.x, this.y ] );
    this.speed *= this.friction;
    this.x += Math.cos( this.angle ) * this.speed;
    this.y += Math.sin( this.angle ) * this.speed + this.gravity;
    this.alpha -= this.decay;
    var imgAge = new Image();
    imgAge.src = "items/age.png";
    imgAge.onload = function(){
        context.drawImage(imgAge,imgX,imgY);
    }
    if( this.alpha <= this.decay ) {
        particles.splice( index, 1 );
    }
}

Particle.prototype.draw = function() {
    context. beginPath();
    context.moveTo( this.coordinates[ this.coordinates.length - 1 ][ 0 ], this.coordinates[ this.coordinates.length - 1 ][ 1 ] );
    context.lineTo( this.x, this.y );
    context.strokeStyle = 'hsla(' + this.hue + ', 100%, ' + this.brightness + '%, ' + this.alpha + ')';
    context.stroke();
}

function createParticles( x, y ) {
    var particleCount = 20;
    while( particleCount-- ) {
        particles.push( new Particle( x, y ) );
    }
}

function loop() {
    requestAnimFrame( loop );


    hue = random(0, 360);

    context.globalCompositeOperation = 'destination-out';
    context.fillStyle = 'rgba(0,0,0,0.5)';
    context.fillRect( 0, 0, window.innerWidth, window.innerHeight );
    context.globalCompositeOperation = 'lighter';

    var i = fireworks.length;
    while( i-- ) {
        fireworks[ i ].draw();
        fireworks[ i ].update( i );
    }

    var i = particles.length;
    while( i-- ) {
        particles[ i ].draw();
        particles[ i ].update( i );
    }

    if( timerTick >= timerTotal ) {
        if( !mousedown ) {
            fireworks.push( new Firework( random(0,window.innerWidth), window.innerHeight, random( 0, window.innerWidth ), random( 0, window.innerHeight / 2 ) ) );
            timerTick = 0;
        }
    } else {
        timerTick++;
    }

    if( limiterTick >= limiterTotal ) {
        if( mousedown ) {
            fireworks.push( new Firework( random(0,window.innerWidth), window.innerHeight, mx, my ) );
            limiterTick = 0;
        }
    } else {
        limiterTick++;
    }
}

canvas.addEventListener( 'mousemove', function( e ) {
    mx = e.pageX - canvas.offsetLeft;
    my = e.pageY - canvas.offsetTop;
});

canvas.addEventListener( 'mousedown', function( e ) {
    e.preventDefault();
    mousedown = true;
});

canvas.addEventListener( 'mouseup', function( e ) {
    e.preventDefault();
    mousedown = false;
});

window.onload = loop;



