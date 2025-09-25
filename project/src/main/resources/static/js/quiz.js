// --- ELEMENTOS DO DOM ---
const questionTextEl = document.getElementById('question-text');
const answersContainerEl = document.getElementById('answers-container');
const actionBtnEl = document.getElementById('action-btn');
const progressTextEl = document.getElementById('progress-text');
const progressBarEl = document.getElementById('quiz-progress-bar');

// --- ESTADO DO QUIZ ---
let currentQuestionIndex = 0;
let selectedAnswerId = null;
let score = 0;
let answered = false;

// --- FUNÇÕES ---

function loadQuestion() {
    answered = false;
    selectedAnswerId = null;
    const currentQuestion = quizData.perguntas[currentQuestionIndex];

    // CORREÇÃO: Usar a propriedade 'text' em vez de 'texto'
    questionTextEl.textContent = currentQuestion.text;

    answersContainerEl.innerHTML = '';
    // CORREÇÃO: Usar a propriedade 'answers' em vez de 'respostas'
    currentQuestion.answers.forEach(answer => {
        const button = document.createElement('button');
        button.className = 'btn answer-btn';
        // CORREÇÃO: Usar a propriedade 'text' em vez de 'texto'
        button.textContent = answer.text;
        button.dataset.id = answer.id;
        button.onclick = () => selectAnswer(button, answer.id);
        answersContainerEl.appendChild(button);
    });

    updateProgress();
    actionBtnEl.textContent = 'Confirmar';
    actionBtnEl.disabled = true;
    actionBtnEl.classList.remove('btn-success', 'btn-info');
    actionBtnEl.classList.add('btn-primary');
}

function selectAnswer(btnElement, answerId) {
    if (answered) return;
    document.querySelectorAll('.answer-btn').forEach(btn => btn.classList.remove('selected'));
    btnElement.classList.add('selected');
    selectedAnswerId = answerId;
    actionBtnEl.disabled = false;
}

function confirmAnswer() {
    if (answered || selectedAnswerId === null) return;
    answered = true;

    const currentQuestion = quizData.perguntas[currentQuestionIndex];
    // CORREÇÃO: Usar a propriedade 'answers' e 'correct'
    const correctAnswer = currentQuestion.answers.find(r => r.correct);

    const isCorrect = selectedAnswerId === correctAnswer.id;
    if (isCorrect) {
        score++;
    }

    document.querySelectorAll('.answer-btn').forEach(btn => {
        const btnId = parseInt(btn.dataset.id);
        if (btnId === correctAnswer.id) {
            btn.classList.add('correct');
        } else if (btnId === selectedAnswerId) {
            btn.classList.add('incorrect');
        }
        btn.disabled = true;
    });

    actionBtnEl.textContent = 'Próxima Pergunta';
    actionBtnEl.classList.remove('btn-primary');
    actionBtnEl.classList.add(isCorrect ? 'btn-success' : 'btn-info');
    actionBtnEl.disabled = false;
}

function nextQuestion() {
    currentQuestionIndex++;
    if (currentQuestionIndex < quizData.perguntas.length) {
        loadQuestion();
    } else {
        finishQuiz();
    }
}

function finishQuiz() {
    const cardBody = document.querySelector('.quiz-card .card-body');
    const accuracy = ((score / quizData.perguntas.length) * 100).toFixed(0);

    cardBody.innerHTML = `
        <div class="text-center">
            <h2 class="finish-title">Quiz Concluído!</h2>
            <p class="finish-subtitle">Você acertou ${score} de ${quizData.perguntas.length} perguntas.</p>
            <div class="accuracy-display">
                <span class="accuracy-value">${accuracy}%</span>
                <span class="accuracy-label">de precisão</span>
            </div>
            <a href="/dashboard" class="btn btn-primary action-btn mt-4">Voltar ao Dashboard</a>
        </div>
    `;
    actionBtnEl.style.display = 'none';
}

function updateProgress() {
    const totalQuestions = quizData.perguntas.length;
    const currentNumber = currentQuestionIndex + 1;
    const progressPercentage = (currentNumber / totalQuestions) * 100;

    progressTextEl.innerHTML = `Pergunta <strong>${currentNumber}</strong> de ${totalQuestions}`;
    progressBarEl.style.width = `${progressPercentage}%`;
    progressBarEl.setAttribute('aria-valuenow', progressPercentage);
}

// --- EVENT LISTENERS ---
actionBtnEl.addEventListener('click', () => {
    if (answered) {
        nextQuestion();
    } else {
        confirmAnswer();
    }
});

// --- INICIALIZAÇÃO ---
if (typeof quizData !== 'undefined' && quizData.perguntas && quizData.perguntas.length > 0) {
    loadQuestion();
} else {
    console.error("Dados do quiz (quizData) não foram encontrados ou o quiz não tem perguntas. Verifique a injeção de dados via Thymeleaf.");
    const container = document.querySelector('.quiz-card .card-body') || document.body;
    container.innerHTML = `
        <div class="text-center p-4">
            <h2 class="finish-title">Erro ao Carregar Quiz</h2>
            <p class="finish-subtitle">Este quiz não pôde ser carregado ou não contém perguntas.</p>
            <a href="/dashboard" class="btn btn-primary action-btn mt-4">Voltar ao Dashboard</a>
        </div>
    `;
    if (actionBtnEl) actionBtnEl.style.display = 'none';
}

