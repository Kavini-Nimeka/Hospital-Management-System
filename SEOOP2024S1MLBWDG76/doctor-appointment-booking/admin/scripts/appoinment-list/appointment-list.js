let appointmentList = [];

const getAllAppointments = async () => {
    await fetch('http://localhost:8085/api/v1/appointment')
    .then((res) => res.json())
    .then((data) => {
        appointmentList = data;
        console.log(appointmentList);
    })
    .catch((err) => {
        console.log(err);
    })
}
const cancelAppointment = async (id) => {
    if (confirm('Are you sure you want to cancel this appointment?')) {
        await fetch(`http://localhost:8085/api/v1/appointment?id=${id}`, {
            method: 'DELETE',
        })
            .then((res) => {
                if (res.ok) {
                    loadAppointments();
                    alert("Deleted Successfully");
                }
            })
            .catch((err) => {
                console.log(err);
                alert('Something went wrong');
            })
    }

}
const loadAppointments = async () => {
    await getAllAppointments();
    document.getElementById("appointments-list-table").innerHTML = '';
    appointmentList.forEach( app => {
        document.getElementById("appointments-list-table").innerHTML +=`
            <tr>
                <td>
                    <h2 class="table-avatar">
                        
                        <a href="../../pages/profile.html">${app.doctorName}</a>
                    </h2>
                </td>
                <td>Dental</td>
                <td>
                    <h2 class="table-avatar">
                    
                        <a href="../../pages/profile.html">${app.patientName}</a>
                    </h2>
                </td>
                <td>${app.reservedTime}</td>
                <td class="text-right">
                    ${app.amount}LKR
                </td>
                <td className="col-auto profile-btn">
             
                    <a href="edit-prescription.html?id=${app.id}" className="btn btn-primary">
                        Create
                    </a>
                    /   
                    <a onclick="cancelAppointment(${app.id})" class="btn text-danger">
                        Cancel
                    </a>
                </td>
            </tr>
            `
    })
}
authAdmin = () => {
    const user = JSON.parse(localStorage.getItem('admin'));
    if (!user) {
        window.location.href = 'login.html';
    }
    loadAppointments()
}
authAdmin();
