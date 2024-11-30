
document.getElementById('btn-patient-login-onActon').addEventListener('click', async () => {
    const username = document.getElementById('input-login-username').value;
    const password = document.getElementById('input-login-password').value;

    const res = await fetch(`http://localhost:8085/api/v1/patient/auth?username=${username}&password=${password}`, {
        method: 'POST',
    })
    if (!res.ok) {
        alert('Invalid username or password');
        throw new Error('Network response was not ok');
    }
    const data = await res.json();
    localStorage.setItem('user', JSON.stringify(data));
    window.location.href = '../index.html';
})