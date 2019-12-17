const SIZE = 10;
const MINE_COUNTS = 20;
const AROUND = [[-1, -1], [0, -1], [1, -1], [-1, 0], [1, 0], [-1, 1], [0, 1], [1, 1]];
const FLAG_ICONS = ['', 'glyphicon glyphicon-flag', 'glyphicon glyphicon-question-sign'];


function validAroundPositions(o, size){
  let i = o[0];
  let j = o[1];
  return AROUND.map(p => [i + p[0], j + p[1]]).filter(p => p[0] >=0 && p[0] < size && p[1] >= 0 && p[1] < size);
}

class Game{
  constructor(){
    this.size = SIZE;
    this.gameOver = false;
    this.drawer = new Drawer(document.querySelector('#boxes'), this);
    this.initGameBox();
  }
  initGameBox(){
    this.boxes = [];
    let mines = new Set();
    let count = this.size * this.size;
    // random mine created
    for(let i = 0; i < MINE_COUNTS; i++){
      mines.add(Math.floor(Math.random() * count));
    }

    // init boxes
    for(let i = 0; i < this.size; i++){
      this.boxes[i] = [];
      for(let j   = 0; j < this.size; j++){
        let isMine = mines.has(i * this.size + j);
        this.boxes[i][j] = {
          flag: 0,
          idx: i + ',' + j,
          isMine: isMine,
          mineNumberAround: -1,
          isSweeped: false
        };
      } 
    }

    // calculate mines number around
    for(let i = 0; i < this.size; i++){
      for(let j = 0; j< this.size; j++){
        let box = this.boxes[i][j];
        if(!box.isMine){
          box.mineNumberAround = validAroundPositions([i, j], this.size)
          .filter(p => this.boxes[p[0]][p[1]].isMine)
          .map(p => 1)
          .reduce((n1, n2) => n1 + n2, 0);
        }
      }
    }
  }

  start(){
    this.drawer.draw(this.boxes);
  }

  sweepe(p){
    p = [parseInt(p[0]), parseInt(p[1])];
    let i = p[0];
    let j = p[1];
    let size = this.size;
    if(this.boxes[i][j].isSweeped){
      return;
    }
    if(this.boxes[i][j].isMine){
      this.gameOver = true;
      this.boxes[i][j].isSweeped = true;
      this.drawer.draw(this.boxes);
      return;
    }
    let trySweepe = function(positions, boxes){
      if(positions.length === 0){
        return;
      }
      let newPosition = [];
      positions.map(p => {
        boxes[p[0]][p[1]].isSweeped = true;
        return p;
      }).filter(p => boxes[p[0]][p[1]].mineNumberAround === 0)
      .forEach(p => {
        validAroundPositions(p, size).filter(vp => boxes[vp[0]][vp[1]].isSweeped === false)
        .forEach(vp => {
          newPosition.push(vp);
        });
      });
      trySweepe(newPosition, boxes);
    };
    trySweepe([p], this.boxes);
    this.drawer.draw(this.boxes);
  }

  flag(p){
    p = [parseInt(p[0]), parseInt(p[1])];
    let i = p[0];
    let j = p[1];
    let box = this.boxes[i][j];
    if(box.isSweeped){
      return;
    }
    box.flag = (box.flag + 1)%3;
    this.drawer.draw(this.boxes);
  }

  isGameOver(){
    return this.gameOver;
  }
}

class Drawer{
  constructor(el, game){
    this.el = el;
    this.game = game;
    this.el.addEventListener('click', evt => {
      if(this.game.isGameOver()){
        alert('Game over!');
        return;
      }
      if(evt.srcElement instanceof HTMLTableCellElement){
        let td = evt.srcElement;
        this.game.sweepe(td.dataset.idx.split(','));
      }
    });
    this.el.addEventListener('contextmenu', evt => {
      evt.preventDefault();
      if(evt.srcElement instanceof HTMLTableCellElement){
        let td = evt.srcElement;
        this.game.flag(td.dataset.idx.split(','));
      }
    });
    this.cellTemplate = new Template('<td class="{{flag}}" data-idx={{dataIdx}} style="background:{{background}};line-height:2.5em;color:{{color}};">{{text}}</td>');
  }

  draw(boxes){
    this.el.innerHTML = boxes.map(row => {
      return '<tr>' + row.map(box => {
        return this.cellTemplate.fill({
          dataIdx: box.idx,
          flag: box.isSweeped?(box.isMine?'glyphicon glyphicon-certificate' :'') : FLAG_ICONS[box.flag],
          background: box.isSweeped?box.isMine?'white' : 'green' : 'black',
          text: box.isSweeped && box.mineNumberAround > 0? box.mineNumberAround : '',
          color: box.isMine?'red' : 'white'
        });
      }).join('') + '</tr>';
    }).join('');
  }
}

class Template{
  constructor(pattern){
    this.pattern = pattern;
  }
  fill(data){
    return this.pattern.replace(/{{(.+?)}}/g, (m, g) => {
      return data[g];
    });
  }
}

new Game().start();