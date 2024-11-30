const params = new URLSearchParams(window.location.search);
const id = params.get('id');
const url = `http://localhost:8085/api/v1/patient/find?id=${id}`;
const getPatient = async () => {
    if (!id) {
        return;
    }
    const res = await fetch(url);
    const patient = await res.json();
        console.log(patient)
    document.getElementById("input-patient-full-name").value = patient.name;
    document.getElementById("input-patient-dob").value = patient.dob;
    document.getElementById("input-patient-address").value = patient.address;
    document.getElementById("input-patient-phone").value = patient.phone;
    document.getElementById("input-patient-username").value = patient.username;
    document.getElementById("input-patient-password").value = patient.password;
    document.getElementById("save-new-patient-btn").innerText = "Update Doctor";
}



document.getElementById("save-new-patient-btn").addEventListener('click', async () => {
    let name = document.getElementById("input-patient-full-name").value;
    let address = document.getElementById("input-patient-address").value;
    let dob = document.getElementById("input-patient-dob").value;
    let phone = document.getElementById("input-patient-phone").value;
    let username = document.getElementById("input-patient-username").value;
    let password = document.getElementById("input-patient-password").value;
    // dob = dob.split("-").reverse().join("-");
    if (name === "" || address === "" || dob === "" || phone === "" || username === "" || password === "") {
        alert("Please fill all the fields");
        return;
    }

    if (!id) {
        let patient = {name, address, dob, phone, username, password};
        const res = await fetch('http://localhost:8085/api/v1/patient', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(patient)
        });
        if (!res.ok) {
            alert('Network response was not ok');
        }
        alert('Patient added successfully');
        window.location.href = "patient-list.html";
    } else {
        let patient = {id,name,  address, dob, phone, username, password};
        const res = await fetch(`http://localhost:8085/api/v1/patient`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(patient)
        });
        if (!res.ok) {
            alert('Network response was not ok');
        }
        alert('Patient updated successfully');
        window.location.href = "patient-list.html";
    }
})

authAdmin = () => {
    const user = JSON.parse(localStorage.getItem('admin'));
    if (!user) {
        window.location.href = 'login.html';
    }
    getPatient()
}
authAdmin();
