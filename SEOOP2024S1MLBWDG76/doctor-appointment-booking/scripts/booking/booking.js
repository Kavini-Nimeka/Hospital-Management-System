let selectedDate ;
const params = new URLSearchParams(window.location.search);

function parseDateTime(dateTimeString) {
    // Split the date time string into components
    var components = dateTimeString.split(' ');

    // Extract day, month, year, hour, minute
    var day = parseInt(components[0], 10);
    var month = components[1];
    var year = parseInt(components[2], 10);
    var time = components[3];
    var meridiem = components[4];

    // Convert month abbreviation to month index
    var monthIndex = {
        'JAN': 0, 'FEB': 1, 'MAR': 2, 'APR': 3, 'MAY': 4, 'JUN': 5,
        'JUL': 6, 'AUG': 7, 'SEP': 8, 'OCT': 9, 'NOV': 10, 'DEC': 11
    }[month.toUpperCase()];

    // Adjust hour for AM/PM format
    var hour = parseInt(time.split(':')[0], 10);
    if (meridiem.toUpperCase() === 'PM' && hour < 12) {
        hour += 12;
    } else if (meridiem.toUpperCase() === 'AM' && hour === 12) {
        hour = 0;
    }

    // Extract minutes
    var minutes = parseInt(time.split(':')[1], 10);

    // Create a new Date object
    var parsedDate = new Date(year, monthIndex, day, hour, minutes);

    return parsedDate;
}

function selectTimeSlot(element) {
    let allTimingLinks = document.querySelectorAll('.timing');
    let allDateLinks = document.querySelectorAll('.slot-date');
    allTimingLinks.forEach(function(link) {
        link.classList.remove('selected');
    });
    element.classList.add('selected');
    const time = element.querySelector('span').innerText+" AM";
    let parentUl = element.parentNode.parentNode;
    let index = Array.prototype.indexOf.call(parentUl.children, element.parentNode);
    selectedDate = parseDateTime(allDateLinks[index].innerText + " " + time);

}
function formatLocalDateTime(date) {
    var year = date.getFullYear();
    var month = ('0' + (date.getMonth() + 1)).slice(-2); // Month is zero-indexed
    var day = ('0' + date.getDate()).slice(-2);
    var hours = ('0' + date.getHours()).slice(-2);
    var minutes = ('0' + date.getMinutes()).slice(-2);
    var seconds = ('0' + date.getSeconds()).slice(-2);

    return year + '-' + month + '-' + day + ' ' + hours + ':' + minutes + ':' + seconds;
}

const confirmBooking = async () => {
    if (!selectedDate) {
        alert("Time not selected !");
        return;
    }
    const data = {
        doctor: params.get("id"),
        doctorName: "Dr. Darren Elder",
        patient: JSON.parse(localStorage.getItem('user')).id,
        patientName: "Sudeera",
        reservedTime: formatLocalDateTime(selectedDate),
        amount: 5000
    }
    const res = await fetch('http://localhost:8085/api/v1/appointment', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
    if (!res.ok) {
        alert('Network response was not ok');
    }
    window.location.href = "booking-success.html";
}
authUser = () => {
    const user = JSON.parse(localStorage.getItem('user'));
    if (!user) {
        window.location.href = 'login.html';
    }
    document.getElementById('user-menu').children[1].innerHTML = ''
    document.getElementById('user-menu').innerHTML = `
                <a class="dropdown-item" onclick="logout()">Logout</a>`
}
authUser();

const logout = () =>{
    if (confirm('Are you sure you want to log out?')) {
        localStorage.removeItem('user');
        window.location.href = 'login.html';
    }
}