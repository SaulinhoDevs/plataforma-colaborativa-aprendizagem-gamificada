document.addEventListener('DOMContentLoaded', () => {
    const ctx = document.getElementById('performanceChart');

    // Dados de exemplo para o gráfico. No futuro, estes dados virão do backend.
    const performanceData = {
        labels: ['História', 'Geografia', 'Ciência', 'Matemática', 'Artes'],
        datasets: [{
            label: 'Precisão Média (%)',
            data: [88, 92, 75, 68, 95], // Valores de exemplo
            backgroundColor: 'rgba(139, 92, 246, 0.2)',
            borderColor: 'rgba(139, 92, 246, 1)',
            borderWidth: 2,
            pointBackgroundColor: 'rgba(139, 92, 246, 1)',
            pointBorderColor: '#fff',
            pointHoverBackgroundColor: '#fff',
            pointHoverBorderColor: 'rgba(139, 92, 246, 1)'
        }]
    };

    if (ctx) {
        new Chart(ctx, {
            type: 'radar', // Tipo de gráfico (radar, bar, line, etc.)
            data: performanceData,
            options: {
                responsive: true,
                maintainAspectRatio: true,
                scales: {
                    r: {
                        angleLines: {
                            color: '#e2e8f0'
                        },
                        grid: {
                            color: '#e2e8f0'
                        },
                        pointLabels: {
                            font: {
                                size: 12,
                                family: "'Poppins', sans-serif"
                            },
                            color: '#4a5568'
                        },
                        ticks: {
                            backdropColor: 'rgba(255, 255, 255, 0.75)',
                            stepSize: 20,
                            max: 100,
                            min: 0
                        }
                    }
                },
                plugins: {
                    legend: {
                        position: 'top',
                    },
                    tooltip: {
                        callbacks: {
                            label: function(context) {
                                return `${context.dataset.label}: ${context.raw}%`;
                            }
                        }
                    }
                }
            }
        });
    }
});
