import { Chart as ChartJS, CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend, Color } from 'chart.js';
import { Bar } from 'react-chartjs-2';
import { useState } from 'react';
import { useEffect } from 'react';
import { IRevenue } from '../../../models/IRevenue';
import PurchaseService from '../../../services/purchase.service';


ChartJS.register(
    CategoryScale,
    LinearScale,
    BarElement,
    Title,
    Tooltip,
    Legend
);

const SalesPerMonth = () => {

    const [chartData, setChartData] = useState<any>({
        datasets: []
    });
    const [chartOptions, setChartOptions] = useState({});
    const [revenueData, setRevenueData] = useState<IRevenue[]>([]);
    const dataPoints = revenueData.map(revenue => revenue.revenue);
    const labels = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];

    const bgc: any = [];
    const copyDataPoints: any = [...dataPoints];
    const max = Math.max(...dataPoints);
    console.log(max);

    const highestValueColor = dataPoints.map((dataPoint, index) => {
        const color = dataPoint === max ? 'rgba(255, 1, 60, 0.6)' : 'rgba(255, 251, 1, 0.6)';
        bgc.push(color);

        if (max === dataPoint) {
            copyDataPoints.splice(index, 1, 0);
        }
    })

    const secondMax = Math.max(...copyDataPoints);
    const secondHighestValueColor = copyDataPoints.map((dataPoint: number, index: any) => {
        if (secondMax === dataPoint) {
            bgc.splice(index, 1, 'rgba(255, 111, 1, 0.6)');
        }
    })

    useEffect(() => {
        PurchaseService.getYearlyRevenue().then(response => {
            setRevenueData(response.data);
            console.log(response);
        });
    }, []);

    // 'rgba(255, 251, 1, 0.6)'//yellow
    // 'rgba(255, 1, 60, 0.6)'//red
    // 'rgba(255, 111, 1, 1)'//orange
    // 'rgba( 162, 1, 255, 1)'//purple


    useEffect(() => {
        setChartData({
            labels: labels,
            datasets: [
                {
                    label: "2022",
                    data: dataPoints,
                    backgroundColor: bgc,
                    borderColor: bgc,
                    color: "rgb(255, 187, 114, 1)",
                    borderWidth: 1,
                },
            ],
        });
        setChartOptions({
            responsive: true,
            maintainAspectRatio: false,
            plugins: {
                legend: {
                    labels: {
                        color: "white",
                        font: {
                            display: true,
                            size: 16,
                        }
                    },
                    position: "top",
                },
                title: {
                    display: true,
                    text: "Total sales per month chart",
                    color: "white",
                    font: {
                        display: true,
                        size: 16,
                    }
                },
            },
            scales: {
                y: {
                    grid: {
                        color: 'rgba(255, 255, 255, 0.2)'
                    },
                    ticks: {
                        color: 'white', beginAtZero: true
                    }
                },
                x: {
                    grid: {
                        color: 'rgba(255, 255, 255, 0.2)'
                    },
                    ticks: {
                        color: 'white', beginAtZero: true
                    }
                },
            },
        });
    }, [revenueData]);

    return (
        <Bar options={chartOptions} data={chartData} />
    );
};

export default SalesPerMonth;