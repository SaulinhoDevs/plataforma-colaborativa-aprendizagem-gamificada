document.addEventListener('DOMContentLoaded', () => {
    const searchInput = document.getElementById('searchInput');
    const resultsTable = document.getElementById('resultsTable');
    const tableBody = resultsTable ? resultsTable.querySelector('tbody') : null;

    if (searchInput && tableBody) {
        const rows = tableBody.querySelectorAll('tr');

        searchInput.addEventListener('keyup', () => {
            const searchTerm = searchInput.value.toLowerCase();

            rows.forEach(row => {
                // Procura pelo nome do aluno, que está no primeiro <td>
                const studentName = row.cells[0].textContent.toLowerCase();
                
                // Mostra ou esconde a linha com base na pesquisa
                if (studentName.includes(searchTerm)) {
                    row.style.display = '';
                } else {
                    row.style.display = 'none';
                }
            });
        });
    }
});
