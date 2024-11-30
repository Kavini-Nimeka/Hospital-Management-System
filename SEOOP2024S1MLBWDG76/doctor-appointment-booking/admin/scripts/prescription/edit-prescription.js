const params = new URLSearchParams(window.location.search);
const id = params.get('id');
const pres = params.get('pres');
const url = `http://localhost:8085/api/v1/appointment?id=${id}`;
let itemList = [];
let appointment;
const getItemList  = async () => {
    const res = await fetch('http://localhost:8085/api/v1/item');
    itemList = await res.json();

}

const loadPrescription = async (pres) => {
    const res = await fetch('http://localhost:8085/api/v1/prescription');
    let result = await res.json();
    result = result.filter(r => r.id == pres)[0].prescriptionItems;
    console.log(result)
    result.forEach(item => {
        let table = document.getElementById("table-prescription-items");
        table.innerHTML += `
        <tr>
            <td>
                 <input id="autocomplete-input" class="form-control" type="text" list="drugs-list" value="${item.item.name}">
                <datalist id="drugs-list">
                ${
            itemList.map(drug => {
                return `<option value="${drug.name}" class="${drug.id}">`
            })
        }
                </datalist>
            </td>
            <td>
                <input class="form-control" value="${item.days}" type="number">
            </td>
            <td>
                <div class="form-check form-check-inline">
                    <label class="form-check-label">
                        <input class="form-check-input" type="checkbox" ${item.note.includes('Morning') ? 'checked' : ''}> Morning
                    </label>
                </div>
                <div class="form-check form-check-inline">
                    <label class="form-check-label">
                        <input class="form-check-input" type="checkbox" ${item.note.includes('Afternoon') ? 'checked' : ''}> Afternoon
                    </label>
                </div>
                <div class="form-check form-check-inline">
                    <label class="form-check-label">
                        <input class="form-check-input" type="checkbox" ${item.note.includes('Evening') ? 'checked' : ''}> Evening
                    </label>
                </div>
                <div class="form-check form-check-inline">
                    <label class="form-check-label">
                        <input class="form-check-input" type="checkbox" ${item.note.includes('Night') ? 'checked' : ''}   > Night
                    </label>
                </div>
            </td>
            <td>
                <a style="cursor: pointer" onclick="deleteRow()" class="text-danger">Delete</a>
            </td>
        </tr>
        `;
    });
}
const getPrescription = async () => {
    if (!id) {
        return;
    }
    const res = await fetch(url);
    const appointmentList = await res.json();
    if (appointmentList.length === 0) {
        return;
    }
     appointment = appointmentList[0];
    if (!appointment) {
        return;
    }
    console.log(appointment);
    document.getElementById("label-appointment-patient-name").innerText = appointment.patientName;
    document.getElementById("label-appointment-date").innerHTML = appointment.reservedTime.split(' ')[0]+`&nbsp;&nbsp;&nbsp;`+appointment.reservedTime.split(' ')[1];

    getItemList();
    if (!pres) {
        return;
    }
    document.getElementById("btn-save-prescription").innerText = "Update";
    loadPrescription(pres);
}
const deleteRow = () => {
    event.target.parentElement.parentElement.remove()
}
const addRowToTable = () => {

    let table = document.getElementById("table-prescription-items");
    table.innerHTML += `
        <tr>
            <td>
                 <input id="autocomplete-input" class="form-control" type="text" list="drugs-list" value="">
                <datalist id="drugs-list">
                ${
                    itemList.map(drug => {
                        return `<option value="${drug.name}" class="${drug.id}">`
                    })
                }
                </datalist>
            </td>
            <td>
                <input class="form-control" value="" type="number">
            </td>
            <td>
                <div class="form-check form-check-inline">
                    <label class="form-check-label">
                        <input class="form-check-input" type="checkbox"> Morning
                    </label>
                </div>
                <div class="form-check form-check-inline">
                    <label class="form-check-label">
                        <input class="form-check-input" type="checkbox"> Afternoon
                    </label>
                </div>
                <div class="form-check form-check-inline">
                    <label class="form-check-label">
                        <input class="form-check-input" type="checkbox"> Evening
                    </label>
                </div>
                <div class="form-check form-check-inline">
                    <label class="form-check-label">
                        <input class="form-check-input" type="checkbox"> Night
                    </label>
                </div>
            </td>
            <td>
                <a style="cursor: pointer" onclick="deleteRow()" class="text-danger">Delete</a>
            </td>
        </tr>
        `;
}

document.getElementById("btn-clear-prescription").addEventListener('click', async () => {
    document.getElementById("table-prescription-items").innerText='';
})
document.getElementById("btn-save-prescription").addEventListener('click', async () => {
    const prescription = {
        prescriptionItems: []
    }
    let table = document.getElementById("table-prescription-items");
    let rows = table.getElementsByTagName("tr");
    for (let i = 0; i < rows.length; i++) {
        let cells = rows[i].getElementsByTagName("td");
        let item = {
            item: {
                id: itemList.filter(item => item.name === cells[0].getElementsByTagName('input')[0].value)[0].id
            },
            days: cells[1].getElementsByTagName('input')[0].value,
            note:
                `${cells[2].getElementsByTagName('input')[0].checked ? 'Morning ' : ''}` +
                `${cells[2].getElementsByTagName('input')[1].checked ? 'Afternoon ' : ''}` +
                `${cells[2].getElementsByTagName('input')[2].checked ? 'Evening ' : ''}` +
                `${cells[2].getElementsByTagName('input')[3].checked ? 'Night' : ''}`
        }
        item.id = itemList.filter(item => item.name === item.name)[0].id
        if (item.id === undefined || item.name === '' || item.price === '' || item.note === '') {
            alert('Please fill all the fields');
            return;
        }
        prescription.prescriptionItems.push(item);
    }

    prescription.appointment  = {
        id : appointment.id
    };
    prescription.date = formatDate(new Date());
    console.log(prescription)
    if (!pres) {
        const res = await fetch(`http://localhost:8085/api/v1/prescription`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(prescription)
        });
        if (!res.ok) {
            alert('Network response was not ok');
            return;
        }
        alert('Prescription added successfully');
    }else {
        prescription.id = pres;
        console.log(prescription)
        const res = await fetch(`http://localhost:8085/api/v1/prescription`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(prescription)
        });
        if (!res.ok) {
            alert('Network response was not ok');
            return;
        }
        alert('Prescription updated successfully');
    }


    window.location.href = "prescription-list.html";
})
function formatDate(date) {
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0'); // getMonth() is zero-based
    const day = String(date.getDate()).padStart(2, '0');

    return `${year}-${month}-${day}`;
}

authAdmin = () => {
    const user = JSON.parse(localStorage.getItem('admin'));
    if (!user) {
        window.location.href = 'login.html';
    }
    getPrescription()
}
authAdmin();

