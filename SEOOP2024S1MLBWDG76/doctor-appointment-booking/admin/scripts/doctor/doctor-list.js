let doctorList = [];

const getAllDoctors = async () => {
    await fetch('http://localhost:8085/api/v1/doctor')
        .then((res) => res.json())
        .then((data) => {
            doctorList = data;
        })
        .catch((err) => {
            console.log(err);
        })
}

const deleteDoctor = async (id) => {
    if (confirm('Are you sure you want to delete this doctor?')) {
        await fetch(`http://localhost:8085/api/v1/doctor?id=${id}`, {
            method: 'DELETE',
        })
            .then((res) => {
                if (res.ok) {
                    loadDoctorList();
                    alert("Deleted Successfully");
                }
            })
            .catch((err) => {
                console.log(err);
                alert('Something went wrong');
            })
    }

}
const loadDoctorList = async () => {
    await getAllDoctors();
    document.getElementById("doctor-list-table").innerHTML = '';
    doctorList.forEach(doc => {
        document.getElementById("doctor-list-table").innerHTML += `
            <tr>
                <td>
                    <h2 class="table-avatar">
                            <a href="../profile.html">${doc.name}</a>
                    </h2>
                </td>
                <td>${doc.special}</td>

                <td>${doc.degree}</td>

                <td>${doc.price}LKR</td>
                <td className="col-auto profile-btn">
             
                    <a href="doctor-profile-settings.html?id=${doc.id}" className="btn btn-primary">
                        Edit
                    </a>
                    /   
                    <a onclick="deleteDoctor(${doc.id})" class="btn text-danger">
                        Delete
                    </a>
                </td>
                
            </tr>
            `
    })
}
loadDoctorList();
