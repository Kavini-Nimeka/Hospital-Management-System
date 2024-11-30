let doctorsList = []
const params = new URLSearchParams(window.location.search);



const fetchDoctorsAPI =async (name) => {
    try {
        const response = await fetch(`http://localhost:8085/api/v1/doctor?name=${name}`);
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        const data = await response.json();
        return data;
    } catch (error) {
        console.error('There was a problem with your fetch operation:', error);
        return [];
    }
}
const loadDoctors = async () => {
    const text = params.get("text");
    try {
        const doctorsList = await fetchDoctorsAPI(text);

        const table = document.getElementById("doctor-container");

        doctorsList.forEach(doctor => {
            const data = `
            <div class="card" id="${doctor.id}">
            <div class="card-body">
            <div class="doctor-widget">
            <div class="doc-info-left">
            <div class="doctor-img">
            <a href="../doctor-profile.html">
            <img src="https://img.freepik.com/free-photo/beautiful-young-female-doctor-looking-camera-office_1301-7807.jpg" style="height: 200px" class="img-fluid" alt="User Image">
            </a>
            </div>
            <div class="doc-info-cont">
            <h4 class="doc-name"><a href="../doctor-profile.html">${doctor.name}</a></h4>
            <p class="doc-speciality">${doctor.degree}</p>
            <h5 class="doc-department">${doctor.special}</h5>
            <p>${doctor.price}LKR</p>
            </div>
            </div>
            <div class="doc-info-right">
            <div class="clini-infos">
            </div>
            <div class="clinic-booking">
            <a class="view-pro-btn" href="">View Profile</a>
            <a class="apt-btn" href="booking.html?id=${doctor.id}">Book Appointment</a>
            </div>
            </div>
            </div>
            </div>
            </div>
            `
            table.innerHTML = (data) + table.innerHTML
        });

    } catch (error) {
        console.error('Error loading doctors:', error);
    }
};
authUser = () => {
    const user = JSON.parse(localStorage.getItem('user'));
    if (!user) {
        window.location.href = 'login.html';
    }
    document.getElementById('user-menu').children[1].innerHTML = ''
    document.getElementById('user-menu').innerHTML = `
                <a class="dropdown-item" onclick="logout()">Logout</a>`
    loadDoctors();
}
authUser();

const logout = () =>{
    if (confirm('Are you sure you want to log out?')) {
        localStorage.removeItem('user');
        window.location.href = 'login.html';
    }
}