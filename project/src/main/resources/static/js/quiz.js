    // --- LÓGICA FRONT-END ORIENTADA A DADOS ---

    // 1. DADOS (No futuro, virão de uma API/Thymeleaf)
    const quizData = {
        title: "Capitais da Europa",
        category: "Geografia",
        questions: [
            {
                text: "Qual é a capital da França?",
                answers: [
                    { text: "Paris", isCorrect: true },
                    { text: "Berlim", isCorrect: false },
                    { text: "Madrid", isCorrect: false },
                    { text: "Lisboa", isCorrect: false }
                ]
            },
            {
                text: "Qual é a capital da Alemanha?",
                answers: [
                    { text: "Viena", isCorrect: false },
                    { text: "Praga", isCorrect: false },
                    { text: "Berlim", isCorrect: true },
                    { text: "Roma", isCorrect: false }
                ]
            },
             {
                text: "Qual é a capital da Itália?",
                answers: [
                    { text: "Roma", isCorrect: true },
                    { text: "Atenas", isCorrect: false },
                    { text: "Amsterdã", isCorrect: false },
                    { text: "Dublin", isCorrect: false }
                ]
            }
            // Adicione mais perguntas aqui para testar
        ]
    };

    // 2. ESTADO DA APLICAÇÃO
    let currentQuestionIndex = 0;
    let selectedAnswer = null;
    let isAnswered = false;

    // 3. REFERÊNCIAS AOS ELEMENTOS DO DOM
    const questionTextEl = document.getElementById('question-text');
    const answersContainerEl = document.getElementById('answers-container');
    const actionBtn = document.getElementById('action-btn');
    const progressBar = document.getElementById('quiz-progress-bar');
    const progressText = document.getElementById('progress-text');


    // 4. FUNÇÕES
    function renderQuestion() {
        isAnswered = false;
        const question = quizData.questions[currentQuestionIndex];

        // Atualiza o texto da pergunta
        questionTextEl.textContent = question.text;

        // Limpa as respostas anteriores
        answersContainerEl.innerHTML = '';
        answersContainerEl.classList.remove('answered');

        // Cria e adiciona os novos botões de resposta
        question.answers.forEach(answer => {
            const button = document.createElement('button');
            button.className = 'answer-btn';
            button.innerHTML = `
                <span class="answer-text">${answer.text}</span>
                <i class="feedback-icon"></i>
            `;
            // Armazena a informação se a resposta é correta no próprio elemento
            button.dataset.correct = answer.isCorrect;
            answersContainerEl.appendChild(button);
        });

        // Atualiza a barra de progresso
        const progressPercentage = ((currentQuestionIndex + 1) / quizData.questions.length) * 100;
        progressBar.style.width = `${progressPercentage}%`;
        progressBar.setAttribute('aria-valuenow', progressPercentage);
        progressText.innerHTML = `Pergunta <strong>${currentQuestionIndex + 1}</strong> de ${quizData.questions.length}`;

        // Reseta o botão de ação
        actionBtn.textContent = 'Confirmar';
        actionBtn.disabled = true;
    }

    function handleAnswerClick(event) {
        const button = event.target.closest('.answer-btn');
        if (button && !isAnswered) {
            // Remove a seleção de outros botões
            if (selectedAnswer) {
                selectedAnswer.classList.remove('selected');
            }
            // Marca o novo botão como selecionado
            selectedAnswer = button;
            selectedAnswer.classList.add('selected');
            actionBtn.disabled = false;
        }
    }

    function handleActionClick() {
        if (isAnswered) {
            // Lógica para ir para a próxima pergunta
            currentQuestionIndex++;
            if (currentQuestionIndex < quizData.questions.length) {
                renderQuestion();
            } else {
                // Fim do quiz
                alert('Quiz finalizado! Você completou todas as perguntas.');
                window.location.href = '#'; // Redirecionar para a página de resultados
            }
        } else {
            // Lógica para confirmar a resposta
            if (!selectedAnswer) return;

            isAnswered = true;
            answersContainerEl.classList.add('answered');
            const isCorrect = selectedAnswer.dataset.correct === 'true';
            const feedbackIcon = selectedAnswer.querySelector('.feedback-icon');

            if (isCorrect) {
                selectedAnswer.classList.add('correct');
                feedbackIcon.classList.add('bi', 'bi-check-circle-fill');
            } else {
                selectedAnswer.classList.add('incorrect');
                feedbackIcon.classList.add('bi', 'bi-x-circle-fill');
                
                // Mostra qual era a resposta correta
                const correctButton = answersContainerEl.querySelector('[data-correct="true"]');
                if (correctButton) {
                    correctButton.classList.add('correct');
                    correctButton.querySelector('.feedback-icon').classList.add('bi', 'bi-check-circle-fill');
                }
            }

            // Prepara o botão para a próxima ação
            if (currentQuestionIndex < quizData.questions.length - 1) {
                actionBtn.textContent = 'Próxima Pergunta';
            } else {
                actionBtn.textContent = 'Ver Resultados';
            }
        }
    }

    // 5. INICIALIZAÇÃO
    document.addEventListener('DOMContentLoaded', () => {
        answersContainerEl.addEventListener('click', handleAnswerClick);
        actionBtn.addEventListener('click', handleActionClick);
        
        // Renderiza a primeira pergunta
        renderQuestion();
    });
