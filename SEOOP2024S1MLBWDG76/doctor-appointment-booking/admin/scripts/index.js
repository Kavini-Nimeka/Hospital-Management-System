let authAdmin = () => {
    const user = JSON.parse(localStorage.getItem('admin'));
    if (!user) {
        window.location.href = 'pages/login.html';
    }
}
authAdmin();

const logout = () => {
    if (confirm('Are you sure you want to log out?')) {
        localStorage.removeItem('admin');
        window.location.href = 'pages/login.html';
    }
}