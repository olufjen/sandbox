/**
 * fedsa
 */
// form-handler.js

document.addEventListener('DOMContentLoaded', function() {
    
    const form = document.getElementById('mittSkjema'); // form id mittskjema
    const responsOmrade = document.getElementById('respons_område'); // div id respons_område

    if (!form || !responsOmrade) {
        console.error("Kritisk feil: Kunne ikke finne skjemaet eller responsområdet.");
        return;
    }

    // --- Robust beregning av Context Path (f.eks. /servlet-example) ---
    // Dette sikrer at vi får riktig rotsti, uansett om vi er på /forside.html eller /api/skjema
    const pathSegments = window.location.pathname.split('/');
    // Vi antar at Context Root er det andre segmentet i URLen (første er tom, andre er prosjektnavn)
    const contextRoot = pathSegments[1]; 
    const contextPath = contextRoot ? '/' + contextRoot : '';

    form.addEventListener('submit', function(event) {
        // Forhindrer standard HTML-form innsending
        event.preventDefault(); 
        
        const formData = new URLSearchParams(new FormData(form)).toString();
        
        // Den fullstendige JAX-RS POST-adressen: /servlet-example/api/skjema
        const postUrl = `${contextPath}/api/skjema`; 

        responsOmrade.innerHTML = "Sender data...";
        responsOmrade.style.borderColor = '#ccc'; 

        fetch(postUrl, { 
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: formData
        })
        .then(response => {
            if (!response.ok) {
                // Hvis status er 405, 404 eller annen feil, kast en Error
                return response.text().then(text => {
                    throw new Error(`HTTP ${response.status}: ${text || response.statusText}`);
                });
            }
            return response.text();
        })
        .then(data => {
            // Lenken tilbake til den statiske HTML-siden
            const tilbakeLenke = `${contextPath}/forside.html`; 

            // Oppdaterer DOM med suksessmelding
            responsOmrade.innerHTML = '✅ **Suksess!** ' + data;
            responsOmrade.innerHTML += `<br><a href="${tilbakeLenke}">Tilbake til skjema</a>`; 
            
            responsOmrade.style.borderColor = '#28a745'; // Grønn
        })
        .catch(error => {
            // Viser feilmelding i DOM
            responsOmrade.innerHTML = '❌ **FEIL ved innsending:** ' + error.message;
            responsOmrade.style.borderColor = '#dc3545'; // Rød
        });
    });
});