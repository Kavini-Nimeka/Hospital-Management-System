
document.getElementById('btn-admin-login').addEventListener('click', async () => {
    const username = document.getElementById('input-admin-login-username').value
    const password = document.getElementById('input-admin-login-password').value
    const user = {
        username,
        password
    }
    const res =await fetch(`http://localhost:8085/api/v1/admin/auth?username=${username}&password=${password}`, {
        method: 'POST',
    })
    if (res.ok) {
        localStorage.setItem('admin',JSON.stringify(user));
        window.location.href = '../index.html'
    }else {
        alert('Invalid username or password');
        throw new Error('Network response was not ok');
    }
})