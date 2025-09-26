document.addEventListener('DOMContentLoaded', () => {
    // Contador de caracteres para a nova publicação
    const newPostTextarea = document.getElementById('newPostTextarea');
    const charCounter = document.getElementById('charCounter');
    const maxChars = 280;

    if (newPostTextarea && charCounter) {
        newPostTextarea.addEventListener('input', () => {
            const remaining = maxChars - newPostTextarea.value.length;
            charCounter.textContent = remaining;
            if (remaining < 20) {
                charCounter.style.color = 'red';
            } else {
                charCounter.style.color = '#6c757d';
            }
        });
    }

    // Lógica para mostrar/ocultar comentários
    document.querySelectorAll('.toggle-comments').forEach(button => {
        button.addEventListener('click', () => {
            const postCard = button.closest('.post-card');
            const commentSection = postCard.querySelector('.comment-section');
            if (commentSection) {
                if (commentSection.style.display === 'block') {
                    commentSection.style.display = 'none';
                } else {
                    commentSection.style.display = 'block';
                }
            }
        });
    });
});
