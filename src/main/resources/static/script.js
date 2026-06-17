//  MENU 1
fetch('http://localhost:7070/macchine')
    .then(res => res.json())
    .then(data => {
        const griglia = document.getElementById('griglia');
        if (!griglia) return;
        data.forEach(m => {
            griglia.innerHTML += `
                <div class="flex flex-col justify-evenly gap-y-2 bg-white/15 rounded-2xl p-7 shadow-lg hover:bg-white/25 transition">
                    <div class="text-xs text-white/80 ">ID AUTO: <strong>${m.id}</strong></div>
                    <div class="text-lg font-bold">${m.targa}</div>
                    <div class="flex items-center gap-x-5 w-full">
                        <div class="text-blue-400">${m.marca}</div>
                        <div class="text-white/80">📍 Posto ${m.idStallo}</div>
                    </div>
                    <div class="text-sm text-white/80 mt-2">ORA DI ENTRATA: <strong>${m.oraEntrata.replace('T', ' ')}</strong></div>
                </div>
            `;
        });
    });



//  MENU 2
function cercaPer(colonna, valore) {
    if (!valore) {
        alert('Inserisci un valore!');
        return;
    }

    document.getElementById('inputId').value = '';
    document.getElementById('inputTarga').value = '';
    document.getElementById('inputPos').value = '';

    fetch(`http://localhost:7070/macchine/${colonna}/${valore}`)
        .then(res => {
            if (!res.ok) throw new Error('Macchina non trovata');
            return res.json();
        })
        .then(m => {
            const tbody = document.getElementById('tbody');
            tbody.innerHTML = `
                <tr class="hover:bg-white/5 transition">
                    <td class="px-6 py-4 text-white/50">${m.id}</td>
                    <td class="px-6 py-4 font-semibold">${m.targa}</td>
                    <td class="px-6 py-4 text-violet-400">${m.marca}</td>
                    <td class="px-6 py-4">
                        <span class="bg-violet-600 text-white text-xs px-2 py-1 rounded-full">📍 ${m.idStallo}</span>
                    </td>
                    <td class="px-6 py-4 text-white/50">${m.oraEntrata.replace('T', ' ')}</td>
                </tr>
            `;
            document.getElementById('risultato').classList.remove('hidden');
        })
        .catch(() => {
            document.getElementById('risultato').classList.add('hidden');
            alert('Macchina non trovata!');
        });
}


//  MENU 3

// Carica le marche al caricamento della pagina
window.addEventListener('load', () => {
    const select = document.getElementById('selectMarca');
    if (!select) return; // se non esiste il select, esci subito

    fetch('http://localhost:7070/marche')
        .then(res => res.json())
        .then(data => {
            data.forEach(m => {
                select.innerHTML += `<option value="${m.nomeMarca}" class="bg-gray-900">${m.nomeMarca}</option>`;
            });
            select.innerHTML += `<option value="Altro" class="bg-gray-900">Altro</option>`;
        });
});

document.addEventListener('DOMContentLoaded', () => {
    const select = document.getElementById('selectMarca');
    if (!select) return;
    select.addEventListener('change', () => {
        const wrapper = document.getElementById('inputAltraWrapper');
        if (!wrapper) return;
        if (select.value === 'Altro') {
            wrapper.classList.remove('hidden');
        } else {
            wrapper.classList.add('hidden');
        }
    });
});

function entra() {
    const targa = document.getElementById('inputTarga').value.trim().toUpperCase();
    let marca = document.getElementById('selectMarca').value;
    if (marca === 'Altro') {
        marca = document.getElementById('inputAltraMarca').value.trim();
        if (!marca) {
            alert('Inserisci il nome della marca!');
            return;
        }
    }
    const pos = document.getElementById('inputPos').value.trim();

    const errTarga = document.getElementById('errTarga');
    const errPos = document.getElementById('errPos');
    const successo = document.getElementById('successo');

    // Reset errori
    errTarga.classList.add('hidden');
    errPos.classList.add('hidden');
    successo.classList.add('hidden');

    if (targa.length !== 7) {
        errTarga.textContent = 'La targa deve essere di 7 caratteri!';
        errTarga.classList.remove('hidden');
        return;
    }

    if (!marca) {
        alert('Seleziona una marca!');
        return;
    }

    if (!pos) {
        errPos.textContent = 'Inserisci una posizione!';
        errPos.classList.remove('hidden');
        return;
    }

    fetch('http://localhost:7070/macchine', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
            targa: targa,
            marca: marca,
            idStallo: parseInt(pos)
        })
    })
        .then(res => {
            if (res.status === 409) throw new Error('targa');
            if (res.status === 410) throw new Error('posizione');
            if (!res.ok) throw new Error('generico');
            return res.text();
        })
        .then(() => {
            document.getElementById('inputTarga').value = '';
            document.getElementById('selectMarca').selectedIndex = 0;
            document.getElementById('inputPos').value = '';
            successo.classList.remove('hidden');
            document.getElementById('inputAltraMarca').value = '';
            document.getElementById('inputAltraWrapper').classList.add('hidden');
            document.getElementById('selectMarca').selectedIndex = 0;
        })
        .catch(err => {
            if (err.message === 'targa') {
                errTarga.textContent = 'Targa già presente nel parcheggio!';
                errTarga.classList.remove('hidden');
            } else if (err.message === 'posizione') {
                errPos.textContent = 'Posizione già occupata, scegline un\'altra!';
                errPos.classList.remove('hidden');
            } else {
                alert('Errore generico, riprova!');
            }
        });
}

// USCITA
function esci() {
    const targa = document.getElementById('inputUscita').value.trim().toUpperCase();
    const errUscita = document.getElementById('errUscita');
    const successoUscita = document.getElementById('successoUscita');

    errUscita.classList.add('hidden');
    successoUscita.classList.add('hidden');

    if (targa.length !== 7) {
        errUscita.textContent = 'La targa deve essere di 7 caratteri!';
        errUscita.classList.remove('hidden');
        return;
    }

    fetch(`http://localhost:7070/macchine/${targa}`, { method: 'DELETE' })
        .then(res => {
            if (!res.ok) throw new Error('non trovata');
            return res.text();
        })
        .then(() => {
            document.getElementById('inputUscita').value = '';
            successoUscita.classList.remove('hidden');
        })
        .catch(() => {
            errUscita.textContent = 'Macchina non trovata nel parcheggio!';
            errUscita.classList.remove('hidden');
        });
}