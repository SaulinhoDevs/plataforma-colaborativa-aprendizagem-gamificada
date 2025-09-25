document.addEventListener('DOMContentLoaded', () => {

    const perguntasContainer = document.getElementById('perguntas-container');
    const addPerguntaBtn = document.getElementById('add-pergunta-btn');
    const quizForm = document.getElementById('quiz-form');

    // Templates
    const perguntaTemplate = document.getElementById('pergunta-template');
    const respostaTemplate = document.getElementById('resposta-template');
    
    let perguntaCounter = 0;

    // Função para adicionar uma nova pergunta ao formulário
    function adicionarPergunta() {
        const clone = perguntaTemplate.content.cloneNode(true);
        const perguntaCard = clone.querySelector('.pergunta-card');
        
        // Atualiza os atributos 'name' para serem únicos
        perguntaCard.querySelector('.pergunta-texto').name = `perguntas[${perguntaCounter}].texto`;
        const radioInputs = perguntaCard.querySelectorAll('input[type="radio"]');
        radioInputs.forEach(input => {
            input.name = `perguntas[${perguntaCounter}].respostaCorreta`;
        });

        perguntasContainer.appendChild(clone);
        
        // Adiciona 2 respostas por padrão à nova pergunta
        const novaPerguntaCard = perguntasContainer.lastElementChild.querySelector('.pergunta-card');
        adicionarResposta(novaPerguntaCard.querySelector('.add-resposta-btn'));
        adicionarResposta(novaPerguntaCard.querySelector('.add-resposta-btn'));

        perguntaCounter++;
    }

    // Função para adicionar uma nova resposta a uma pergunta
    function adicionarResposta(addButton) {
        const perguntaCard = addButton.closest('.pergunta-card');
        const respostasContainer = perguntaCard.querySelector('.respostas-container');
        const perguntaIndex = Array.from(perguntasContainer.querySelectorAll('.pergunta-card')).indexOf(perguntaCard);
        const respostaCounter = respostasContainer.querySelectorAll('.resposta-item').length;

        const clone = respostaTemplate.content.cloneNode(true);
        
        // Atualiza os atributos 'name' e 'value'
        const radioInput = clone.querySelector('input[type="radio"]');
        radioInput.name = `perguntas[${perguntaIndex}].respostaCorreta`;
        radioInput.value = respostaCounter;

        clone.querySelector('.resposta-texto').name = `perguntas[${perguntaIndex}].respostas[${respostaCounter}].texto`;

        respostasContainer.appendChild(clone);
    }
    
    // Delegação de eventos para os botões de remover e adicionar
    perguntasContainer.addEventListener('click', event => {
        if (event.target.matches('.remove-pergunta-btn')) {
            event.target.closest('.pergunta-card').remove();
            // Re-indexar as perguntas restantes para manter a consistência dos names
            reindexarPerguntas();
        }

        if (event.target.matches('.add-resposta-btn')) {
            adicionarResposta(event.target);
        }

        if (event.target.matches('.remove-resposta-btn')) {
            const respostaItem = event.target.closest('.resposta-item');
            const perguntaCard = respostaItem.closest('.pergunta-card');
            respostaItem.remove();
            // Re-indexar as respostas restantes dentro desta pergunta
            reindexarRespostas(perguntaCard);
        }
    });
    
    // Função para re-indexar os nomes dos campos após a remoção
    function reindexarPerguntas() {
        const perguntaCards = perguntasContainer.querySelectorAll('.pergunta-card');
        perguntaCounter = 0;
        perguntaCards.forEach((card, index) => {
            card.querySelector('.pergunta-texto').name = `perguntas[${index}].texto`;
            card.querySelectorAll('input[type="radio"]').forEach(radio => {
                radio.name = `perguntas[${index}].respostaCorreta`;
            });
            reindexarRespostas(card);
            perguntaCounter++;
        });
    }

    function reindexarRespostas(perguntaCard) {
        const perguntaIndex = Array.from(perguntasContainer.querySelectorAll('.pergunta-card')).indexOf(perguntaCard);
        const respostaItems = perguntaCard.querySelectorAll('.resposta-item');
        respostaItems.forEach((item, index) => {
            item.querySelector('input[type="radio"]').value = index;
            item.querySelector('.resposta-texto').name = `perguntas[${perguntaIndex}].respostas[${index}].texto`;
        });
    }

    // Inicialização
    addPerguntaBtn.addEventListener('click', adicionarPergunta);

    // Adiciona a primeira pergunta quando a página carrega
    adicionarPergunta();
});
