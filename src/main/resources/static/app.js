document.addEventListener('DOMContentLoaded', () => {

    // --- Theme Management ---
    const themeToggle = document.getElementById('checkbox');
    const htmlElement = document.documentElement;

    // Check for saved theme or preference
    const currentTheme = localStorage.getItem('theme') ? localStorage.getItem('theme') : null;

    if (currentTheme) {
        htmlElement.setAttribute('data-theme', currentTheme);
        if (currentTheme === 'light') {
            themeToggle.checked = true;
        }
    } else {
        // Default is dark mode per CSS, ensure checkbox matches
        themeToggle.checked = false;
    }

    themeToggle.addEventListener('change', function (e) {
        if (e.target.checked) {
            htmlElement.setAttribute('data-theme', 'light');
            localStorage.setItem('theme', 'light');
        } else {
            htmlElement.setAttribute('data-theme', 'dark');
            localStorage.setItem('theme', 'dark');
        }
    });


    // --- Core UI Elements ---
    const refreshBtn = document.getElementById('refreshBtn');

    // Metrics
    const avgAQIEl = document.getElementById('avgAQI');
    const maxAQIEl = document.getElementById('maxAQI');
    const minAQIEl = document.getElementById('minAQI');
    const avgAqiProgress = document.getElementById('avgAqiProgress');

    // Banner
    const alertBanner = document.getElementById('alertBanner');
    const alertIcon = document.getElementById('alertIcon');
    const alertText = document.getElementById('alertText');

    // Temperature
    const tempCircle = document.getElementById('tempCircle');
    const avgTempText = document.getElementById('avgTempText');
    const tempAnalysisTitle = document.getElementById('tempAnalysisTitle');
    const tempAnalysisText = document.getElementById('tempAnalysisText');

    let isFetching = false;

    // Advanced Counter Animation (Supports decimals)
    const animateValue = (element, endValue, duration, isDecimal = false) => {
        if (isNaN(endValue)) {
            element.textContent = endValue;
            return;
        }

        const startTimestamp = performance.now();
        const startValue = 0;
        const end = parseFloat(endValue);

        const step = (currentTimestamp) => {
            const progress = Math.min((currentTimestamp - startTimestamp) / duration, 1);
            const easeProgress = 1 - Math.pow(1 - progress, 4); // easeOutQuart
            const currentVal = easeProgress * (end - startValue) + startValue;

            if (isDecimal) {
                element.textContent = currentVal.toFixed(1) + '°'; // format for temp
            } else {
                element.textContent = Math.floor(currentVal); // format for AQI
            }

            if (progress < 1) {
                window.requestAnimationFrame(step);
            } else {
                // Ensure exact end value is shown
                element.textContent = isDecimal ? end.toFixed(1) + '°' : Math.floor(end);
            }
        };

        window.requestAnimationFrame(step);
    };

    // Calculate circular stroke dasharray
    const updateCircularProgress = (element, value, maxVal = 50) => {
        // Value bounded to 0-maxVal (50C is max typical temp for scale)
        const percent = Math.min(Math.max((value / maxVal) * 100, 0), 100);
        // Circle stroke-dasharray format: stroke, gap. Circumference is 100 for this SVG setup.
        element.setAttribute('stroke-dasharray', `${percent}, 100`);

        // Dynamically set color of temp ring
        if (value > 35) element.style.stroke = '#ef4444'; // Red hot
        else if (value > 25) element.style.stroke = '#f59e0b'; // Warm orange
        else if (value > 15) element.style.stroke = '#10b981'; // Green nice
        else element.style.stroke = '#3b82f6'; // Blue cold
    };

    // Update AQI Linear Progress
    const updateLinearProgress = (element, value, maxVal = 300) => {
        const percent = Math.min(Math.max((value / maxVal) * 100, 0), 100);
        element.style.width = `${percent}%`;

        if (value > 200) element.style.backgroundColor = '#ef4444';
        else if (value > 150) element.style.backgroundColor = '#f59e0b';
        else element.style.backgroundColor = '#10b981';
    }

    const fetchAnalytics = async () => {
        if (isFetching) return;
        isFetching = true;

        refreshBtn.classList.add('spinning');
        alertBanner.className = 'alert-banner loading';
        alertIcon.textContent = '⏳';
        alertText.textContent = 'Fetching latest environment data...';

        try {
            const response = await fetch('/analytics');
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            const data = await response.json();

            // Native Animations for core metrics
            animateValue(avgAQIEl, data.averageAQI, 1000);
            animateValue(maxAQIEl, data.maxAQI, 1000);
            animateValue(minAQIEl, data.minAQI, 1000);
            animateValue(avgTempText, data.averageTemperature, 1200, true);

            // Trigger Visual Bars
            updateLinearProgress(avgAqiProgress, data.averageAQI);
            updateCircularProgress(tempCircle, data.averageTemperature);

            // Populate Temperature Analysis
            tempAnalysisText.textContent = data.temperatureAnalysis;

            // Generate Title based on temp
            if (data.averageTemperature > 35) tempAnalysisTitle.textContent = "Extreme Heat Detected";
            else if (data.averageTemperature > 25) tempAnalysisTitle.textContent = "Warm Conditions";
            else if (data.averageTemperature > 15) tempAnalysisTitle.textContent = "Moderate Climate";
            else tempAnalysisTitle.textContent = "Cold Conditions";

            // Update AQI Alert
            const rawAlert = data.alert || "";
            if (rawAlert.includes("Severe")) {
                alertBanner.className = 'alert-banner severe';
                alertIcon.textContent = '🚨';
                alertText.textContent = rawAlert;
            } else if (rawAlert.includes("High")) {
                alertBanner.className = 'alert-banner high';
                alertIcon.textContent = '⚠';
                alertText.textContent = rawAlert;
            } else if (rawAlert.includes("Normal")) {
                alertBanner.className = 'alert-banner normal';
                alertIcon.textContent = '🌿';
                alertText.textContent = rawAlert;
            } else {
                alertBanner.className = 'alert-banner normal';
                alertIcon.textContent = '💡';
                alertText.textContent = rawAlert || 'Data Received';
            }

        } catch (error) {
            console.error('Error fetching analytics:', error);
            alertBanner.className = 'alert-banner severe';
            alertIcon.textContent = '❌';
            alertText.textContent = 'Connection Error: Unable to fetch data';

            avgAQIEl.textContent = 'N/A';
            maxAQIEl.textContent = 'N/A';
            minAQIEl.textContent = 'N/A';

        } finally {
            isFetching = false;
            setTimeout(() => {
                refreshBtn.classList.remove('spinning');
            }, 600);
        }
    };

    refreshBtn.addEventListener('click', fetchAnalytics);
    fetchAnalytics();
    setInterval(fetchAnalytics, 30000);
});
