let prescriptionList = [];

const getAllPrescriptions = async () => {
    await fetch('http://localhost:8085/api/v1/prescription')
        .then((res) => res.json())
        .then((data) => {
            prescriptionList = data;
            console.log(prescriptionList)
        })
        .catch((err) => {
            console.log(err);
        })
}

const deletePrescription = async (id) => {
    if (confirm("Are you sure you want to delete this Prescription?")) {
        await fetch(`http://localhost:8085/api/v1/prescription?id=${id}`, {
            method: 'DELETE',
        })
            .then((res) => {
                if (res.ok) {
                    loadAllPrescriptions();
                    alert("Deleted Successfully");
                }
            })
            .catch((err) => {
                console.log(err);
                alert('Something went wrong');
            })
    }
}
const loadAllPrescriptions = async () => {
    await getAllPrescriptions();
    document.getElementById("prescription-list-table").innerHTML = '';
    prescriptionList.forEach(pr => {
        document.getElementById("prescription-list-table").innerHTML += `
            <tr id="${pr.id}">
                <td>
                    <h2>
                            <a href="">$P000-${pr.id}</a>
                    </h2>
                </td>
                <td>
                    <h2>
                            <a href="">${pr.appointment.doctorName}</a>
                    </h2>
                </td>
                <td>
                    <h2>
                            <a href="">${pr.appointment.patientName}</a>
                    </h2>
                </td>
                <td>
                    <h2>
                            <a href="">${pr.date}</a>
                    </h2>
                </td>
                <td className="col-auto profile-btn">
             
                    <a href="edit-prescription.html?id=${pr.appointment.id}&pres=${pr.id}" className="btn btn-primary">
                        Edit
                    </a>
                    /   
                    <a onclick="deletePrescription(${pr.id})" class="btn text-danger">
                        Delete
                    </a>
                </td>
                
            </tr>
            `
    })
}
loadAllPrescriptions();
