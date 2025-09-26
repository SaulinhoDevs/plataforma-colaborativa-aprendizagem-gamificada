document.addEventListener('DOMContentLoaded', () => {
    // --- LÓGICA EXISTENTE PARA O MODAL DE EXCLUSÃO ---
    const deleteConfirmModal = document.getElementById('deleteModal'); // Corrigido para corresponder ao ID do HTML
    if (deleteConfirmModal) {
        deleteConfirmModal.addEventListener('show.bs.modal', function (event) {
            const button = event.relatedTarget;
            const quizId = button.getAttribute('data-quiz-id');
            const quizTitle = button.getAttribute('data-quiz-title');

            const modalTitle = deleteConfirmModal.querySelector('.modal-title');
            const modalBody = deleteConfirmModal.querySelector('.modal-body');
            
            // Atualiza o conteúdo do modal com os dados do quiz a ser apagado
            if(modalTitle) modalTitle.textContent = `Apagar Quiz: ${quizTitle}`;
            if(modalBody) modalBody.textContent = `Tem a certeza de que deseja apagar o quiz "${quizTitle}"? Esta ação não pode ser desfeita.`;
            
            // Configura o botão de confirmação para submeter o formulário de exclusão
            const confirmDeleteBtn = deleteConfirmModal.querySelector('.btn-danger');
            if(confirmDeleteBtn) {
                // Aqui seria a lógica para apagar, por exemplo, submeter um formulário
                // confirmDeleteBtn.onclick = () => document.getElementById(`deleteForm-${quizId}`).submit();
            }
        });
    }

    // --- NOVA LÓGICA PARA O BOTÃO DE DESFAZER (UNDO) ---
    const undoBtn = document.getElementById('undoBtn');
    if (undoBtn) {
        undoBtn.addEventListener('click', () => {
            // Mostra uma confirmação simples ao utilizador
            if (confirm('Tem a certeza de que deseja desfazer a última ação registada?')) {
                // Envia um pedido POST para o endpoint de undo
                fetch('/command/undo', {
                    method: 'POST',
                    headers: {
                        // O CSRF token seria necessário se estivesse ativado no Spring Security
                        // 'X-CSRF-TOKEN': 'valor_do_token'
                    }
                })
                .then(response => {
                    if (response.ok) {
                        alert('Ação desfeita com sucesso!');
                        // Recarrega a página para refletir as alterações (ex: um quiz submetido pode voltar a aparecer)
                        window.location.reload();
                    } else {
                        alert('Não foi possível desfazer a ação. Pode não haver ações para desfazer.');
                    }
                })
                .catch(error => {
                    console.error('Erro ao tentar desfazer a ação:', error);
                    alert('Ocorreu um erro de rede ao tentar desfazer a ação.');
                });
            }
        });
    }
});

