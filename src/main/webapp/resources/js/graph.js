const SCALE_FACTOR = 35;
let currentPoints = [];

// Инициализация графика
function initGraph(r = 2) {
    const canvas = document.getElementById('graph');
    if (!canvas) return;

    drawGraph(r);

    // Обработчик кликов
    canvas.addEventListener('click', function(event) {
        handleCanvasClick(event, r);
    });
}

// Обработка клика по графику
function handleCanvasClick(event, r) {
    if (!r || r < 1 || r > 5) {
        alert('Сначала выберите радиус R от 1 до 5');
        return;
    }

    const canvas = document.getElementById('graph');
    const rect = canvas.getBoundingClientRect();
    const clickX = event.clientX - rect.left;
    const clickY = event.clientY - rect.top;

    // Преобразование координат canvas в математические
    const x = ((clickX - canvas.width / 2) / SCALE_FACTOR).toFixed(2);
    const y = ((canvas.height / 2 - clickY) / SCALE_FACTOR).toFixed(2);

    // Отправка через JSF форму
    const form = document.querySelector('form[name="pointForm"]');
    if (form) {
        // Находим скрытые поля для отправки
        const xInput = document.querySelector('[id*="x_input"]') ||
                      document.querySelector('[name*="x"]');
        const yInput = document.querySelector('[id*="y_input"]') ||
                      document.querySelector('[name*="y"]');

        if (xInput && yInput) {
            xInput.value = x;
            yInput.value = y;

            // Находим кнопку отправки
            const submitBtn = document.querySelector('[id*="submit_btn"]') ||
                             document.querySelector('button[type="submit"]');
            if (submitBtn) {
                submitBtn.click();
            }
        }
    }
}

// Отрисовка графика
function drawGraph(r) {
    if (!r) return;

    const canvas = document.getElementById('graph');
    if (!canvas) return;

    const ctx = canvas.getContext('2d');
    const w = canvas.width;
    const h = canvas.height;
    const cx = w / 2;
    const cy = h / 2;

    // Очистка
    ctx.clearRect(0, 0, w, h);

    // Оси координат
    drawAxes(ctx, w, h, cx, cy);

    // Область попадания (ваш вариант 470354)
    drawArea(ctx, cx, cy, r);

    // Точки
    drawPoints(ctx, cx, cy, r);
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
}

// Область попадания (ваш вариант 470354)
function drawArea(ctx, cx, cy, r) {
    const scale = SCALE_FACTOR;

    // Сиреневый цвет с прозрачностью
    ctx.fillStyle = "rgba(147, 112, 219, 0.3)";
    ctx.strokeStyle = "#6A0DAD";
    ctx.lineWidth = 2;

    ctx.beginPath();

    // 1. Левый верхний: прямоугольник (-R/2 до 0, 0 до R)
    ctx.rect(cx, cy - r * scale, -(r/2) * scale, -r * scale);

    // 2. Правый верхний: четверть окружности радиусом R
    ctx.moveTo(cx, cy);
    ctx.arc(cx, cy, r * scale, 0, Math.PI / 2, false);
    ctx.lineTo(cx, cy);

    // 3. Левый нижний: прямоугольный треугольник
    // Вершины: (0,0), (-R,0), (0,-R/2)
    ctx.moveTo(cx, cy);
    ctx.lineTo(cx - r * scale, cy);
    ctx.lineTo(cx, cy + (r/2) * scale);
    ctx.closePath();

    ctx.fill();
    ctx.stroke();
}

// Отрисовка точек
function drawPoints(ctx, cx, cy, r) {
    if (!currentPoints || currentPoints.length === 0) return;

    const scale = SCALE_FACTOR;

    currentPoints.forEach(point => {
        if (Math.abs(point.r - r) < 0.001) { // Точки для текущего R
            const canvasX = cx + point.x * scale;
            const canvasY = cy - point.y * scale;

            ctx.beginPath();
            ctx.arc(canvasX, canvasY, 5, 0, Math.PI * 2);
            ctx.fillStyle = point.result ? "#4CAF50" : "#f44336";
            ctx.fill();
            ctx.strokeStyle = "#333";
            ctx.lineWidth = 1;
            ctx.stroke();
        }
    });
}

// Обновление точек (будет вызываться из JSF)
function updatePoints(points) {
    currentPoints = points || [];
    const r = getCurrentR();
    drawGraph(r);
}

// Получение текущего R из формы
function getCurrentR() {
    const rInputs = document.querySelectorAll('input[name*="r"]');
    for (let input of rInputs) {
        if (input.checked) {
            return parseFloat(input.value);
        }
    }
    return 2; // Значение по умолчанию
}

// Для использования в main.xhtml
window.initGraph = initGraph;
window.drawGraph = drawGraph;
window.updatePoints = updatePoints;
window.handleCanvasClick = handleCanvasClick;