document.addEventListener('DOMContentLoaded', () => {
    const deleteConfirmModal = document.getElementById('deleteConfirmModal');
    if (deleteConfirmModal) {
        deleteConfirmModal.addEventListener('show.bs.modal', function (event) {
            // Botão que acionou o modal
            const button = event.relatedTarget;
            
            // Extrai informações dos atributos data-*
            const quizId = button.getAttribute('data-quiz-id');
            const quizTitle = button.getAttribute('data-quiz-title');

            // Atualiza o conteúdo do modal
            const modalTitle = deleteConfirmModal.querySelector('#quiz-title-modal');
            const quizIdInput = deleteConfirmModal.querySelector('#quiz-id-input');
            const deleteForm = deleteConfirmModal.querySelector('#delete-form');

            if (modalTitle) {
                modalTitle.textContent = quizTitle;
            }
            if (quizIdInput) {
                quizIdInput.value = quizId;
            }
            // Opcional: se quiser que o form action seja dinâmico também
            // if (deleteForm) {
            //     deleteForm.action = `/quiz/delete/${quizId}`; 
            // }
        });
    }
});
