const SCALE_FACTOR = 40;
let currentPoints = [];

// Инициализация графика
function initGraph(r = 2) {
    console.log('Инициализация графика с R =', r);

    const canvas = document.getElementById('graphCanvas');
    if (!canvas) {
        console.error('Canvas не найден!');
        return;
    }

    drawGraph(r);

    // Обработчик кликов
    canvas.addEventListener('click', function(event) {
        console.log('Клик по графику');
    });

    // Инициализируем точки
    updatePointsFromJava();
}

// Отрисовка графика
function drawGraph(r) {
    if (!r || r < 1 || r > 5) {
        console.warn('Некорректный R:', r);
        r = 2;
    }

    console.log('Рисуем график для R =', r);

    const canvas = document.getElementById('graphCanvas');
    if (!canvas) return;

    const ctx = canvas.getContext('2d');
    const w = canvas.width;
    const h = canvas.height;
    const cx = w / 2;
    const cy = h / 2;

    // Очистка
    ctx.clearRect(0, 0, w, h);

    // Сетка
    drawGrid(ctx, w, h, cx, cy);

    // Оси
    drawAxes(ctx, w, h, cx, cy);

    // Область попадания (ваш вариант 470354)
    drawArea(ctx, cx, cy, r);

    // Точки
    drawPoints(ctx, cx, cy, r);
}

// Сетка
function drawGrid(ctx, w, h, cx, cy) {
    ctx.strokeStyle = '#f0f0f0';
    ctx.lineWidth = 1;

    // Вертикальные линии
    for (let i = -5; i <= 5; i++) {
        const x = cx + i * SCALE_FACTOR;
        ctx.beginPath();
        ctx.moveTo(x, 0);
        ctx.lineTo(x, h);
        ctx.stroke();
    }

    // Горизонтальные линии
    for (let i = -5; i <= 5; i++) {
        const y = cy - i * SCALE_FACTOR;
        ctx.beginPath();
        ctx.moveTo(0, y);
        ctx.lineTo(w, y);
        ctx.stroke();
    }
}

// Оси координат
function drawAxes(ctx, w, h, cx, cy) {
    // Ось X
    ctx.beginPath();
    ctx.moveTo(0, cy);
    ctx.lineTo(w, cy);
    ctx.strokeStyle = "#333";
    ctx.lineWidth = 2;
    ctx.stroke();

    // Ось Y
    ctx.beginPath();
    ctx.moveTo(cx, 0);
    ctx.lineTo(cx, h);
    ctx.stroke();

    // Стрелки
    ctx.fillStyle = "#333";

    // Стрелка X
    ctx.beginPath();
    ctx.moveTo(w - 10, cy - 5);
    ctx.lineTo(w, cy);
    ctx.lineTo(w - 10, cy + 5);
    ctx.fill();

    // Стрелка Y
    ctx.beginPath();
    ctx.moveTo(cx - 5, 10);
    ctx.lineTo(cx, 0);
    ctx.lineTo(cx + 5, 10);
    ctx.fill();

    // Подписи
    ctx.font = "14px Arial";
    ctx.fillText("X", w - 15, cy - 10);
    ctx.fillText("Y", cx + 10, 15);

    // Деления
    ctx.font = "12px Arial";
    for (let i = -4; i <= 4; i++) {
        if (i === 0) continue;

        // Деления на оси X
        const xPos = cx + i * SCALE_FACTOR;
        ctx.fillText(i, xPos - 5, cy + 15);

        // Деления на оси Y
        const yPos = cy - i * SCALE_FACTOR;
        ctx.fillText(i, cx - 20, yPos + 4);
    }
}

// Область попадания (вариант 470354)
function drawArea(ctx, cx, cy, r) {
    const scale = SCALE_FACTOR;

    // Фиолетовый с прозрачностью
    ctx.fillStyle = "rgba(106, 13, 173, 0.3)";
    ctx.strokeStyle = "#6A0DAD";
    ctx.lineWidth = 2;

    ctx.beginPath();

    // 1. Четверть окружности (левый верхний квадрант)
    // Центр в (0,0), радиус R/2, углы от π до 3π/2
    ctx.arc(cx, cy, (r/2) * scale, Math.PI, Math.PI * 1.5, false);
    ctx.lineTo(cx, cy);

    // 2. Треугольник (правый верхний квадрант)
    // Вершины: (0,0), (R/2,0), (0,R)
    ctx.moveTo(cx, cy);
    ctx.lineTo(cx + (r/2) * scale, cy);
    ctx.lineTo(cx, cy - r * scale);
    ctx.lineTo(cx, cy);

    // 3. Прямоугольник (левый нижний квадрант)
    // От (0,0) до (-R, R/2)
    ctx.moveTo(cx, cy);
    ctx.lineTo(cx - r * scale, cy);
    ctx.lineTo(cx - r * scale, cy + (r/2) * scale);
    ctx.lineTo(cx, cy + (r/2) * scale);
    ctx.lineTo(cx, cy);

    ctx.fill();
    ctx.stroke();
}

// Отрисовка точек
function drawPoints(ctx, cx, cy, r) {
    if (!currentPoints || currentPoints.length === 0) {
        console.log('Нет точек для отрисовки');
        return;
    }

    console.log('Рисуем', currentPoints.length, 'точек');

    const scale = SCALE_FACTOR;

    currentPoints.forEach(point => {
        // Фильтруем по текущему R
        if (Math.abs(point.r - r) > 0.1) return;

        const canvasX = cx + point.x * scale;
        const canvasY = cy - point.y * scale;

        // Отрисовываем точку
        ctx.beginPath();
        ctx.arc(canvasX, canvasY, 5, 0, Math.PI * 2);
        ctx.fillStyle = point.result ? "#4CAF50" : "#f44336";
        ctx.fill();
        ctx.strokeStyle = "#333";
        ctx.lineWidth = 1;
        ctx.stroke();

        console.log('Точка:', point.x, point.y, point.result ? 'попадание' : 'промах');
    });
}

// Обновление точек из Java
function updatePointsFromJava() {
    // Эта функция будет вызываться из JSF
    console.log('Запрос точек из Java...');

    // Временная заглушка
    currentPoints = [
        {x: 1.0, y: 2.0, r: 2, result: true},
        {x: -1.5, y: -1.0, r: 2, result: false}
    ];

    const r = getCurrentR();
    drawGraph(r);
}

// Получение текущего R
function getCurrentR() {
    const rDisplay = document.getElementById('currentRValue');
    if (rDisplay) {
        return parseFloat(rDisplay.textContent) || 2;
    }

    const checkedRadio = document.querySelector('input[name*=":r"]:checked');
    return checkedRadio ? parseFloat(checkedRadio.value) : 2;
}

// Для использования из main.xhtml
window.initGraph = initGraph;
window.drawGraph = drawGraph;
window.updatePoints = updatePointsFromJava;
window.getCurrentR = getCurrentR;

// Автоматическая инициализация
document.addEventListener('DOMContentLoaded', function() {
    setTimeout(() => {
        if (window.initGraph) {
            window.initGraph(getCurrentR());
        }
    }, 500);
});