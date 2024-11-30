let patientList = [];

const getAllPatients = async () => {
    await fetch('http://localhost:8085/api/v1/patient')
        .then((res) => res.json())
        .then((data) => {
            patientList = data;
            console.log(patientList)
        })
        .catch((err) => {
            console.log(err);
        })
}

const deletePatient = async (id) => {
    if (confirm("Are you sure you want to delete this patient?")) {
        await fetch(`http://localhost:8085/api/v1/patient?id=${id}`, {
            method: 'DELETE',
        })
            .then((res) => {
                if (res.ok) {
                    loadAllPatients();
                    alert("Deleted Successfully");
                }
            })
            .catch((err) => {
                console.log(err);
                alert('Something went wrong');
            })
    }
}
const loadAllPatients = async () => {
    await getAllPatients();
    document.getElementById("patient-list-table").innerHTML = '';
    patientList.forEach(patient => {
        document.getElementById("patient-list-table").innerHTML += `
            <tr id="${patient.id}">
                <td>
                    <h2 class="table-avatar">
                            <a href="../profile.html">${patient.name}</a>
                    </h2>
                </td>
                <td>${patient.dob}</td>

                <td>${patient.phone}</td>

                <td>${patient.address}</td>
                <td className="col-auto profile-btn">
             
                    <a href="patient-profile-settings.html?id=${patient.id}" className="btn btn-primary">
                        Edit
                    </a>
                    /   
                    <a onclick="deletePatient(${patient.id})" class="btn text-danger">
                        Delete
                    </a>
                </td>
                
            </tr>
            `
    })
}
loadAllPatients();
