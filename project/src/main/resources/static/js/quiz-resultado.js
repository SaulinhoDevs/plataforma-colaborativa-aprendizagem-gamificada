document.addEventListener('DOMContentLoaded', () => {

    const scoreValueEl = document.getElementById('score-value');

    // Verifica se o elemento e a variável finalScore existem antes de animar
    if (scoreValueEl && typeof finalScore !== 'undefined') {
        animateValue(scoreValueEl, 0, finalScore, 1000);
    }

    /**
     * Anima um número de um valor inicial para um final durante um período.
     * @param {HTMLElement} element - O elemento cujo textContent será animado.
     * @param {number} start - O valor inicial.
     * @param {number} end - O valor final.
     * @param {number} duration - A duração da animação em milissegundos.
     */
    function animateValue(element, start, end, duration) {
        let startTimestamp = null;
        const step = (timestamp) => {
            if (!startTimestamp) startTimestamp = timestamp;
            const progress = Math.min((timestamp - startTimestamp) / duration, 1);
            element.textContent = Math.floor(progress * (end - start) + start);
            if (progress < 1) {
                window.requestAnimationFrame(step);
            }
        };
        window.requestAnimationFrame(step);
    }

});
