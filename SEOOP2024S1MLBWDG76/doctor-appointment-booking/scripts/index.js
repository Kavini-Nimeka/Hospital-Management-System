


let authUser = () => {
    const user = JSON.parse(localStorage.getItem('user'));
    if (!user) {
        window.location.href = 'pages/login.html';
    }
    document.getElementById('user-menu').children[1].innerHTML = ''
    document.getElementById('user-menu').innerHTML = `
                <a class="dropdown-item" onclick="logout()">Logout</a>`
}
authUser();

const logout = () =>{
    if (confirm('Are you sure you want to log out?')) {
        localStorage.removeItem('user');
        window.location.href = 'pages/login.html';
    }
}
document.getElementById("doctor-search-btn").addEventListener("click", () => {
    const text = document.getElementById("doctor-search-home-input").value;
    navigateToDoctor(text)
})

const navigateToDoctor = (text) => {
    window.location.href = `pages/search.html?text=${text}`
}

