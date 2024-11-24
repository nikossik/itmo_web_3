const canvas = document.getElementById('coordinateCanvas');
const ctx = canvas.getContext('2d');
const width = canvas.width;
const height = canvas.height;

let points = [];

function initializePoints() {
    const table = document.querySelector('.results-table tbody');
    const r = parseFloat(document.getElementById('inputForm:radius').value) || 1;
    if (!table) return;

    points = [];
    const rows = table.getElementsByTagName('tr');

    for (let i = 0; i < rows.length; i++) {
        const cells = rows[i].getElementsByTagName('td');
        if (cells.length >= 4) {
            const x = parseFloat(cells[0].textContent);
            const y = parseFloat(cells[1].textContent);
            const rValue = parseFloat(cells[2].textContent);
            const hit = checkHit(x, y, r);

            if (!isNaN(x) && !isNaN(y) && !isNaN(rValue)) {
                points.push({ x, y, hit });
            }
        }
    }

    drawGraph();
}

function setRadius(value) {
    const rInput = document.getElementById('inputForm:radius');
    if (!rInput) return;

    rInput.value = value;
    document.querySelectorAll('.r-button').forEach(button => {
        button.classList.toggle('active', button.value === value.toString());
    });

    const newR = parseFloat(value);
    points = points.map(point => ({
        ...point,
        hit: checkHit(point.x, point.y, newR),
    }));

    drawGraph();
}

function drawGraph() {
    const r = parseFloat(document.getElementById('inputForm:radius').value) || 1;
    ctx.clearRect(0, 0, width, height);
    ctx.save();
    ctx.translate(width / 2, height / 2);
    ctx.scale(1, -1);

    const scale = width / 10;

    ctx.fillStyle = 'rgba(64, 156, 255, 0.5)';

    // Прямоугольник
    ctx.fillRect(0, 0, scale * r, scale * (r / 2));

    // Четверть круга
    ctx.beginPath();
    ctx.moveTo(0, 0);
    ctx.arc(0, 0, scale * r, Math.PI, Math.PI * 3 / 2, false);
    ctx.closePath();
    ctx.fill();

    // Треугольник
    ctx.beginPath();
    ctx.moveTo(0, 0);
    ctx.lineTo(scale * r, 0);
    ctx.lineTo(0, -scale * (r / 2));
    ctx.closePath();
    ctx.fill();

    // Оси
    ctx.strokeStyle = '#000';
    ctx.lineWidth = 1;
    ctx.beginPath();

    // X-ось
    ctx.moveTo(-width / 2, 0);
    ctx.lineTo(width / 2, 0);
    ctx.moveTo(width / 2 - 10, -5);
    ctx.lineTo(width / 2, 0);
    ctx.lineTo(width / 2 - 10, 5);

    // Y-ось
    ctx.moveTo(0, -height / 2);
    ctx.lineTo(0, height / 2);
    ctx.moveTo(-5, height / 2 - 10);
    ctx.lineTo(0, height / 2);
    ctx.lineTo(5, height / 2 - 10);

    ctx.stroke();

    // Подписи осей
    ctx.scale(1, -1);
    ctx.fillStyle = '#000';
    ctx.textAlign = 'center';
    ctx.textBaseline = 'middle';
    ctx.font = '12px Arial';

    // Метки X
    [-r, -r / 2, r / 2, r].forEach(value => {
        const x = value * scale;
        ctx.beginPath();
        ctx.moveTo(x, -5);
        ctx.lineTo(x, 5);
        ctx.stroke();
        ctx.fillText(value.toFixed(1), x, 20);
    });

    // Метки Y
    [-r, -r / 2, r / 2, r].forEach(value => {
        const y = value * scale;
        ctx.beginPath();
        ctx.moveTo(-5, -y);
        ctx.lineTo(5, -y);
        ctx.stroke();
        ctx.fillText(value.toFixed(1), -20, -y);
    });

    ctx.restore();

    points.forEach(point => {
        drawPoint(point.x, point.y, point.hit);
    });
}

function drawPoint(x, y, hit) {
    const scale = width / 10;
    const pixelX = (width / 2) + (x * scale);
    const pixelY = (height / 2) - (y * scale);

    ctx.beginPath();
    ctx.arc(pixelX, pixelY, 5, 0, Math.PI * 2);
    ctx.fillStyle = hit ? '#2ecc71' : '#e74c3c';
    ctx.fill();
    ctx.strokeStyle = 'white';
    ctx.lineWidth = 2;
    ctx.stroke();
}

function checkHit(x, y, r) {
    if (r <= 0) return false;
    if (x >= 0 && y >= 0 && x <= r && y <= r / 2) {
        return true;
    }
    if (x <= 0 && y <= 0 && (x ** 2 + y ** 2) <= (r ** 2)) {
        return true;
    }
    if (x >= 0 && y <= 0 && y >= -r/2 + x/2) {
        return true;
    }
    return false;
}

canvas.addEventListener('click', function(event) {
    const r = parseFloat(document.getElementById('inputForm:radius').value);
    if (!r) {
        alert('Сначала выберите значение R');
        return;
    }

    const rect = canvas.getBoundingClientRect();
    const scale = width / 10;

    let x = ((event.clientX - rect.left - width / 2) / scale);
    let y = -((event.clientY - rect.top - height / 2) / scale);

    x = snapToGrid(x, -4, 4, 0.5);
    y = snapToGrid(y, -3, 3, 0.5);

    document.getElementById('inputForm:xCoord').value = x.toFixed(1);
    document.getElementById('inputForm:yCoord').value = y.toFixed(1);

    const submitButton = document.querySelector('#inputForm .action-button');
    if (submitButton) submitButton.click();
});

function snapToGrid(value, min, max, step) {
    const snappedValue = Math.round(value / step) * step;
    return Math.max(min, Math.min(max, snappedValue));
}

window.addEventListener('load', () => {
    document.getElementById('inputForm:xCoord').value = '1';
    document.getElementById('inputForm:yCoord').value = '1';

    document.getElementById('inputForm:radius').value = '1';

    initializePoints();
});

window.addEventListener('resize', drawGraph);