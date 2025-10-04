// Endpoints: GET/POST/DELETE /api/strokes

const API = {
  base: '',
  list: (since) => `/api/strokes?since=${since || 0}`,
  post: () => `/api/strokes`,
  clear: () => `/api/strokes`,
  epoch: () => `/api/strokes/epoch`
};

let myColor = randomColor();
let brushSize = 8;
let lastSeenId = 0;
let strokesQueue = [];
let epoch = 1;

function randomColor() {

  const palette = ['#ef476f','#ffd166','#06d6a0','#118ab2','#8338ec','#ff006e','#3a86ff','#fb5607','#1dd3b0','#8ac926'];
  return palette[Math.floor(Math.random() * palette.length)];
}

function setup() {
  const container = document.getElementById('canvas-container');
  const w = Math.min(window.innerWidth - 32, 1000);
  const h = Math.min(window.innerHeight - 150, 600);
  const cnv = createCanvas(w, h);
  cnv.parent(container);

  background(255);
  noStroke();


  const swatch = document.getElementById('colorSwatch');
  const colorText = document.getElementById('colorText');
  const clearBtn = document.getElementById('clearBtn');
  const sizeRange = document.getElementById('sizeRange');

  swatch.style.backgroundColor = myColor;
  colorText.textContent = myColor;
  sizeRange.value = brushSize;
  sizeRange.addEventListener('input', () => brushSize = Number(sizeRange.value));
  clearBtn.addEventListener('click', clearBoard);


  setInterval(fetchEpoch, 400);
  setInterval(fetchNewStrokes, 200);
}

function windowResized() {
  const w = Math.min(window.innerWidth - 32, 1000);
  const h = Math.min(window.innerHeight - 150, 600);
  resizeCanvas(w, h);
}

function draw() {

  if (strokesQueue.length > 0) {
    const batch = strokesQueue;
    strokesQueue = [];
    for (const s of batch) {
      fill(s.color || '#000');
      circle(s.x, s.y, s.size || 8);
    }
  }


  if (mouseIsPressed) {
    const s = { x: mouseX, y: mouseY, color: myColor, size: brushSize, timestamp: Date.now() };
    fill(myColor);
    circle(s.x, s.y, s.size);

    fetch(API.post(), {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(s)
    }).then(r => r.json()).then(created => {

      if (created && created.id && created.id > lastSeenId) {
        lastSeenId = created.id;
      }
    }).catch(() => {/* noop */});
  }
}

async function fetchNewStrokes() {
  try {
    const res = await fetch(API.list(lastSeenId));
    if (!res.ok) return;
    const data = await res.json();
    if (Array.isArray(data) && data.length > 0) {
      const maxId = data.reduce((m, s) => Math.max(m, s.id || 0), lastSeenId);
      strokesQueue.push(...data);
      if (maxId > lastSeenId) lastSeenId = maxId;
    }
  } catch (e) {

  }
}

async function fetchEpoch() {
  try {
    const res = await fetch(API.epoch());
    if (!res.ok) return;
    const current = await res.json();
    if (typeof current === 'number' && current !== epoch) {
      epoch = current;
      background(255);
      lastSeenId = 0;
    }
  } catch (e) {}
}

async function clearBoard() {
  try {
    await fetch(API.clear(), { method: 'DELETE' });
  } catch (e) {}
  background(255);
  lastSeenId = 0;
}
