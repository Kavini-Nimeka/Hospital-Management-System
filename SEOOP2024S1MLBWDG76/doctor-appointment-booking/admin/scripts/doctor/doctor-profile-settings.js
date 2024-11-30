 const params = new URLSearchParams(window.location.search);
 const id = params.get('id');
 const url = `http://localhost:8085/api/v1/doctor/find?id=${id}`;
 const getDoctor = async () => {
     if (!id) {
         return;
     }
     const res = await fetch(url);
     const doctor = await res.json();
     document.getElementById("input-doctor-full-name").value = doctor.name;
     document.getElementById("input-doctor-education").value = doctor.degree;
     document.getElementById("input-doctor-speciality").value = doctor.special;
     document.getElementById("input-doctor-price").value = doctor.price;
     document.getElementById("save-new-doctor-btn").innerText = "Update Doctor";
 }


 
document.getElementById("save-new-doctor-btn").addEventListener('click', async () => {
    let name = document.getElementById("input-doctor-full-name").value;
    let degree = document.getElementById("input-doctor-education").value;
    let special = document.getElementById("input-doctor-speciality").value;
    let price = document.getElementById("input-doctor-price").value;

    if (name === "" || degree === "" || special === "" || price === "") {
        alert("Please fill all the fields");
        return;
    }
    price = parseFloat(price);
    if (price < 0) {
        alert("Price cannot be negative");
        return;
    }

    if (!id) {
        let doctor = {name, degree, special, price};
        const res = await fetch('http://localhost:8085/api/v1/doctor', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(doctor)
        });
        if (!res.ok) {
            alert('Network response was not ok');
        }
        alert('Doctor added successfully');
        window.location.href = "doctor-list.html";

    } else {
        let doctor = {id,name, degree, special, price};
        const res = await fetch(`http://localhost:8085/api/v1/doctor`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(doctor)
        });
        if (!res.ok) {
            alert('Network response was not ok');
        }
        alert('Doctor updated successfully');
        window.location.href = "doctor-list.html";
    }
})

 authAdmin = () => {
     const user = JSON.parse(localStorage.getItem('admin'));
     if (!user) {
         window.location.href = 'login.html';
     }
     getDoctor()
 }
 authAdmin();
